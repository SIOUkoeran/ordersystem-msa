package apigateway.demo;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureWebTestClient
public class EndPointTest {


    @Autowired
    private WebTestClient client;

    @Test
    void WebTest(){
        client.get().uri("http://localhost:8000/first-service/welcome")
                .exchange()
                .expectStatus().isOk();
    }

}
