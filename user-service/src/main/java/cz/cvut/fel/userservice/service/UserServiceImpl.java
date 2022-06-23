package cz.cvut.fel.userservice.service;

import cz.cvut.fel.userservice.model.User;
import cz.cvut.fel.userservice.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository repo;

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }


    @Override
    public void createUser(@NotNull User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(),BCrypt.gensalt()));
        repo.save(user);

        logger.info(String.format("User %s created", user.getUsername()));
    }

    @Override
    public void deleteUser(@NotNull Integer id) {
        Optional<User> user = repo.findById(id);

        if(user.isPresent()) {
            user.get().setRemoved(true);
            repo.save(user.get());

            logger.info(String.format("User with id %s was deleted", id));
        } else {
            logger.warn(String.format("User with id %s doesn't exist", id));
        }
    }

    @Override
    public void updateUser(@NotNull User user) {
        if(!repo.existsById(user.getId())) {
            logger.warn(String.format("User with id %s doesn't exist", user.getId()));
            return;
        }

        repo.save(user);

        logger.info(String.format("User %s updated", user));
    }

    @Override
    public User getUserById(Integer id) {
        Optional<User> user = repo.findById(id);

        if(user.isPresent()) {
            return user.get();
        } else {
            logger.warn(String.format("User with id %s doesn't exist", id));
        }

        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) repo.findAll();
    }

    @Override
    public boolean userExistsById(Integer id) {
        return repo.existsById(id);
    }
}
