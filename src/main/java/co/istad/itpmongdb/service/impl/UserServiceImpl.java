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


    /**
     * Filters the users based on the provided filter criteria and returns a paginated
     * list of users sorted by their name in ascending order.
     *
     * @param filterDto the filter criteria used to filter the users
     * @param page the page number to retrieve (0-based index)
     * @param size the number of records per page
     * @return a paginated list of filtered users mapped to UserResponse objects
     */
    public Page<UserResponse> filterUsers(FilterDto filterDto, int page, int size){

        Sort sortByName = Sort.by(Sort.Direction.ASC, "name");
        Pageable pageable = PageRequest.of(page, size, sortByName);

        Page<User> filteredUsers = userRepository.findAllWithFilter(User.class,
                FilteringFactory.parseFromParams(filterDto.filter(), User.class), pageable);

        return filteredUsers.map(userMapper::toUserResponse);
    }

    /**
     * Retrieves a paginated list of all users, sorted by name in ascending order.
     *
     * @param page the page number to retrieve (0-based index).
     * @param size the number of items per page.
     * @return a {@code Page} of {@code UserResponse} containing user data.
     */
    @Override
    public Page<UserResponse> findAll(int page, int size) {
        Sort sortByName = Sort.by(Sort.Direction.ASC, "name");
        Pageable pageable = PageRequest.of(page, size, sortByName);
        Page<User> users = userRepository
                .findAll(pageable);
        return users.map(userMapper::toUserResponse);
    }

    /**
     *
     */
    @Override
    public UserResponse findById(String id) {
        User users = userRepository.findById(id).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("User with id %s not found!",id)));
        return userMapper.toUserResponse(users);
    }

    /**
     * Creates a new user based on the provided data and saves it to the repository.
     *
     * @param createUserDto the data transfer object containing the details required to create a new user
     * @return a UserResponse object containing the details of the newly created user
     */
    @Override
    public UserResponse createUser(CreateUserDto createUserDto) {

        User user = userMapper.fromCreateUserDto(createUserDto);
        User savedUser = userRepository.save(user);

        return userMapper.toUserResponse(savedUser);
    }


    /**
     * Updates the details of an existing user identified by their unique ID.
     *
     * @param id the unique identifier of the user to update
     * @param userRequest the data transfer object containing the new details of the user
     * @return the updated user details wrapped in a {@code UserRequest} object
     * @throws ResponseStatusException if the user with the specified ID does not exist
     */
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

    /**
     * Deletes a user identified by their unique ID.
     *
     * @param id the unique identifier of the user to delete
     * @return a confirmation message indicating the successful deletion of the user
     * @throws ResponseStatusException if the user with the specified ID does not exist
     */
    @Override
    public String deleteUserById(String id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("User with id %s not found!",id)));
        userRepository.delete(user);
        return "User with id " + id + " deleted successfully!";
    }

}
