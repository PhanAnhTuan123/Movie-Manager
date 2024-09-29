package com.reactivespring.movies_info_service.repository;

import com.reactivespring.movies_info_service.domain.MovieInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MovieInfoRepository extends ReactiveMongoRepository<MovieInfo, String> {

}
