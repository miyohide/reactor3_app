package com.github.miyohide;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

class MainTest {

    @Test
    void fluxMethod01() {
        assertThat(Main.fluxMethod01(Flux.just(1, 2, 3, 4)), is(contains(1, 2, 3, 4)));
    }

    @Test
    void fluxMethodWithImplementSubscriber() {
        assertThat(
                Main.fluxMethodWithImplementSubscriber(Flux.just(1, 2, 3, 4, 5)),
                is(contains(1, 2, 3, 4, 5)));
    }

    @Test
    void backpressure01() {
        assertThat(
                Main.backpressure01(Flux.just(1, 2, 3, 4, 5)),
                is(contains(1, 2, 3, 4, 5)));
    }

    @Test
    void useMap() {
        assertThat(
                Main.useMap(Flux.just(1, 2, 3, 4, 5)),
                is(contains(2, 4, 6, 8, 10))
        );
    }

    @Test
    void combiningTwoStreams() {
        assertThat(
                Main.combiningTwoStreams(Flux.just(1, 2, 3, 4)),
                is(contains(
                        "First Flux: 2, Second Flux: 0",
                        "First Flux: 4, Second Flux: 1",
                        "First Flux: 6, Second Flux: 2",
                        "First Flux: 8, Second Flux: 3"
                        ))
        );
    }
}