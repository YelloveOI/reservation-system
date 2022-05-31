package cz.cvut.fel.userService.repository;

import cz.cvut.fel.userService.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    void deleteById(Integer id);
    @Query("select u from User u where u.username = ?1")
    User findByUsername(String username);
}
