package pl.sda.springrest.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestParam;
import pl.sda.springrest.model.dto.UserDto;
import pl.sda.springrest.services.UserService;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.sda.springrest.controllers.AbstractRestControllerTest.asJsonString;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    MockMvc mockMvc;  // pozwala wykonywać requesty bez uruchamiania całego środowiska springowego.

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void getAllUsers() throws Exception {

        UserDto user1 = UserDto.builder().id(1L).firstname("Wojtek").lastname("Kowalski").age(25).email("wojtekkowalski@gmail.com").gender("male").build();
        UserDto user2 = UserDto.builder().id(2L).firstname("Andrzej").lastname("Kowalski").age(28).email("kowalskiandrzej@gmail.com").gender("male").build();

        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get(UserController.BASE_URL + "/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userDtoList", hasSize(2)));

    }

    @Test
    public void getUserById() throws Exception {
        UserDto user1 = UserDto.builder().id(1L).firstname("Wojtek").lastname("Kowalski").age(25).email("wojtekkowalski@gmail.com").gender("male").build();

        when(userService.getUserById(anyLong())).thenReturn(user1);

        mockMvc.perform(get(UserController.BASE_URL + "/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("Wojtek")))
                .andExpect(jsonPath("$.lastname", equalTo("Kowalski")))
                .andExpect(jsonPath("$.id", equalTo(1)));
    }

    @Test
    public void getUserByEmail() throws Exception {
        UserDto userDto = UserDto.builder().id(1L).firstname("Pawel").lastname("Iksinski").email("iksinski@gamil.com").build();

        when(userService.getUserByEmail(anyString())).thenReturn(userDto);

        mockMvc.perform(get(UserController.BASE_URL + "/user").param("email", userDto.getEmail()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("Pawel")))
                .andExpect(jsonPath("$.lastname", equalTo("Iksinski")))
                .andExpect(jsonPath("$.id", equalTo(1)));
    }

    @Test
    public void createNewUser() throws Exception {
        UserDto userDto = UserDto.builder().id(1L).firstname("Adam").lastname("Nowak").build();
        when(userService.createNewUser(any(UserDto.class))).thenReturn(userDto);

        mockMvc.perform(post(UserController.BASE_URL + "/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.firstname", equalTo("Adam")))
                .andExpect(jsonPath("$.lastname", equalTo("Nowak")));

    }

    @Test
    public void deleteUser() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete(UserController.BASE_URL + "/users/1"))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteById(id);

    }

    @Test
    public void updateUser() {


    }

    @Test
    public void patchUser() {
    }
}