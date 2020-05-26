package com.learnreactivespring.learnreactivesrping.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class FluxAndMonoController {

	@GetMapping("/flux")
	public Flux<Integer> flux() {
		return Flux.just(1,2,3,4)
						.log();
	}

	@GetMapping(value = "/fluxStream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<Integer> fluxStream() {
		return Flux.just(1,2,3,4)
						.delayElements(Duration.ofSeconds(1))
						.log();
	}
}
