package cz.cvut.fel.userService.rest;

import cz.cvut.fel.userService.exception.NotFoundException;
import cz.cvut.fel.userService.exception.ValidationException;
import cz.cvut.fel.userService.model.User;
import cz.cvut.fel.userService.rest.util.RestUtils;
import cz.cvut.fel.userService.security.model.AuthenticationToken;
import cz.cvut.fel.userService.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/rest/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;


    // private final ReservationService reservationService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
/*

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER', 'ROLE_EMPLOYEE')")
    @GetMapping(value = "/{user}/reservation/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean getUsersReservationsWithStatus(@PathVariable User user, @PathVariable Status status) {
        return userService.checkIfUserHasAnyReservationsWithSpecifiedStatus(userService.findById(user.getId()), status);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER', 'ROLE_EMPLOYEE')")
    @GetMapping(value = "/{user}/reservations/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Reservation> getListOfUsersReservationsWithStatus(@PathVariable User user, @PathVariable Status status) {
        return userService.getListOfUsersReservationsWithSpecifiedStatus(userService.findById(user.getId()), status);
    }
*/

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/userexistence", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean ifExists(@RequestHeader String username) {
        return userService.exists(username);
    }

/*    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getCurrentUser(Principal principal) {
        final AuthenticationToken auth = (AuthenticationToken) principal;
        return auth.getPrincipal().getUser();
    }*/

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @GetMapping
    public List<User> getUsers() {
        return userService.findAll();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserById(@PathVariable int id) {
        final User user = userService.findById(id);
        if(user == null) {
            throw NotFoundException.create("User", id);
        }
        return user;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        userService.persist(user);
        LOG.debug("User with id {} has been registered.", user.getId());
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/current");
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable int id, @RequestBody User user) {
        final User defaultUser = userService.findById(id);
        if(!defaultUser.getId().equals(user.getId())) {
            throw new ValidationException("ID does not match with the requested one.");
        }
        userService.update(user);
        LOG.debug("User with id {} has been updated.", user.getId());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeUser(@PathVariable int id) {
        final User user = getUserById(id);
        if(user == null) {
            return;
        }

        /*List<Reservation> reservations = userService.getListOfUsersReservations(user);
        for(Reservation reservation : reservations) {
            reservationService.remove(reservation);
        }*/

        userService.remove(user);
        LOG.debug("User with id {} has been deleted.", user.getId());
    }
}

