package com.reactivespring.movies_info_service.controller;

import com.reactivespring.movies_info_service.domain.MovieInfo;
import com.reactivespring.movies_info_service.service.MoviesInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1")
public class MoviesInfoController {

    private MoviesInfoService moviesInfoService;

    public MoviesInfoController(MoviesInfoService moviesInfoService) {
        this.moviesInfoService = moviesInfoService;
    }

    @DeleteMapping("/movieinfos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteMovieInfo(@PathVariable String id) {
        return moviesInfoService.deleteMovieInfo(id);
    }

    @PutMapping("/movieinfos/{id}")
    public Mono<ResponseEntity<MovieInfo>> updateMovieInfo(@PathVariable String id, @RequestBody MovieInfo updatedMovieInfo) {
        return  moviesInfoService.updateMovieInfo(updatedMovieInfo,id)
                .map(ResponseEntity.ok()::body)
                .log();
    }

    @GetMapping("/movieinfos/{id}")
    public Mono<MovieInfo> getMoviesInfos(@PathVariable String id) {
        return moviesInfoService.getMovieInfoById(id).log();
    }


    @GetMapping("/movieinfos")
    public Flux<MovieInfo> getMoviesInfos() {
        return moviesInfoService.getAllMovieInfo().log();
    }


    @PostMapping("/movieinfos")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovieInfo> addMovieInfo(@RequestBody @Valid MovieInfo movieInfo) {
        return moviesInfoService.addMovieInfo(movieInfo).log();
    }



}
