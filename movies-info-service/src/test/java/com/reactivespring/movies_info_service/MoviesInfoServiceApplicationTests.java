package com.reactivespring.movies_info_service;

import com.reactivespring.movies_info_service.domain.MovieInfo;
import com.reactivespring.movies_info_service.repository.MovieInfoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@DataMongoTest
@ActiveProfiles("test")
class MoviesInfoServiceApplicationTests {

	@Autowired
	MovieInfoRepository movieInfoRepository;

	@BeforeEach
	void setUp() {
		var movieInfo = List.of(new MovieInfo(null, "Batman Begins",
				2025, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15")));
		movieInfoRepository.saveAll(movieInfo)
				.blockLast();
	}

	@Test
	void contextLoads() {
		//given

		//when
		var moviesInfoFlux = movieInfoRepository.findAll();
		//then

	}
	@AfterEach
	void tearDown() {
		movieInfoRepository.deleteAll().block();
	}

	@Test
	void findAll() {

		var moviesInfoFlux = movieInfoRepository.findAll().log();
					StepVerifier.create(moviesInfoFlux)
						.expectNextCount(3).verifyComplete();
	}

	@Test
	void findById() {

		var moviesInfoMono = movieInfoRepository.findById("123").log();
		StepVerifier.create(moviesInfoMono)
//				.expectNextCount(3)

				.verifyComplete();
	}

	@Test
	void saveMovieInfo() {
		var movieInfo = new MovieInfo(null, "Batman Begins",
				2025, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15"));

		var moviesInfoMono = movieInfoRepository.save(movieInfo).log();
		StepVerifier.create(moviesInfoMono)
//				.expectNextCount(3)
				.assertNext(movie -> {
				})
				.verifyComplete();
	}

	@Test
	void updateMovieInfo() {
		var movieInfo = movieInfoRepository.findById("123").block();
		movieInfo.setYear(2021);
		var moviesInfoMono = movieInfoRepository.save(movieInfo).log();
		StepVerifier.create(moviesInfoMono)
//				.expectNextCount(3)
				.assertNext(movie -> {
				})
				.verifyComplete();
	}


	@Test
	void deleteMovieInfo() {
		var movieInfo = movieInfoRepository.deleteById("123").block();
//		movieInfo.setYear(2021);
//		var moviesInfoMono = movieInfoRepository.save(movieInfo).log();
 		var moviesInfoMono = movieInfoRepository.findAll().log();
		StepVerifier.create(moviesInfoMono)
//				.expectNextCount(3)
				.assertNext(movie -> {
				})
				.verifyComplete();
	}

}
