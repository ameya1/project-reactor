package org.reative;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;

@Log4j2
public class Main {

    public Flux<String> flux() {
        return Flux.fromIterable(List.of("Ferrari", "Mercedes", "BMW"))
                .map(String::toUpperCase)
                .log();
    }

    public Flux<String> filterMap(int length) {
        return Flux.fromIterable(List.of("Ferrari", "Mercedes", "BMW"))
                .filter(car -> car.length() > length)
                .map(String::toUpperCase);
    }

    public Flux<String> flat() {
        return Flux.fromIterable(List.of("Ferrari", "Mercedes", "BMW"))
                .flatMap(car -> Flux.just(car.split("")));
    }

    public Flux<String> flatDelay() {
        return Flux.fromIterable(List.of("Ferrari", "Mercedes", "BMW"))
                .flatMap(car -> Flux.just(car.split("")))
                .delayElements(Duration.ofMillis(1)).log();
    }


    public Mono<String> mono() {
        return Mono.just("Bugati")
                .map(String::toUpperCase)
                .log();
    }

    public Flux<String> onMethods() {
        return Flux.fromIterable(List.of("Ferrari", "Mercedes", "BMW"))
                .doOnSubscribe(subscription -> log.info("Subscribed "))
                .doOnRequest(consumer -> log.info("doOnRequest -> " + consumer))
                .doOnNext(consumer -> log.info("doOnNext -> " + consumer))
                .doOnComplete(() -> log.info("Completed"));
    }

    public Flux<Integer> request() {
        return Flux.range(1, 10);
    }

    public static void main(String[] args) {
        Main main = new Main();
        //main.flux().subscribe(car -> log.info("Flux -> " + car));
        //main.mono().subscribe(car -> log.info("Mono -> " + car));

        //main.filterMap(6).subscribe(car -> log.info(car));

        //main.flat().subscribe(car -> System.out.print(car + " "));

        //main.flatDelay().subscribe(car -> log.info(car + " "));

        //main.onMethods().subscribe(car -> log.info(car));
        main.request().subscribe(num -> log.info(num), null, null, subscription -> subscription.request(3));
    }
}