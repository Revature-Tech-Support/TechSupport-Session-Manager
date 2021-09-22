CREATE KEYSPACE IF NOT EXISTS techsupport WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

USE techsupport;

CREATE TABLE IF NOT EXISTS issues (
issueId UUID,
issueTitle TEXT,
openedBy UUID,
closedBy UUID,
openTime TIMESTAMP,
reviewTime TIMESTAMP,
closedTime TIMESTAMP,
inQueue BOOLEAN,
reviewed BOOLEAN
PRIMARY KEY (inQueue, openTime)
);
