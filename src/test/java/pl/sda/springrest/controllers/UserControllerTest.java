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
import pl.sda.springrest.model.dto.UserDto;
import pl.sda.springrest.services.UserService;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    public void getUserByEmail() {
    }

    @Test
    public void createNewUser() {
    }

    @Test
    public void deleteUser() {
    }

    @Test
    public void updateUser() {
    }

    @Test
    public void patchUser() {
    }
}