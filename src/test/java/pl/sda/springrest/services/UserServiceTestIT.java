package pl.sda.springrest.services;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.sda.springrest.bootstrap.Bootstrap;
import pl.sda.springrest.mappers.UserMapper;
import pl.sda.springrest.model.User;
import pl.sda.springrest.model.dto.UserDto;
import pl.sda.springrest.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserServiceTestIT {

    @Autowired
    UserRepository userRepository;

    UserService userService;

    @Before
    public void setUp() throws Exception {

        System.out.println("Loading users");
        Bootstrap bootstrap = new Bootstrap(userRepository);
        bootstrap.run();

        userService = new UserService(userRepository, UserMapper.INSTANCE);
    }

    @Test
    public void findAllUsersTest(){
        List<User> users = userRepository.findAll();

        assertNotNull(users);
        assertThat(users.size(), equalTo(5));
    }

    @Test
    public void findUserByEmailTest(){

        User user = userRepository.getOne(1L);

        userService.getUserByEmail(user.getEmail());

        assertNotNull(user);
        assertThat(user.getEmail(), equalTo("kowalski@gmail.com"));

    }

    @Test
    public void findUserByIdTest() {

        UserDto userById = userService.getUserById(2L);

        assertNotNull(userById);
        assertThat(userById.getFirstname(), equalTo("Pawel"));
    }

    @Test
    public void createNewUserTest(){

        UserDto userDto = UserDto.builder().firstname("Pablo").lastname("Picasso").email("picasso@gmail.com").age(60).gender("male").build();
        userService.createNewUser(userDto);

        UserDto userById = userService.getUserById(6L);

        assertNotNull(userById);
        assertThat(userById.getFirstname(), equalTo("Pablo"));
        assertThat(userById.getLastname(), equalTo("Picasso"));
        assertThat(userById.getEmail(), equalTo("picasso@gmail.com"));
        assertThat(userById.getAge(), equalTo(60));
        assertThat(userById.getGender(), equalTo("male"));

    }
}
