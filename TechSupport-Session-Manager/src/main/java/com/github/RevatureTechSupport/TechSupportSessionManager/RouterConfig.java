package com.github.RevatureTechSupport.TechSupportSessionManager;

import com.github.RevatureTechSupport.TechSupportSessionManager.controller.QueueController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {
    @Bean
    public RouterFunction<ServerResponse> routes(QueueController queueController) {
        return route(GET("/queue/issue"), queueController::getOldestIssue)
                .andRoute(POST("/queue"), queueController::create)
                .andRoute(PUT("/queue/{id}"), queueController::update);
    }
}
