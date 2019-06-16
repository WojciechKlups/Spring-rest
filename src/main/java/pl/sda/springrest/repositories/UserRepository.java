package pl.sda.springrest.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.springrest.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


}
