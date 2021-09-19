package com.github.RevatureTechSupport.TechSupportSessionManager.Repository;

import com.github.RevatureTechSupport.TechSupportSessionManager.domain.Ticket;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface QueueRepository extends ReactiveCassandraRepository<Ticket, UUID> {

    @Query("SELECT * FROM tickets WHERE inqueue= true order by opentime asc limit 1 ALLOW FILTERING")
    Mono<Ticket> findOldestTicket();

    @Query("SELECT * FROM tickets WHERE id =?0")
    Mono<Ticket> findById(UUID id);
}
