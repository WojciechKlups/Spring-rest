package pl.sda.springrest.model.dto;


import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;

    private int age;
    private String gender;
}
