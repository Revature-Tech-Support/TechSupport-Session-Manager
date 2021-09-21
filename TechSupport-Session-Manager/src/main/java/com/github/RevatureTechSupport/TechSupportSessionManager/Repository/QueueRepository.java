package com.github.RevatureTechSupport.TechSupportSessionManager.Repository;

import com.github.RevatureTechSupport.TechSupportSessionManager.domain.Ticket;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.UUID;

@Repository
public interface QueueRepository extends ReactiveCassandraRepository<Ticket, UUID> {

    @Query("SELECT * FROM tickets WHERE inQueue= true AND reviewed = false ORDER BY openTime ASC limit 1 ALLOW FILTERING")
    Mono<Ticket> findOldestTicket();

    @Query("SELECT * FROM tickets WHERE id =?0 ALLOW FILTERING")
    Mono<Ticket> findById(UUID id);

    //find using partition key inQueue
    @Query("SELECT * FROM tickets WHERE inQueue=false AND id =?0 ALLOW FILTERING")
    Mono<Ticket> findByIdAndPk(UUID id);

}
