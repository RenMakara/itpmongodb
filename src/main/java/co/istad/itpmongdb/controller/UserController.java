package co.istad.itpmongdb.controller;

import co.istad.itpmongdb.base.BaseRest;
import co.istad.itpmongdb.dto.CreateUserDto;
import co.istad.itpmongdb.dto.FilterDto;
import co.istad.itpmongdb.dto.UserRequest;
import co.istad.itpmongdb.dto.UserResponse;
import co.istad.itpmongdb.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/filter")
    public BaseRest<?> filterUsers(@RequestBody FilterDto filterDto,
                                   @RequestParam(required = false, defaultValue = "0") int page,
                                   @RequestParam(required = false, defaultValue = "25") int size
                                   ){

        var filterUser = userService.filterUsers(filterDto, page, size);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User find successfully!")
                .timestamp(LocalDateTime.now())
                .data(filterUser)
                .build();
    }

    @GetMapping
    public BaseRest<?> findAllUser(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "25") int size
    ){

        var userList = userService.findAll(page, size);
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
                .message(String.format("User with id %s founded", id))
                .timestamp(LocalDateTime.now())
                .data(user)
                .build();
    }

    // create user ====
    @PostMapping
    public BaseRest<?> createUser(@RequestBody @Valid CreateUserDto createUserDto){
        UserResponse userResponse = userService.createUser(createUserDto);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.CREATED.value())
                .message("User created successfully!")
                .timestamp(LocalDateTime.now())
                .data(userResponse)
                .build();
    }

    // update user by id ====
    @PutMapping("/{id}")
    public BaseRest<?> updateUser(@PathVariable("id") String id, @Valid @RequestBody UserRequest request){
        var updateUser = userService.updateUserById(id, request);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Update user successfully!")
                .timestamp(LocalDateTime.now())
                .data(updateUser)
                .build();
    }

// delete user by id ====
    @DeleteMapping("/{id}")
    public BaseRest<?> deleteUser(@PathVariable("id") String id){
        String massage = userService.deleteUserById(id);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.NO_CONTENT.value())
                .message("User deleted!")
                .timestamp(LocalDateTime.now())
                .data(massage)
                .build();
    }
}
