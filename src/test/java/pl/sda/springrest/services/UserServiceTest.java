package pl.sda.springrest.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.sda.springrest.mappers.UserMapper;
import pl.sda.springrest.model.User;
import pl.sda.springrest.model.dto.UserDto;
import pl.sda.springrest.repositories.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private UserService userService;

    private static final String FIRSTNAME = "Janusz";
    private static final String LASTNAME = "Kowalski";
    private static final String EMAIL = "kowalski@gmail.com";
    private static final int AGE = 28;
    private static final Long ID = 129734918273L;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        userService = new UserService(userRepository, UserMapper.INSTANCE);
    }

    @Test
    public void getAllUsers() {
        //given
        List<User> users = Arrays.asList(new User(), new User(), new User());

        when(userRepository.findAll()).thenReturn(users);
        //when
        List<UserDto> userDtos = userService.getAllUsers();
        //then
        assertThat(userDtos.size(), equalTo(3));

    }

    @Test
    public void getUserByEmail() {
        //given
        User user = User.builder().firstname(FIRSTNAME).lastname(LASTNAME).email(EMAIL).age(AGE).build();

        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.ofNullable(user));

        //when
        UserDto userDto = userService.getUserByEmail(EMAIL);
        //then
        assertThat(userDto.getFirstname(), equalTo(FIRSTNAME));
        assertThat(userDto.getLastname(), equalTo(LASTNAME));
        assertThat(userDto.getAge(), equalTo(AGE));
        assertThat(userDto.getEmail(), equalTo(EMAIL));

    }

    @Test(expected = ResourceNotFoundException.class)
    public void getUserByEmailException() {
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.empty());

        userService.getUserByEmail(EMAIL);

    }

    @Test
    public void getUserById(){
        //given
        User user = User.builder().id(ID).build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));
        //when
        UserDto userDto = userService.getUserById(ID);
        //then
        assertThat(userDto.getId(), equalTo(ID));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getUserByIdException(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        userService.getUserById(ID);
    }

    @Test
    public void deleteById() {
        //given
        Long id = 1L;
        //when
        userService.deleteById(id);
        //then
        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    public void createNewUser() {
        //given
        User userToCreate = User.builder().email("nowak@gmail.com").id(1234567L).build();

        when(userRepository.save(any(User.class))).thenReturn(userToCreate);
        UserDto userDto = UserMapper.INSTANCE.userToUserDto(userToCreate);

        //when
        UserDto newUser = userService.createNewUser(userDto);
        //then
        assertThat(newUser.getEmail(), equalTo("nowak@gmail.com"));
    }

    @Test
    public void updateUser() {
        //given
        User userToUpdate = User.builder().email("pablo@picasso.com").id(98765L).build();
        User userInDataBase = User.builder().email("antonio@banderas.com").id(1L).build();

        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(userInDataBase));
        when(userRepository.save(any(User.class))).thenReturn(userToUpdate);

        UserDto userDtoMapped = UserMapper.INSTANCE.userToUserDto(userToUpdate);
        //when
        UserDto userDto = userService.updateUser(userDtoMapped, 1L);
        //then
        assertThat(userDto.getEmail(), equalTo("pablo@picasso.com"));
    }

    //TODO
//    @Test
//    public void patchUser(){
//        //given
//        User userToUpdate = User.builder().id(1L).email("jankowalski@wp.pl").age(15).firstname("Jan").build();
//        User userInDataBase = User.builder().id(1L).email("jankowalski@wp.pl").age(15).firstname("Jan").build();
//        //when
//
//        //then
//    }

}