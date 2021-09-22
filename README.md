# TechSupport-Session-Manager
Repository for session manager features in the application.


## Client Specifications:
- Test Driven Development (TDD)


## Goal:
Managing instances of chat sessions for the queue 


## User Stories:
### User
- As a User
- I want a session manager
- So that I can leave the queue and enter the chat when it's my turn

### Tech Support
- As a Tech Support
- I want a session manager
- So that I can accurately track a FIFO queue and join the user in the chat to address the technical issue

### Admin
- As a Admin
- I want a session manager
- So I can provide all users a private chat session based on their order in the queue


## Technology:
- Java
- Docker
- Spring Boot
- Spring WebFlux
- Spring Eureka Discovery
- Spring Data
- Cassandra
- Karate

## Getting Started
* To get started, clone the repository:

  ` git clone git@github.com:Revature-Tech-Support/TechSupport-Session-Manager.git`

* Before running the repository, make sure to set up a Docker container with a cassandra instance. For more information, [click here](https://cassandra.apache.org/doc/latest/cassandra/getting_started/installing.html#installing-the-docker-image)

## Usage
### Compile
To compile the program use: `mvn compile`

### Run
To run the program use: `mvn spring-boot:run`

### Clean
To clean up artifacts created by prior builds: `mvn clean`

## RESTful API endpoints
* GET `/queue`: Retrieves all the issues in the queue that haven't been reviewed yet
* POST `/queue`: Adds a new issue to the queue
* GET `/queue/issue`: Retrieves the oldest issue in the queue that hasn't been reviewed 
* PUT `/queue/{id}/update`: Marks the issue as reviewed
* PUT `/queue/{id}/close`: Marks the issue as closed 
