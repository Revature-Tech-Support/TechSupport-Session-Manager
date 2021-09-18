package com.github.RevatureTechSupport.TechSupportSessionManager.controller;

import com.github.RevatureTechSupport.TechSupportSessionManager.Repository.QueueRepository;
import com.github.RevatureTechSupport.TechSupportSessionManager.domain.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.sql.Timestamp;



@Component
public class QueueController {
//    @Autowired
    private QueueRepository queueRepository;

    public QueueController(QueueRepository transactionRepository){
        this.queueRepository= transactionRepository;
    }

    //Get the oldest ticket in the queue
    public Mono<ServerResponse> getOldestTicket(ServerRequest req) {
//        return ServerResponse.ok().body(this.queueRepository.findOldestTicket(), Ticket.class);
        return this.queueRepository.findOldestTicket()
                .flatMap(ticket-> {
                    ticket.setInQueue(false);
                    return this.queueRepository.save(ticket)
                        .flatMap(saved -> ServerResponse.ok().body(saved, Ticket.class))
                            .switchIfEmpty(ServerResponse.notFound().build());
        });
    }

    //Create a new Ticket in the queue
    public Mono<ServerResponse> create(ServerRequest req){
        return req.bodyToMono(Ticket.class)
                .flatMap(ticket-> this.queueRepository.save(ticket))
                .flatMap(ticket-> ServerResponse.created(URI.create("/queue/" + ticket.getIssueID().toString())).build());
    }

    //Update the ticket status to reviewed
    public Mono<ServerResponse> delete(ServerRequest req) {
        return req.bodyToMono(Ticket.class).flatMap(update -> {
                return this.queueRepository.findById(update.getIssueID())
                    .flatMap(previous -> {
                        previous.setReviewed(true);
                        previous.setCloseBy(update.getOpenedBy());
                        previous.setCloseTime(new Timestamp(System.currentTimeMillis()));
                        return this.queueRepository.save(previous)
                                .flatMap(saved -> ServerResponse.ok().build());
                    });
        }).switchIfEmpty(ServerResponse.notFound().build());

    }


}
