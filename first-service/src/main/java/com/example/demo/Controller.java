package com.example.demo;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.context.ReactiveWebServerApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServerRequest;

@RestController
@Slf4j
@RequestMapping("/first-service/")
public class Controller {


    @Autowired
    private ReactiveWebServerApplicationContext context;


    @GetMapping("/welcome")
    public Mono<String> welcome(){
        return Mono.just("Welcome");

    }
    @GetMapping("/message")
    public Mono<String> message(@RequestHeader("first-request") String header){
        log.info(header);
        return Mono.just(header);
    }

    @GetMapping("/check")
    public Flux<String> check(){

        return Flux.just("Hi, there is a message from first-service port"+context.getWebServer().getPort());
    }
}
