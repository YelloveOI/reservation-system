package cz.cvut.fel.userService.rest;

import cz.cvut.fel.userService.dto.UserDto;
import cz.cvut.fel.userService.exception.NotFoundException;
import cz.cvut.fel.userService.model.User;
import cz.cvut.fel.userService.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-service")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "")
    public ResponseEntity<String> entryPoint() {
        return new ResponseEntity<>("User service", HttpStatus.valueOf(200));
    }

    // @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @GetMapping(value="/user")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.valueOf(200));
    }

    // @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @GetMapping(value = "/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) {
        if(userService.userExistsById(id)) {
            return new ResponseEntity<>(userService.getUserById(id), HttpStatus.valueOf(200));
        } else {
            return new ResponseEntity<>(HttpStatus.valueOf(404));
        }
    }

    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value="/user")
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        userService.createUser(user);

        return new ResponseEntity<>(HttpStatus.valueOf(201));
    }

    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value="/user/{id}")
    public ResponseEntity<Void> updateUser(@RequestBody User user, @PathVariable("id") Integer id) {
        if(userService.userExistsById(id)) {
            userService.updateUser(user);

            return new ResponseEntity<>(HttpStatus.valueOf(201));
        }else {
            return new ResponseEntity<>(HttpStatus.valueOf(404));
        }
    }

    @DeleteMapping(value="/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer id) {
        if(userService.userExistsById(id)) {
            userService.deleteUser(id);

            return new ResponseEntity<>(HttpStatus.valueOf(200));
        } else {
            return new ResponseEntity<>(HttpStatus.valueOf(404));
        }
    }
}

