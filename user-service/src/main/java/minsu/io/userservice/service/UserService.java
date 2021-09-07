package minsu.io.userservice.service;


import minsu.io.userservice.domain.User;
import minsu.io.userservice.dto.UserDto;


import minsu.io.userservice.vo.ResponseOrder;
import minsu.io.userservice.vo.ResponseUser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface UserService{
    Mono<ResponseUser> createUser(Mono<UserDto> userDto);
    Flux<User> getUsers();

    Mono<UserDto> getUserByUserId(String userId);

    Flux<ResponseOrder> getOrders(String orderUrl);
    Mono<User> findByUserEmail(String email);


}
