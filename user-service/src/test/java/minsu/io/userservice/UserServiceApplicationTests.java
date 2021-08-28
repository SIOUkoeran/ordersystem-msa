package minsu.io.userservice;

import minsu.io.userservice.domain.User;
import minsu.io.userservice.dto.UserDto;
import minsu.io.userservice.repository.UserRepository;
import minsu.io.userservice.service.UserService;
import minsu.io.userservice.vo.AuthRequest;
import minsu.io.userservice.vo.RequestUser;
import org.assertj.core.api.AssertionInfo;
import org.assertj.core.api.Assertions;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.assertj.AssertableReactiveWebApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;


@DataMongoTest
class UserServiceApplicationTests {

    
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    
    @Test
    void contextLoads() {

        RequestUser requestUser = new RequestUser("Asda","asda","asdaa");
        UserDto userDto = UserDto.builder()
                .email(requestUser.getEmail())
                .name(requestUser.getName())
                .pwd(requestUser.getPwd()).build();
        
        
        User user =User.builder()
                .id("a")
                .name(userDto.getName())
                .email(userDto.getEmail())
                .passWord(passwordEncoder.encode(userDto.getPwd()))
                .createdAt(DateTime.now())
                .build();
        
        userRepository.save(user)
                .as(StepVerifier::create)
                .expectNextMatches(user1 -> {
                    Assertions.assertThat(user1.getId()).isNotNull();
                    Assertions.assertThat(user1.getName()).isEqualTo("asdaa");


                    System.out.println("user1.toString() = " + user1.toString());
                    System.out.println("user1.getId().toString() = " + user1.getId().toString());

                    return true;
                })
                .verifyComplete();
        AuthRequest auth = new AuthRequest("Asda","asda");
        this.userRepository.findByEmail(auth.getEmail())
                .as(StepVerifier::create)
                .expectNextMatches(
                        user11 ->{
                            Assertions.assertThat(user11.getPassword()).isEqualTo(passwordEncoder.encode(auth.getPassword()));
                            System.out.println("user.toString() = " + user11.toString());
                            return true;
                        }
                ).verifyComplete();


    }


    @Test
    void findById(){

        User user1 =User.builder()
                .id("a")
                .name("userDto.getName()")
                .email("userDto.getEmail()")
                .passWord("userDto.getPwd()")
                .build();

        this.userRepository.save(user1).subscribe();



    }

    @Test
    void checkFindEmailService(){

        AuthRequest auth = new AuthRequest("test","test");
        System.out.println("this.userRepository.findByEmail(auth.getEmail()) = "
                + this.userRepository.findByEmail(auth.getEmail()).toString());

            }

}
