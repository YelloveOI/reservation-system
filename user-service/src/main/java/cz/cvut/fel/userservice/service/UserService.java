package cz.cvut.fel.userservice.service;

import cz.cvut.fel.userservice.model.User;

import java.util.List;

public interface UserService {
    void createUser(User user);

    void deleteUser(Integer userId);

    void updateUser(User user);

    User getUserById(Integer id);

    List<User> getAllUsers();

    boolean userExistsById(Integer id);
}

