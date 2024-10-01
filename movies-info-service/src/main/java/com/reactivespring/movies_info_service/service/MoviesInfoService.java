package com.reactivespring.movies_info_service.service;

import com.reactivespring.movies_info_service.domain.MovieInfo;
import com.reactivespring.movies_info_service.repository.MovieInfoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MoviesInfoService {

    private MovieInfoRepository movieInfoRepository;
    public MoviesInfoService(MovieInfoRepository movieInfoRepository) {
        this.movieInfoRepository = movieInfoRepository;
    }

    public Mono<MovieInfo> addMovieInfo(MovieInfo movieInfo) {
        return movieInfoRepository.save(movieInfo);
    }

}
