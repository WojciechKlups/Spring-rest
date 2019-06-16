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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private UserService userService;

    private static final String FIRSTNAME = "Janusz";
    private static final String LASTNAME = "Kowalski";
    private static final String EMAIL = "kowalski@gmail.com";
    private static final int AGE = 28;

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
}