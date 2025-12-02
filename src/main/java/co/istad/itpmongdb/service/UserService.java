package co.istad.itpmongdb.service;

import co.istad.itpmongdb.dto.CreateUserDto;
import co.istad.itpmongdb.dto.FilterDto;
import co.istad.itpmongdb.dto.UserRequest;
import co.istad.itpmongdb.dto.UserResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    Page<UserResponse> filterUsers(FilterDto filterDto, int page, int size);

    Page<UserResponse> findAll(int page, int size);

    UserResponse findById(String id);

    UserResponse createUser(CreateUserDto createUserDto);

    UserRequest updateUserById(String id, UserRequest userRequest);

    String deleteUserById(String id);
}
