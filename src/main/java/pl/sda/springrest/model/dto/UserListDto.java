package pl.sda.springrest.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserListDto {

    List<UserDto> userDtoList;
}
