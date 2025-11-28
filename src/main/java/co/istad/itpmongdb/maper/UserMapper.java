package co.istad.itpmongdb.maper;

import co.istad.itpmongdb.domain.User;
import co.istad.itpmongdb.dto.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);
}
