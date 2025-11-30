package co.istad.itpmongdb.service;

import co.istad.itpmongdb.dto.CreateUserDto;
import co.istad.itpmongdb.dto.UserRequest;
import co.istad.itpmongdb.dto.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> findAll();

    UserResponse findById(String id);

    UserResponse createUser(CreateUserDto createUserDto);

    UserRequest updateUserById(String id, UserRequest userRequest);

    String deleteUserById(String id);
}
