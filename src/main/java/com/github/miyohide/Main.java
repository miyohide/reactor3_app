package com.github.miyohide;

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
}
