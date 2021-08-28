package minsu.io.userservice.controller;


import com.netflix.discovery.converters.Auto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minsu.io.userservice.domain.User;
import minsu.io.userservice.dto.UserDto;
import minsu.io.userservice.security.jwt.JWTUtil;
import minsu.io.userservice.security.password.PBKDF2Encoder;
import minsu.io.userservice.service.UserService;
import minsu.io.userservice.vo.AuthRequest;
import minsu.io.userservice.vo.AuthResponse;
import minsu.io.userservice.vo.RequestUser;
import minsu.io.userservice.vo.ResponseUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.function.Consumer;

@RestController
@Slf4j
@RequestMapping("/")
public class LoginController {

    private JWTUtil jwtUtil;
    private PBKDF2Encoder passwordEncoder;
    private UserService userService;

    @Autowired
    public LoginController(JWTUtil jwtUtil, PBKDF2Encoder passwordEncoder, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping(value = "/login")
    @CrossOrigin
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest ar){




        return this.userService.findByUserEmail(ar.getEmail())
                .filter(user -> passwordEncoder.encode(ar.getPassword()).equals(user.getPassword()))
                .map(userDetails-> new AuthResponse(jwtUtil.generateToken(userDetails), userDetails.getId()))
                .map(authResponse -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.set("Token", "Bearer "+authResponse.getToken());
                    headers.add("userId", authResponse.getUserId());
                    return ResponseEntity.ok().headers(headers).body(authResponse);
                })
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));

        // user -> response.header.add(new AuthResponse(jwt)
        //

    }
    @PostMapping(value = "/signUp")
    public Mono<ResponseEntity<ResponseUser>> createUser(@RequestBody Mono<RequestUser> requestUser){

        Mono<UserDto> userDto = requestUser.map(requestUser1 -> UserDto.builder()
                                                            .pwd(requestUser1.getPwd())
                                                            .email(requestUser1.getEmail())
                                                            .name(requestUser1.getName()).build());

        return userService.createUser(userDto)
                .map(responseUser -> ResponseEntity.status(HttpStatus.CREATED).body(responseUser));
    }
}
