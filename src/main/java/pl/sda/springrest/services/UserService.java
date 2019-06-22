package pl.sda.springrest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.springrest.mappers.UserMapper;
import pl.sda.springrest.model.User;
import pl.sda.springrest.model.dto.UserDto;
import pl.sda.springrest.repositories.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream()
                .map(user -> userMapper.userToUserDto(user))
                .collect(Collectors.toList());
        return userDtos;
    }

    public UserDto getUserByEmail(String email){
        UserDto userDto = userRepository.findUserByEmail(email)
                .map(user -> userMapper.userToUserDto(user))
                .orElseThrow(ResourceNotFoundException::new);

        return userDto;
    }

    public UserDto getUserById(Long id){

        UserDto userDto = userRepository.findById(id)
                .map(user -> userMapper.userToUserDto(user))
                .orElseThrow(ResourceNotFoundException::new);

        return userDto;
    }

    public void deleteById(Long id){
       userRepository.deleteById(id);
    }

    public UserDto createNewUser(UserDto userDto){
        User newUserToSave = userMapper.userDtoToUser(userDto);
        userRepository.save(newUserToSave);
        UserDto userDtoSaved = userMapper.userToUserDto(newUserToSave);
        return userDtoSaved;
    }

    public UserDto updateUser(UserDto userDto, Long id){
        User user = userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        User mappedUser = userMapper.userDtoToUser(userDto);
        mappedUser.setId(id);
        mappedUser.setPassword(user.getPassword());
        mappedUser.setPersonalIdNumber(user.getPersonalIdNumber());

        User savedUser = userRepository.save(mappedUser);
        return userMapper.userToUserDto(savedUser);
    }
}
