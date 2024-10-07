package com.reactivespring.router;

import com.reactivespring.handler.ReviewHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ReviewRouter {

    @Bean
    public RouterFunction<ServerResponse> reviewsRoute(ReviewHandler reviewHandler) {

        return route()
                .nest(path("/v1/reviews"),builder -> {
                      builder.POST("/v1/reviews",reviewHandler::addReview)
                             .GET("/v1/reviews",reviewHandler::getReviews);
                })
                .GET("/v1/helloworld",(request -> ServerResponse.ok().bodyValue("helloWorld")))
                .POST("/v1/reviews",reviewHandler::addReview)
                .GET("/v1/reviews",reviewHandler::getReviews)
//                .GET("/v1/reviews/{movieInfoId}",reviewHandler::getReview)
                .PUT("/v1/reviews/{id}",reviewHandler::updateReview)
                .DELETE("/v1/reviews/{id}",reviewHandler::deleteReview)
                .build();
    }

    private RequestPredicate path(String path) {
        return new RequestPredicate() {
            @Override
            public boolean test(ServerRequest serverRequest) {
                return false;
            }
        };
    }
}
