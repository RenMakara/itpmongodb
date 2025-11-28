package co.istad.itpmongdb.service;

import co.istad.itpmongdb.dto.UserRequest;
import co.istad.itpmongdb.dto.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> findAll();

    UserResponse findById(String id);

    UserRequest createUser(UserRequest userRequest);

}
