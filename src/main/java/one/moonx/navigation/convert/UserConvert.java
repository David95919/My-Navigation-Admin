package one.moonx.navigation.convert;

import one.moonx.navigation.pojo.dto.UserDTO;
import one.moonx.navigation.pojo.dto.UserLoginDTO;
import one.moonx.navigation.pojo.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserConvert {
    UserDTO convert(User user);
    User convert(UserDTO userDTO);
    User convert(UserLoginDTO userDTO);
}
