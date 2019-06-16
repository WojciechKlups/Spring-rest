package pl.sda.springrest.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.sda.springrest.model.User;
import pl.sda.springrest.model.dto.UserDto;


@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);

}
