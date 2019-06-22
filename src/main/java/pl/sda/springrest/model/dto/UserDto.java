package pl.sda.springrest.model.dto;


import lombok.Builder;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String firstname;
    private String lastname;

    private int age;
    private String gender;
    private String email;
}
