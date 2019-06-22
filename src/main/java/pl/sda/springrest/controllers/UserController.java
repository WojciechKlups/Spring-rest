package pl.sda.springrest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.sda.springrest.model.dto.UserDto;
import pl.sda.springrest.model.dto.UserListDto;
import pl.sda.springrest.services.UserService;

import java.util.List;

@RestController
@RequestMapping(UserController.BASE_URL)
public class UserController {

   public static final String BASE_URL = "/api/v1/users";

   private UserService userService;

   @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public UserListDto getAllUsers(){
        List<UserDto> allUsers = userService.getAllUsers();
        return new UserListDto(allUsers);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUserById(@PathVariable Long id){
       return userService.getUserById(id);
    }
}
