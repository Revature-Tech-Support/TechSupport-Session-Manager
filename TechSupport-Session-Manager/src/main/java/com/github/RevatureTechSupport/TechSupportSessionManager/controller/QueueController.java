package com.github.RevatureTechSupport.TechSupportSessionManager.controller;

import com.github.RevatureTechSupport.TechSupportSessionManager.Repository.QueueRepository;
import com.github.RevatureTechSupport.TechSupportSessionManager.domain.Issue;
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

    // Get All issues available in the queue
    public Mono<ServerResponse> getAllIssues(ServerRequest request) {
        return ServerResponse.ok().body(this.queueRepository.findAllInQueue(), Issue.class);
    }

    // Get the oldest ticket in the queue
    public Mono<ServerResponse> getOldestIssue(ServerRequest req) {
        return ServerResponse.ok().body(this.queueRepository.findOldestTicket(),Issue.class);
    }

    // Create a new issue
    public Mono<ServerResponse> create(ServerRequest req) {
        return req.bodyToMono(Issue.class)
                .flatMap(issue -> this.queueRepository.save(issue))
                .flatMap(issue -> ServerResponse.created(URI.create("/queue/" + issue.getIssueId().toString())).build());
    }

    // Remove the issue from the queue by setting inQueue to false and marks reviewed to true
    public Mono<ServerResponse> removeIssueFromQueue(ServerRequest req){
        return this.queueRepository.findById(UUID.fromString(req.pathVariable("id")))
                .flatMap(issue -> {
                    issue.setInQueue(false);
                    issue.setReviewed(true);
                    issue.setReviewTime(new Timestamp(System.currentTimeMillis()));
                    return this.queueRepository.save(issue) // Cassandra creates a duplicate entry b/c partition key inQueue is set to false
                            .flatMap(saved -> ServerResponse.ok().body(Mono.just(saved), Issue.class));
                }).switchIfEmpty(ServerResponse.notFound().build());
    }

    // Close an issue by updating closedBy and closedTime
    public Mono<ServerResponse> closeIssue(ServerRequest req) {
        return req.bodyToMono(String.class).flatMap(update -> {
            logger.info(update);
            String[] strArr = update.split(":");
            String closedBy = strArr[1].substring(2, strArr[1].length() - 3); // Extracts the UUID of the person who closed the ticket
            logger.info(closedBy);

            // Find entry using partition key and id
            return this.queueRepository.findByIdAndPk(UUID.fromString(req.pathVariable("id"))).log()
                    .flatMap(duplicate -> {
                        duplicate.setInQueue(true);
                        duplicate.setClosedBy(UUID.fromString(closedBy));
                        duplicate.setClosedTime(new Timestamp(System.currentTimeMillis()));

                        return this.queueRepository.insert(duplicate).log()  // Update duplicate entry
                                .flatMap(entry -> {
                                    entry.setInQueue(false);
                                    return this.queueRepository.save(entry).log();  // Update entry
                                });
                    }).log()
                    .flatMap(updated -> ServerResponse.ok().build());
        }).switchIfEmpty(ServerResponse.notFound().build());
    }
}