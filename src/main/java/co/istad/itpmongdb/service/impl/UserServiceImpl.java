package co.istad.itpmongdb.service.impl;

import co.istad.itpmongdb.domain.User;
import co.istad.itpmongdb.dto.CreateUserDto;
import co.istad.itpmongdb.dto.FilterDto;
import co.istad.itpmongdb.dto.UserRequest;
import co.istad.itpmongdb.dto.UserResponse;
import co.istad.itpmongdb.filter.FilteringFactory;
import co.istad.itpmongdb.mapper.UserMapper;
import co.istad.itpmongdb.repository.UserRepository;
import co.istad.itpmongdb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public Page<UserResponse> filterUsers(FilterDto filterDto, int page, int size){

        Sort sortByName = Sort.by(Sort.Direction.ASC, "name");
        Pageable pageable = PageRequest.of(page, size, sortByName);

        Page<User> filteredUsers = userRepository.findAllWithFilter(User.class,
                FilteringFactory.parseFromParams(filterDto.filter(), User.class), pageable);

        return filteredUsers.map(userMapper::toUserResponse);
    }

    @Override
    public Page<UserResponse> findAll(int page, int size) {
        Sort sortByName = Sort.by(Sort.Direction.ASC, "name");
        Pageable pageable = PageRequest.of(page, size, sortByName);
        Page<User> users = userRepository
                .findAll(pageable);
        return users.map(userMapper::toUserResponse);
    }

    @Override
    public UserResponse findById(String id) {
        User users = userRepository.findById(id).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("User with id %s not found!",id)));
        return userMapper.toUserResponse(users);
    }

    @Override
    public UserResponse createUser(CreateUserDto createUserDto) {

        User user = userMapper.fromCreateUserDto(createUserDto);
        User savedUser = userRepository.save(user);

        return userMapper.toUserResponse(savedUser);
    }


    @Override
    public UserRequest updateUserById(String id, UserRequest userRequest) {
        User existingUser = userRepository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("User with id %s not found!",id)));
        existingUser.setName(userRequest.name());
        existingUser.setUsername(userRequest.username());
        existingUser.setEmail(userRequest.email());
        User updateUser = userRepository.save(existingUser);
        return new UserRequest(
                updateUser.getName(),
                updateUser.getUsername(),
                updateUser.getEmail());
    }

    @Override
    public String deleteUserById(String id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("User with id %s not found!",id)));
        userRepository.delete(user);
        return "User with id " + id + " deleted successfully!";
    }

}
