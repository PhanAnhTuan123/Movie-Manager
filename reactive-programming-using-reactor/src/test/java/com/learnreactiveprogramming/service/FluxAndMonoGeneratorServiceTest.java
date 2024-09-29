package com.learnreactiveprogramming.service;

import lombok.var;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

    @Test
    void nameFlux() {
        // given

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux();
        //then
        StepVerifier.create(namesFlux)
//                .expectNext("alex", "ben", "chloe")
                .expectNextCount(3)
//                .expectNext("alex")
                .verifyComplete();

    }

    @Test
    void nameFlux_map() {

        // given
        int stringLength = 3;
        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_map(stringLength);
        //then
        StepVerifier.create(namesFlux)
                .expectNext("ALEX", "BEN", "CHLOE")
                .verifyComplete();
    }

    @Test
    void nameFlux_immutability() {
        // given

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_immutability();
        //then
        StepVerifier.create(namesFlux)
                .expectNext("alex", "ben", "chloe")
                .verifyComplete();
    }

    @Test
    void nameFlux_flatMap() {

        // given
        int stringLength = 3;
        // when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_flatmap(stringLength);
        // then
        StepVerifier.create(namesFlux)
                .expectNext("A","L","E","X","C","H","L","O","E")
                .verifyComplete();
    }

    @Test
    void namesFlux_flatmap_async() {
        // given
        int stringLength = 3;
        // when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_flatmap_async(stringLength);
        // then
        StepVerifier.create(namesFlux)
//                .expectNext("A","L","E","X","C","H","L","O","E")
                .expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void namesFlux_concatmap() {
        // given
        int stringLength = 3;
        // when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_concatmap(stringLength);
        // then
        StepVerifier.create(namesFlux)
//                .expectNext("A","L","E","X","C","H","L","O","E")
                .expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void namesMono_flatMap() {
        //given
         int stringLength = 3;
        //when
    var value = fluxAndMonoGeneratorService.namesMono_flatMap(stringLength);
        //then
        StepVerifier.create(value)
                .expectNext(Arrays.asList("A","L","E","X"))
                .verifyComplete();
    }

    @Test
    void namesMono_flatMapMany() {
        //given
        int stringLength = 3;
        //when
        var value = fluxAndMonoGeneratorService.namesMono_flatMapMany(stringLength);
        //then
        StepVerifier.create(value)
                .expectNext("A", "L", "E","X")
                .verifyComplete();

    }


    @Test
    void namesFlux_flatmap_transform() {

        int stringLength = 3;
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_transform(stringLength);
        StepVerifier.create(namesFlux)
                .expectNext("A","L","E","X","C","H","L","O","E")
                .verifyComplete();
    }

    @Test
    void namesFlux_flatmap_transform_1() {

        int stringLength = 6;
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_transform(stringLength);
        StepVerifier.create(namesFlux)
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    void namesFlux_transform_switchIfEmpty() {
        int stringLength = 6;
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_transform(stringLength);
        StepVerifier.create(namesFlux)
                .expectNext("D","E","F","A","U","L","T")
                .verifyComplete();
    }

    @Test
    void explore_concat() {

        //given

        //when
        var concatFlux = fluxAndMonoGeneratorService.explore_concat();
        //then
        StepVerifier.create(concatFlux)
                .expectNext("A","B", "C","D","E","F")
                .verifyComplete();

    }

    @Test
    void explore_merge() {


        //given

        //when
        var concatFlux = fluxAndMonoGeneratorService.explore_merge();
        //then
        StepVerifier.create(concatFlux)
                .expectNext("A","B", "C","D","E","F")
                .verifyComplete();

    }

    @Test
    void explore_mergeSequence() {


        //given

        //when
        var concatFlux = fluxAndMonoGeneratorService.explore_mergeSequential();
        //then
        StepVerifier.create(concatFlux)
                .expectNext("A","B", "C","D","E","F")
                .verifyComplete();

    }
}