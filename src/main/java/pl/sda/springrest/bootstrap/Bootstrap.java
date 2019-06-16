package pl.sda.springrest.bootstrap;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.sda.springrest.model.User;
import pl.sda.springrest.repositories.UserRepository;

import java.util.stream.Stream;

@Component
public class Bootstrap implements CommandLineRunner {

    private UserRepository userRepository;

    @Autowired
    public Bootstrap(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        loadUsers();
    }

    private void loadUsers() {

        User user = new User();
        user.setFirstname("Jan");
        user.setLastname("Kowalski");
        user.setAge(34);
        user.setPassword("1234");
        user.setGender("male");
        user.setPersonalIdNumber("982334572");

        User user1 = new User();
        user1.setFirstname("Pawel");
        user1.setLastname("Nowak");
        user1.setAge(24);
        user1.setPassword("0987");
        user1.setGender("male");
        user1.setPersonalIdNumber("172364871");

        User user2 = new User();
        user2.setFirstname("Adam");
        user2.setLastname("Iksiński");
        user2.setAge(56);
        user2.setPassword("123456");
        user2.setGender("male");
        user2.setPersonalIdNumber("987946545");

        User user3 = new User();
        user3.setFirstname("Marta");
        user3.setLastname("Igrekowska");
        user3.setAge(38);
        user3.setPassword("098765");
        user3.setGender("female");
        user3.setPersonalIdNumber("979464132");

        User user4 = new User();
        user4.setFirstname("Janina");
        user4.setLastname("Kowalska");
        user4.setAge(78);
        user4.setPassword("1234567");
        user4.setGender("female");
        user4.setPersonalIdNumber("213135467");

        Stream.of(user, user1, user2, user3, user4)
                .forEach(userRepository::save);
    }
}
