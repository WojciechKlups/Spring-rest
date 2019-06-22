package pl.sda.springrest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import pl.sda.springrest.model.dto.UserDto;
import pl.sda.springrest.model.dto.UserListDto;
import pl.sda.springrest.services.UserService;

import java.util.List;

@RestController
@RequestMapping(UserController.BASE_URL)
public class UserController {

   public static final String BASE_URL = "/api/v1";

   private UserService userService;

   @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public UserListDto getAllUsers(){
        List<UserDto> allUsers = userService.getAllUsers();
        return new UserListDto(allUsers);
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUserById(@PathVariable Long id){
       return userService.getUserById(id);
    }

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUserByEmail(@RequestParam String email){
       return userService.getUserByEmail(email);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createNewUser(@RequestBody UserDto userDto){
       return userService.createNewUser(userDto);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long id){
       userService.deleteById(id);
    }

    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUser(@RequestBody UserDto userDto, @PathVariable Long id) {
       return userService.updateUser(userDto,id);
    }
}
