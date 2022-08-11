package com.github.miyohide;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> elements = Main.fluxMethod01(Flux.just(1, 2, 3, 4));
        System.out.println(elements);
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
}
