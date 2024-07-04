package one.moonx.navigation.convert;

import one.moonx.navigation.pojo.dto.UserDTO;
import one.moonx.navigation.pojo.entity.User;
import one.moonx.navigation.pojo.vo.UserVO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserConvert {
    User convert(UserDTO userDTO);

    UserDTO convertDTO(User user);

    UserVO convert(User user);

    List<UserVO> convertVO(List<User> users);
}
