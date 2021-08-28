package minsu.io.userservice.authorize;


import antlr.preprocessor.Preprocessor;
import antlr.preprocessor.PreprocessorTokenTypes;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.netflix.discovery.converters.Auto;
import minsu.io.userservice.domain.User;
import minsu.io.userservice.repository.UserRepository;
import minsu.io.userservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

import static com.ctc.wstx.shaded.msv_core.grammar.Expression.anyString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.data.mongodb.core.validation.Validator.document;

@WebFluxTest
public class authorizeTest {

    @Autowired
    UserRepository repository;
    @Autowired
    WebTestClient client;

    @MockBean
    UserService service;

    @PostConstruct
    public void setUp() {

        User userTest = User.builder()
                .name("userName")
                .email("userEmail")
                .id("userId")
                .build();
        String Role = "ROLE_USER";
        Mockito.when(this.service.findByUserEmail(anyString())).thenReturn(Mono.just(userTest));
    }
    @Test
    void setHeaderAfterJwtAccessingTest(){
        User userTest = User.builder()
                .name("userName")
                .email("userEmail")
                .id("userId")
                .build();

        client.post()
                .uri("/loginUp")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(BodyInserters.fromObject(userTest)))
                .exchange()
                .expectStatus().isOk();








    }





}
