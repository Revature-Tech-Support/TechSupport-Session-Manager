package com.github.RevatureTechSupport.TechSupportSessionManager.Repository;

import com.github.RevatureTechSupport.TechSupportSessionManager.domain.Issue;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface QueueRepository extends ReactiveCassandraRepository<Issue, UUID> {

    @Query("SELECT * FROM issues WHERE inQueue = true AND reviewed = false ORDER BY openTime ASC limit 1 ALLOW FILTERING")
    Mono<Issue> findOldestTicket();

    @Query("SELECT * FROM issues WHERE issueId = ?0 ALLOW FILTERING")
    Mono<Issue> findById(UUID id);

    // Find entry using partition key inQueue and issueId
    @Query("SELECT * FROM issues WHERE inQueue = false AND issueId = ?0 ALLOW FILTERING")
    Mono<Issue> findByIdAndPk(UUID id);

}
