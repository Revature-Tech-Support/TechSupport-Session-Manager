package com.github.RevatureTechSupport.TechSupportSessionManager.domain;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Table("tickets")
public class Ticket {

    @PrimaryKeyColumn(name="id", type= PrimaryKeyType.PARTITIONED )
    private UUID id = UUID.randomUUID();
    private String issueTitle;
    private UUID openedBy;
    private UUID closedBy;
    private Timestamp openTime = new Timestamp(System.currentTimeMillis());
    private Timestamp reviewTime;
    private Timestamp closedTime;
    private boolean inQueue = true;
    private boolean reviewed = false; //flag to distinguish if issue is closed or not

    public Ticket () {}

    public Ticket( String issueTitle, UUID openedBy) {
        this.id = UUID.randomUUID();
        this.issueTitle = issueTitle;
        this.openedBy = openedBy;
        this.closedBy = null;
        this.openTime = new Timestamp(System.currentTimeMillis());
        this.reviewTime = null;
        this.closedTime = null;
        this.inQueue = true;
        this.reviewed = false;
    }

    public Ticket(UUID id, String issueTitle, UUID openedBy, UUID closedBy, Timestamp openTime, Timestamp reviewTime, Timestamp closedTime, boolean inQueue, boolean reviewed) {
        this.id = id;
        this.issueTitle = issueTitle;
        this.openedBy = openedBy;
        this.closedBy = closedBy;
        this.openTime = openTime;
        this.reviewTime = reviewTime;
        this.closedTime = closedTime;
        this.inQueue = inQueue;
        this.reviewed = reviewed;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getIssueTitle() {
        return issueTitle;
    }

    public void setIssueTitle(String issueTitle) {
        this.issueTitle = issueTitle;
    }

    public UUID getOpenedBy() {
        return openedBy;
    }

    public void setOpenedBy(UUID openedBy) {
        this.openedBy = openedBy;
    }

    public UUID getClosedBy() {
        return closedBy;
    }

    public void setClosedBy(UUID closedBy) {
        this.closedBy = closedBy;
    }

    public Timestamp getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Timestamp openTime) {
        this.openTime = openTime;
    }

    public Timestamp getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Timestamp reviewTime) {
        this.reviewTime = reviewTime;
    }

    public Timestamp getClosedTime() {
        return closedTime;
    }

    public void setClosedTime(Timestamp closedTime) {
        this.closedTime = closedTime;
    }

    public boolean isInQueue() {
        return inQueue;
    }

    public void setInQueue(boolean inQueue) {
        this.inQueue = inQueue;
    }

    public boolean isReviewed() {
        return reviewed;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return inQueue == ticket.inQueue && reviewed == ticket.reviewed && Objects.equals(id, ticket.id) && Objects.equals(issueTitle, ticket.issueTitle) && Objects.equals(openedBy, ticket.openedBy) && Objects.equals(closedBy, ticket.closedBy) && Objects.equals(openTime, ticket.openTime) && Objects.equals(reviewTime, ticket.reviewTime) && Objects.equals(closedTime, ticket.closedTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, issueTitle, openedBy, closedBy, openTime, reviewTime, closedTime, inQueue, reviewed);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", issueTitle='" + issueTitle + '\'' +
                ", openedBy=" + openedBy +
                ", closedBy=" + closedBy +
                ", openTime=" + openTime +
                ", reviewTime=" + reviewTime +
                ", closedTime=" + closedTime +
                ", inQueue=" + inQueue +
                ", reviewed=" + reviewed +
                '}';
    }
}
