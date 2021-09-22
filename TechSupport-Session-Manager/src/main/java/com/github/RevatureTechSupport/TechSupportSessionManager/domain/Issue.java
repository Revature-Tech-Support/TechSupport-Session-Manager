package com.github.RevatureTechSupport.TechSupportSessionManager.domain;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Table("issues")
public class Issue {

    private UUID issueId = UUID.randomUUID();
    private String issueTitle;
    private UUID openedBy;
    private UUID closedBy;
    @PrimaryKeyColumn(name = "opentime", type = PrimaryKeyType.CLUSTERED)
    private Timestamp openTime = new Timestamp(System.currentTimeMillis());
    private Timestamp reviewTime;
    private Timestamp closedTime;
    @PrimaryKeyColumn(name = "inqueue", type = PrimaryKeyType.PARTITIONED)
    private boolean inQueue = true;
    private boolean reviewed = false;

    public Issue() {
    }

    public Issue(String issueTitle, UUID openedBy) {
        this.issueId = UUID.randomUUID();
        this.issueTitle = issueTitle;
        this.openedBy = openedBy;
        this.closedBy = null;
        this.openTime = new Timestamp(System.currentTimeMillis());
        this.reviewTime = null;
        this.closedTime = null;
        this.inQueue = true;
        this.reviewed = false;
    }

    public Issue(UUID issueId, String issueTitle, UUID openedBy, UUID closedBy, Timestamp openTime, Timestamp reviewTime, Timestamp closedTime, boolean inQueue, boolean reviewed) {
        this.issueId = issueId;
        this.issueTitle = issueTitle;
        this.openedBy = openedBy;
        this.closedBy = closedBy;
        this.openTime = openTime;
        this.reviewTime = reviewTime;
        this.closedTime = closedTime;
        this.inQueue = inQueue;
        this.reviewed = reviewed;
    }

    public UUID getIssueId() {
        return issueId;
    }

    public void setIssueId(UUID id) {
        this.issueId = issueId;
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
        Issue issue = (Issue) o;
        return inQueue == issue.inQueue && reviewed == issue.reviewed && Objects.equals(issueId, issue.issueId) && Objects.equals(issueTitle, issue.issueTitle) && Objects.equals(openedBy, issue.openedBy) && Objects.equals(closedBy, issue.closedBy) && Objects.equals(openTime, issue.openTime) && Objects.equals(reviewTime, issue.reviewTime) && Objects.equals(closedTime, issue.closedTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(issueId, issueTitle, openedBy, closedBy, openTime, reviewTime, closedTime, inQueue, reviewed);
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + issueId +
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