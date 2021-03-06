package minsu.io.userservice.service;


import lombok.extern.slf4j.Slf4j;
import minsu.io.userservice.domain.User;
import minsu.io.userservice.dto.UserDto;
import minsu.io.userservice.repository.UserRepository;

import minsu.io.userservice.security.password.PBKDF2Encoder;


import minsu.io.userservice.vo.ResponseOrder;
import minsu.io.userservice.vo.ResponseOrderList;
import minsu.io.userservice.vo.ResponseUser;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;


@Service
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository repository;

    private final PBKDF2Encoder passwordEncoder;
    private final Environment env;
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public UserServiceImpl(UserRepository repository, PBKDF2Encoder passwordEncoder, Environment env, WebClient.Builder webClientBuilder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.env = env;
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public Mono<ResponseUser> createUser(Mono<UserDto> userDto) {

//
//        return userDto.flatMap(userDto1 -> this.repository.findByEmail(userDto1.getEmail()))
//
//                .map(user-> ResponseUser.builder().userId(user.getId())
//                                                    .email(user.getEmail())
//                                                    .name(user.getName())
//                .orderList(new ArrayList<>())
//                .build())
//                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found")));

//
        return userDto.map(userDto1 -> User.builder()
                .name(userDto1.getName())
                .email(userDto1.getEmail())
                .passWord(passwordEncoder.encode(userDto1.getPwd()))
                .createdAt(DateTime.now())
                .enabled(true)
                .build())
                .flatMap(user1 -> this.repository.save(user1))
                .map(user -> ResponseUser.builder()
                                            .userId(user.getId())
                                            .email(user.getEmail())
                                            .name(user.getName()).build());
    }

    @Override
    public Flux<User> getUsers() {


        return this.repository.findAll();
    }
    //String email, String pwd, String name, DateTime createdAt, String encryptedPwd

    @Override
    public Flux<ResponseOrder> getOrders(String orderUrl) {


        return webClientBuilder.build()
                .get()
                .uri(orderUrl)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(ResponseOrder.class)
                .switchIfEmpty(Flux.empty());
    }

    @Override
    public Mono<UserDto> getUserByUserId(String userId) {


        String orderUrl = String.format(Objects.requireNonNull(env.getProperty("order_service.url")), userId);


        return this.repository.findById(userId)
                .flatMap(user1 -> getOrders(orderUrl).collectList()
                                    .map(orderList -> UserDto.builder()
                                    .email(user1.getEmail())
                                    .pwd(user1.getPassword())
                                    .createdAt(user1.getCreatedAt())
                                    .name(user1.getName())
                                    .orderList(orderList)
                                    .build()));




    }



    @Override
    public Mono<User> findByUserEmail(String email) {

        return this.repository.findByEmail(email)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not Found")));
    }
}
