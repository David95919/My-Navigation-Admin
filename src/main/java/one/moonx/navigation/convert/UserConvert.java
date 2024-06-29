package one.moonx.navigation.convert;

import one.moonx.navigation.pojo.dto.UserDTO;
import one.moonx.navigation.pojo.entity.User;
import one.moonx.navigation.pojo.vo.UserVO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserConvert {
    UserDTO convertDTO(User user);

    User convert(UserDTO userDTO);

    List<UserVO> convertVO(List<User> users);

    UserVO convert(User user);
}
