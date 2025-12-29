package co.istad.itpmongdb.mapper;

import co.istad.itpmongdb.domain.User;
import co.istad.itpmongdb.dto.CreateUserDto;
import co.istad.itpmongdb.dto.UserRequest;
import co.istad.itpmongdb.dto.UserResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);
    User fromCreateUserDto(CreateUserDto createUserDto);

}
