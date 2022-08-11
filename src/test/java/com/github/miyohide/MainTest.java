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
}