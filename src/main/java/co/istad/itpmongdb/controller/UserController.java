package co.istad.itpmongdb.controller;

import co.istad.itpmongdb.base.BaseError;
import co.istad.itpmongdb.base.BaseRest;
import co.istad.itpmongdb.dto.UserRequest;
import co.istad.itpmongdb.dto.UserResponse;
import co.istad.itpmongdb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public BaseRest<?> findAllUser(){
        var userList = userService.findAll();
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User find successfully!")
                .timestamp(LocalDateTime.now())
                .data(userList)
                .build();
    }

    @GetMapping("/{id}")
    public BaseRest<?> findById(@PathVariable("id") String id){
        UserResponse user = userService.findById(id);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message(String.format("User with id { %s } founded", id))
                .timestamp(LocalDateTime.now())
                .data(user)
                .build();
    }
// create user ====
    @PostMapping
    public BaseRest<?> createUser(@RequestBody UserRequest userRequest){
        UserRequest userRequest1 = userService.createUser(userRequest);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.CREATED.value())
                .message("User created successfully!")
                .timestamp(LocalDateTime.now())
                .data(userRequest1)
                .build();
    }
// update user by id ====
//
}
