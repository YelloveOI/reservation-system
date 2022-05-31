package cz.cvut.fel.userService.service;

import cz.cvut.fel.userService.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void createUser(User user);

    void deleteUser(Integer userId);

    void updateUser(User user);

    User getUserById(Integer id);

    List<User> getAllUsers();

    boolean userExistsById(Integer id);
}

