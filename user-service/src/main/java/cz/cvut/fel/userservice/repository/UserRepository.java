package cz.cvut.fel.userservice.repository;

import cz.cvut.fel.userservice.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    void deleteById(Integer id);
    @Query("select u from User u where u.username = ?1")
    User findByUsername(String username);
}
