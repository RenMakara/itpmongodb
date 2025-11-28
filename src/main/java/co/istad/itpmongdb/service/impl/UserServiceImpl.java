package co.istad.itpmongdb.service.impl;

import co.istad.itpmongdb.domain.User;
import co.istad.itpmongdb.dto.UserResponse;
import co.istad.itpmongdb.maper.UserMapper;
import co.istad.itpmongdb.repository.UserRepository;
import co.istad.itpmongdb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository UserRepository;
    private final UserMapper userMapper;
    @Override
    public List<UserResponse> findAll() {
        List<User> users = UserRepository
                .findAll();

        return users.stream().map(userMapper::toUserResponse).toList();
    }
}
