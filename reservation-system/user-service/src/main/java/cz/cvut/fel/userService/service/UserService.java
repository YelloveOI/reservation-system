package cz.cvut.fel.userService.service;

import cz.cvut.fel.userService.dao.UserDao;
import cz.cvut.fel.userService.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private final UserDao dao;

    @Autowired
    public UserService(UserDao dao) {
        this.dao = dao;
    }

    /*
    @Transactional
    public boolean checkIfUserHasAnyReservationsWithSpecifiedStatus(User user, Status status) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(status);
        List<Reservation> reservations = dao.findAllUsersReservations(user.getId());
        for (Reservation reservation : reservations) {
            if (reservation.getStatus() == status) {
                return true;
            }
        }
        return false;
    }

    @Transactional
    public List<Reservation> getListOfUsersReservationsWithSpecifiedStatus(User user, Status status) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(status);
        List<Reservation> reservations = dao.findAllUsersReservations(user.getId());
        List<Reservation> statusReservations = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getStatus() == status) {
                statusReservations.add(reservation);
            }
        }
        return statusReservations;
    }

    @Transactional
    public List<Reservation> getListOfUsersReservations(User user) {
        return dao.findAllUsersReservations(user.getId());
    }
    */

    @Transactional
    public void persist(User user) {
        Objects.requireNonNull(user);
        dao.persist(user);
    }

    @Transactional
    public void update(User user) {
        Objects.requireNonNull(user);
        dao.update(user);
    }

    @Transactional
    public void remove(User user) {
        Objects.requireNonNull(user);
        dao.remove(user);
    }

    @Transactional
    public User findById(int id) {
        return dao.find(id);
    }

    @Transactional
    public List<User> findAll() {
        return dao.findAll();
    }

    @Transactional
    public boolean exists(String username) {
        return dao.findByUsername(username) != null;
    }

}

