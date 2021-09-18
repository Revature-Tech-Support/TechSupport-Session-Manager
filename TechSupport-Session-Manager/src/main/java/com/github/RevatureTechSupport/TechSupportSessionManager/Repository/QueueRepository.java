package com.github.RevatureTechSupport.TechSupportSessionManager.Repository;

import com.github.RevatureTechSupport.TechSupportSessionManager.domain.Ticket;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface QueueRepository extends ReactiveCassandraRepository<Ticket, UUID> {

    @Query("select * from tickets where inQueue= true order by openTime asc limit 1")
    Mono<Ticket> findOldestTicket();
}
