package com.github.RevatureTechSupport.TechSupportSessionManager.domain;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Table("tickets")
public class Ticket {
    @PrimaryKeyColumn(name="issueID", type= PrimaryKeyType.PARTITIONED )
    private UUID issueID = UUID.randomUUID();
    private String issueTitle;
    private UUID openedBy;
    private UUID closedBy;
    private Timestamp openTime = new Timestamp(System.currentTimeMillis());
    private Timestamp reviewTime;
    private Timestamp closeTime;
    private boolean inQueue = true;
    private boolean reviewed = false; //flag to distinguish if issue is closed or not

    public Ticket(UUID issueID, String issueTitle, UUID openedBy, UUID closeBy, Timestamp openTime, Timestamp reviewTime, Timestamp closeTime, boolean inQueue, boolean reviewed) {
        this.issueID = issueID;
        this.issueTitle = issueTitle;
        this.openedBy = openedBy;
        this.closedBy = closeBy;
        this.openTime = openTime;
        this.reviewTime = reviewTime;
        this.closeTime = closeTime;
        this.inQueue = inQueue;
        this.reviewed = reviewed;
    }

    public UUID getIssueID() {
        return issueID;
    }

    public void setIssueID(UUID issueID) {
        this.issueID = issueID;
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

    public UUID getCloseBy() {
        return closedBy;
    }

    public void setCloseBy(UUID closeBy) {
        this.closedBy = closeBy;
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

    public Timestamp getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Timestamp closeTime) {
        this.closeTime = closeTime;
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
        return inQueue == ticket.inQueue && reviewed == ticket.reviewed && Objects.equals(issueID, ticket.issueID) && Objects.equals(issueTitle, ticket.issueTitle) && Objects.equals(openedBy, ticket.openedBy) && Objects.equals(closedBy, ticket.closedBy) && Objects.equals(openTime, ticket.openTime) && Objects.equals(reviewTime, ticket.reviewTime) && Objects.equals(closeTime, ticket.closeTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(issueID, issueTitle, openedBy, closedBy, openTime, reviewTime, closeTime, inQueue, reviewed);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "issueID=" + issueID +
                ", issueTitle='" + issueTitle + '\'' +
                ", openedBy=" + openedBy +
                ", closedBy=" + closedBy +
                ", openTime=" + openTime +
                ", reviewTime=" + reviewTime +
                ", closeTime=" + closeTime +
                ", inQueue=" + inQueue +
                ", reviewed=" + reviewed +
                '}';
    }
}
