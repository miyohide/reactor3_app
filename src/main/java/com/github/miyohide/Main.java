package com.github.miyohide;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ConnectableFlux<Object> publish = hotStreams();
        publish.connect();
    }

    public static List<Integer> fluxMethod01(Flux<Integer> f) {
        List<Integer> elements = new ArrayList<>();
        f.log().subscribe(elements::add);
        return elements;
    }

    public static List<Integer> fluxMethodWithImplementSubscriber(Flux<Integer> f) {
        List<Integer> elements = new ArrayList<>();
        f.log().subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }
            @Override
            public void onNext(Integer integer) {
                elements.add(integer);
            }
            @Override
            public void  onError(Throwable t) {}
            @Override
            public void onComplete() {}
        });
        return elements;
    }

    public static List<Integer> backpressure01(Flux<Integer> f) {
        List<Integer> elements = new ArrayList<>();
        f.log().subscribe(new Subscriber<Integer>() {
            private Subscription s;
            int onNextAmount;
            @Override
            public void onSubscribe(Subscription s) {
                this.s = s;
                s.request(2);
            }

            @Override
            public void onNext(Integer integer) {
                elements.add(integer);
                onNextAmount++;
                if (onNextAmount % 2 == 0) {
                    s.request(2);
                }
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onComplete() {
            }
        });

        return elements;
    }

    public static List<Integer> useMap(Flux<Integer> f) {
        List<Integer> elements = new ArrayList<>();
        f.log().map(i -> i * 2).subscribe(elements::add);
        return elements;
    }

    public static List<String> combiningTwoStreams(Flux<Integer> f) {
        List<String> elements = new ArrayList<>();

        f.log().map(i -> i * 2)
                .zipWith(Flux.range(0, Integer.MAX_VALUE),
                        (one, two) -> String.format("First Flux: %d, Second Flux: %d", one, two))
                .subscribe(elements::add);
        return elements;
    }

    public static ConnectableFlux<Object> hotStreams() {
        ConnectableFlux<Object> publish = Flux.create(fluxSink -> {
            while (true) {
                fluxSink.next(System.currentTimeMillis());
            }
        })
                .sample(Duration.ofSeconds(2))
                .publish();
        publish.subscribe(System.out::println);
        publish.subscribe(System.out::println);
        return publish;
    }

    public static List<Integer> concurrency(Flux<Integer> f) {
        List<Integer> elements = new ArrayList<>();
        f.log().map(integer -> integer * 2)
                .subscribeOn(Schedulers.parallel())
                .subscribe(elements::add);

        return elements;
    }
}
