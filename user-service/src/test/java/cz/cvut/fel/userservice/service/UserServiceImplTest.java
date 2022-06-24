package cz.cvut.fel.userservice.service;

import cz.cvut.fel.userservice.model.Role;
import cz.cvut.fel.userservice.model.User;
import cz.cvut.fel.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserServiceImplTest {

    @Autowired
    private UserRepository repo;

    @Autowired
    private UserService userService;

    private static User user;

    @BeforeEach
    void init() {
        user = new User();
        user.setUsername("Tom55");
        user.setFirstName("Thomas");
        user.setLastName("Andersen");
        user.setPassword("NoWayHose");
        user.setRole(Role.CUSTOMER);
        user.setEmail("thomas.ande@nss.cvut.cz");
        user.setRemoved(false);
    }

    @Test
    public void createUserCreated() {
        userService.createUser(user);
        User result = userService.getUserById(user.getId());

        assertEquals(user, result);
    }

    @Test
    public void createUserPasswordHashed() {
        String unhashedPassword = "HesloVeslo22";
        final User user = new User();
        user.setUsername("Amy55");
        user.setFirstName("Amy");
        user.setLastName("Lee");
        user.setPassword(unhashedPassword);
        user.setRole(Role.CUSTOMER);
        user.setEmail("amy.l@nss.cvut.cz");
        user.setRemoved(false);

        userService.createUser(user);
        User result = userService.getUserById(user.getId());

        assertNotEquals(unhashedPassword, result.getPassword());
    }

    @Test
    public void deleteUserRemoved() {
        repo.save(user);

        userService.deleteUser(user.getId());
        User result = userService.getUserById(user.getId());

        assertTrue(result.isRemoved());
    }

    @Test
    public void updateUserUpdated() {
        repo.save(user);
        user.setUsername("Tomm");

        userService.updateUser(user);
        User result = userService.getUserById(user.getId());

        assertEquals(user.getUsername(), result.getUsername());
    }

    @Test
    public void getUserByIdReturned() {
        repo.save(user);

        User result = userService.getUserById(user.getId());

        assertEquals(user, result);
    }

    @Test
    public void getUserByIdDoesntExist() {
        repo.save(user);

        User result = userService.getUserById(558982954);

        assertNull(result);
    }

    @Test
    public void getAllUsersReturned() {
        List<User> expectedUsers = new ArrayList<>();
        Integer expectedSize = 6;
        repo.save(user);
        expectedUsers.add(user);

        final User user2 = new User();
        user2.setUsername("Amy55");
        user2.setFirstName("Amy");
        user2.setLastName("Lee");
        user2.setPassword("HesloVeslo22");
        user2.setRole(Role.CUSTOMER);
        user2.setEmail("amy.l@nss.cvut.cz");
        user2.setRemoved(false);
        repo.save(user2);
        expectedUsers.add(user2);

        List<User> result = userService.getAllUsers();

        assertEquals(expectedSize, result.size());
        assertTrue(result.containsAll(expectedUsers));
    }

    @Test
    public void userExistsByIdExists() {
        repo.save(user);

        boolean result = userService.userExistsById(user.getId());

        assertTrue(result);
    }

    @Test
    public void userExistsByIdNotExists() {
        boolean result = userService.userExistsById(558982954);

        assertFalse(result);
    }
}
