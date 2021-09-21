package com.github.RevatureTechSupport.TechSupportSessionManager.controller;

import com.github.RevatureTechSupport.TechSupportSessionManager.Repository.QueueRepository;
import com.github.RevatureTechSupport.TechSupportSessionManager.domain.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.sql.Timestamp;
import java.util.UUID;


@Component
public class QueueController {
    static Logger logger = LoggerFactory.getLogger(QueueController.class);

    @Autowired
    private QueueRepository queueRepository;

    //Get the oldest ticket in the queue
    public Mono<ServerResponse> getOldestTicket(ServerRequest req) {
        return this.queueRepository.findOldestTicket()
                .flatMap(ticket-> {
                    ticket.setInQueue(false);
                    ticket.setReviewed(true);
                    ticket.setReviewTime(new Timestamp(System.currentTimeMillis()));
                    return this.queueRepository.save(ticket) //cassandra creates a duplicate entry b/c partition key inQueue is set to false
                        .flatMap(saved -> ServerResponse.ok().body(Mono.just(saved), Ticket.class))
                            .switchIfEmpty(ServerResponse.notFound().build());
        });
    }

    //Create a new Ticket in the queue
    public Mono<ServerResponse> create(ServerRequest req){
        return req.bodyToMono(Ticket.class)
                .flatMap(ticket-> this.queueRepository.save(ticket))
                .flatMap(ticket-> ServerResponse.created(URI.create("/queue/" + ticket.getId().toString())).build());
    }

    //Update the ticket status to reviewed
    public Mono<ServerResponse> update(ServerRequest req) {
        return req.bodyToMono(String.class).flatMap(update -> {
            logger.info(update);
            String[] strArr = update.split(":"); // splits the string
            logger.info(strArr[1]);
            String closedBy = strArr[1].substring(2, strArr[1].length() - 3); //extracts the UUID of the person who closed the ticket
            logger.info(closedBy);
            Timestamp closedTime = new Timestamp(System.currentTimeMillis());

            //find entry using partition key and id
            return this.queueRepository.findByIdAndPk(UUID.fromString(req.pathVariable("id"))).log()
                    .flatMap(duplicate -> {
                        duplicate.setInQueue(true);
                        duplicate.setClosedBy(UUID.fromString(closedBy));
                        duplicate.setClosedTime(new Timestamp(System.currentTimeMillis()));

                        return this.queueRepository.insert(duplicate).log()  //updates duplicate entry
                                .flatMap(entry -> {
                                    entry.setInQueue(false);
                                    return this.queueRepository.save(entry).log();  //update entry
                        });
                    } )
                    .log()
                    .flatMap(updated -> ServerResponse.ok().build());
        }).switchIfEmpty(ServerResponse.notFound().build());

    }

}
