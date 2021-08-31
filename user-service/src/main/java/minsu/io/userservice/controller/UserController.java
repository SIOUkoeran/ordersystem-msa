package minsu.io.userservice.controller;


import lombok.extern.slf4j.Slf4j;


import minsu.io.userservice.service.UserService;

import minsu.io.userservice.vo.ResponseUser;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.web.reactive.context.ReactiveWebServerApplicationContext;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/")
@Slf4j
@PreAuthorize("permitAll()")
public class UserController {


    private UserService userService;
    private ReactiveWebServerApplicationContext context;
    private Environment env;



    @Autowired
    public UserController(UserService userService, ReactiveWebServerApplicationContext context, Environment env) {
        this.userService = userService;
        this.context = context;
        this.env = env;
    }

    @GetMapping("/welcome")
    public Flux<String> welcome(){


        return Flux.just("It's working on "+context.getWebServer().getPort());
    }

    @GetMapping("/health-check")
    public Flux<String> status(){


        return Flux.just("It's working" +
                ", port(local.server.port) = " + env.getProperty("local.server.port") +
                ", port(server.port) = " + env.getProperty("server.port") +
                ", with token secret = " + env.getProperty("springbootwebfluxjjwt.jjwt.secret") +
                ", with token time = " + env.getProperty("springbootwebfluxjjwt.jjwt.expiration"));
    }





    @GetMapping(value="/users")
    public Flux<ResponseUser> findAllUser(){





        return this.userService.getUsers()
                .map(user -> ResponseUser.builder()
                        .name(user.getName())
                        .email(user.getEmail())
                        .userId(user.getId()).build());


    }
    @GetMapping(value="/users/{userId}")
    public Mono<ResponseEntity<ResponseUser>> findUser(@PathVariable("userId") String userId){


        return this.userService.getUserByUserId(userId)
                                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Users not Found")))
                                    .map(userDto -> ResponseUser.builder()
                                                    .name(userDto.getName())
                                                    .email(userDto.getEmail())
                                                    .userId(userDto.getUserId())
                                                    .orderList(userDto.getOrderList())
                                                    .build())
                                    .map(ResponseEntity::ok);







    }


}
