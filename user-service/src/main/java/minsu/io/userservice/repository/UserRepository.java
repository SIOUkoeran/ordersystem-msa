package minsu.io.userservice.repository;


import minsu.io.userservice.domain.User;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
public interface UserRepository extends ReactiveCrudRepository<User, String> {

    Mono<User> findByName(String userName);


    Mono<User> findByEmail(String email);

}
