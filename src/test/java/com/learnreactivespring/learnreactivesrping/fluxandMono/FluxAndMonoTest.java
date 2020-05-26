package com.learnreactivespring.learnreactivesrping.fluxandMono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {
	@Test
	public void fluxTest() {
		Flux<String> stringFlux = Flux.just("Spring", "SpringBoot", "Reactor Spring")
						.concatWith(Flux.error(new RuntimeException("This is an error!")))
						.concatWith(Flux.just("after error"))
						.log();

		stringFlux
						.subscribe(System.out::println,
										e -> System.err.println(e),
										() -> System.out.println("Completed!"));
	}

	@Test
	public void fluxTestElements_WithoutErrors() {
		Flux<String> stringFlux = Flux.just("Spring", "SpringBoot", "Reactor Spring")
						.log();

		StepVerifier.create(stringFlux)
						.expectNext("Spring")
						.expectNext("SpringBoot")
						.expectNext("Reactor Spring")
						.verifyComplete();
	}

	@Test
	public void fluxTestElements_withErrors() {
		Flux<String> stringFlux = Flux.just("Spring", "SpringBoot", "Reactor Spring")
						.concatWith(Flux.error(new RuntimeException("This is an error")))
						.log();

		StepVerifier.create(stringFlux)
						.expectNext("Spring", "SpringBoot", "Reactor Spring")
						.expectError(RuntimeException.class)
						.verify();
	}

	@Test
	public void fluxTestElements_count() {
		Flux<String> stringFlux = Flux.just("Spring", "SpringBoot", "Reactor Spring")
						.concatWith(Flux.error(new RuntimeException("This is an error")))
						.log();

		StepVerifier.create(stringFlux)
						.expectNextCount(3)
						.expectErrorMessage("This is an error")
						.verify();
	}

	@Test
	public void monoTest() {
		Mono<String> stringMono = Mono.just("Spring");

		StepVerifier.create(stringMono.log())
						.expectNext("Spring")
						.verifyComplete();
	}

	@Test
	public void monoTest_withError() {
		StepVerifier.create(Mono.error(new RuntimeException("This is an error")))
						.expectError(RuntimeException.class)
						.verify();
	}
}
