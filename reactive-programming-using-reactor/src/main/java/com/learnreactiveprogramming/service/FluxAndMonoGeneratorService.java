package com.learnreactiveprogramming.service;

import lombok.var;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;


public class FluxAndMonoGeneratorService {

    public Flux<String>namesFlux() {

        return Flux.fromIterable(Arrays.asList("alex", "ben", "chloe"))
                .log(); // db or a remote service call
    }


    public Flux<String>namesFlux_map() {

        return Flux.fromIterable(Arrays.asList("alex", "ben", "chloe"))
                .map(String::toUpperCase)
//                .map(s -> s.toUpperCase())
                .log(); // db or a remote service call
    }

    public Flux<String>namesFlux_map(int stringLength) {

        return Flux.fromIterable(Arrays.asList("alex", "ben", "chloe"))
                .map(String::toUpperCase)
//                .map(s -> s.toUpperCase())
                .filter(s -> s.length() > stringLength)
                .map(s -> s.length()+ "-"+s)
                .log(); // db or a remote service call
    }


    public Flux<String>namesFlux_flatmap(int stringLength) {
    // filter the string whose length is greater than 3
        return Flux.fromIterable(Arrays.asList("alex", "ben", "chloe"))
                .map(String::toUpperCase)
//                .map(s -> s.toUpperCase())
                .filter(s -> s.length() > stringLength)
//                .map(s -> s.length()+ "-"+s)
                .flatMap(s -> splitString(s)) //A,L,E,X,C,H,L,O,E
                .log(); // db or a remote service call
    }

    public Flux<String>namesFlux_transform(int stringLength) {

        Function<Flux<String>,Flux<String>>filtermap = name -> name.map(String::toUpperCase)
                .filter(s -> s.length() > stringLength);

        // filter the string whose length is greater than 3
        return Flux.fromIterable(Arrays.asList("alex", "ben", "chloe"))
                .transform(filtermap)
                .flatMap(s -> splitString(s)) //A,L,E,X,C,H,L,O,E
                .defaultIfEmpty("default")
                .log(); // db or a remote service call
    }

    public Flux<String>namesFlux_transform_switchIfEmpty(int stringLength) {

        Function<Flux<String>,Flux<String>>filtermap = name -> name.map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(s -> splitString(s));

        var defaultFlux = Flux.just("default")
                .transform(filtermap); // "D","E","F","A","U","L","T"

        // filter the string whose length is greater than 3
        return Flux.fromIterable(Arrays.asList("alex", "ben", "chloe"))
                .transform(filtermap)
                //A,L,E,X,C,H,L,O,E
                .switchIfEmpty(defaultFlux)
                .log(); // db or a remote service call
    }


    public Flux<String>namesFlux_concatmap(int stringLength) {
        // filter the string whose length is greater than 3
        return Flux.fromIterable(Arrays.asList("alex", "ben", "chloe"))
                .map(String::toUpperCase)
//                .map(s -> s.toUpperCase())
                .filter(s -> s.length() > stringLength)
//                .map(s -> s.length()+ "-"+s)
                .concatMap(s -> splitString_withDelay(s))
//                .flatMap(s -> splitString(s)) //A,L,E,X,C,H,L,O,E
                .log(); // db or a remote service call
    }

    public Flux<String>namesFlux_flatmap_async(int stringLength) {
        // filter the string whose length is greater than 3
        return Flux.fromIterable(Arrays.asList("alex", "ben", "chloe"))
                .map(String::toUpperCase)
//                .map(s -> s.toUpperCase())
                .filter(s -> s.length() > stringLength)
//                .map(s -> s.length()+ "-"+s)
                .flatMap(s -> splitString_withDelay(s)) //A,L,E,X,C,H,L,O,E
                .log(); // db or a remote service call
    }

    public Flux<String> explore_concat() {

        var abcFlux = Flux.just("A","B", "C");

        var defFlux = Flux.just("D","E","F");

        return Flux.concat(abcFlux,defFlux);

    }

    public Flux<String> explore_concatwith() {

        var abcFlux = Flux.just("A");

        var defFlux = Flux.just("B");

        return abcFlux.concatWith(defFlux).log(); // A, B

    }


    public Flux<String> explore_merge() {

        var abcFlux = Flux.just("A","B", "C")
                .delayElements(Duration.ofMillis(100));

        var defFlux = Flux.just("D","E","F")
                .delayElements(Duration.ofMillis(100));

        return Flux.merge(abcFlux,defFlux);

    }


    public Flux<String> explore_mergeWith() {

        var abcFlux = Flux.just("A","B", "C")
                .delayElements(Duration.ofMillis(100));

        var defFlux = Flux.just("D","E","F")
                .delayElements(Duration.ofMillis(100));

        return abcFlux.mergeWith(defFlux).log();

    }


    public Flux<String> explore_mergeSequential() {

        var abcFlux = Flux.just("A","B", "C")
                .delayElements(Duration.ofMillis(100));

        var defFlux = Flux.just("D","E","F")
                .delayElements(Duration.ofMillis(100));

        return Flux.mergeSequential(abcFlux, defFlux).log();

    }

    // ALEX -> Flux(A,L,E,X)
    public Flux<String> splitString(String name) {

        var charArray= name.split("");
        return Flux.fromArray(charArray);
    }

    public Flux<String> splitString_withDelay(String name) {

        var charArray= name.split("");
        var delay = new Random().nextInt(1000);
        return Flux.fromArray(charArray)
                .delayElements(Duration.ofMillis(delay));
    }

    public Flux<String>namesFlux_immutability() {

        var namesFlux = Flux.fromIterable(Arrays.asList("alex", "ben", "chloe"));
        namesFlux.map(String::toUpperCase);
        return namesFlux;
    }

    public Flux<String>explore_zip() {
        var abcFlux = Flux.just("A","B", "C");

        var defFlux = Flux.just("D","E","F");

        return Flux.zip(abcFlux,defFlux, (first,second) -> first + second);
    }

    public Flux<String>explore_zip_1() {
        var abcFlux = Flux.just("A","B", "C");

        var defFlux = Flux.just("D","E","F");

        var _123Flux = Flux.just("1","2","3");

        var _456Flux = Flux.just("4","5","6");

        return Flux.zip(abcFlux,defFlux,_123Flux,_456Flux)
                .map(t4 -> t4.getT1() + t4.getT2() + t4.getT3() + t4.getT4())
                .log();
    }

    Mono<String> explore_mergeZipWith_mono() {
        var aMono = Mono.just("A");
        var bMono = Mono.just("B");
        return aMono.zipWith(bMono)
                .map(t2 -> t2.getT1() + t2.getT2())
                .log(); // a,b
    }

    public Mono<String>namesMono_map_filter(int stringLength) {
        return Mono.just("alex").map(String::toUpperCase)
                .filter(s -> s.length() > stringLength);
    }

    public Mono<List<String>>namesMono_flatMap(int stringLength) {
        return Mono.just("alex").map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(this::splitStringMono);
    }

    public Flux<String> namesMono_flatMapMany(int stringLength) {
        return Mono.just("alex").map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMapMany(this::splitString)
                .log();
    }

    private Mono<List<String>>splitStringMono(String s){
        var charArray = s.split("");
        var charList = Arrays.asList(charArray);
        return Mono.just(charList);
//        return null;
    }

    public Mono<String> nameMono() {

        return Mono.just("alex").log();
    }

    public static void main(String[] args) {

        FluxAndMonoGeneratorService service = new FluxAndMonoGeneratorService();
        service.namesFlux().subscribe(name -> System.out.println("Flux Name is: " +name));

        service.nameMono()
                .subscribe(name -> System.out.println("Mono Name is: " +name));

    }
}
