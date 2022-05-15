package cz.cvut.fel.userService.dao;

import cz.cvut.fel.userService.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class UserDao extends BaseDao<User> {

    public UserDao() {
        super(User.class);
    }

    public List<User> findAll() {
        return em.createNamedQuery("User.findAll", User.class).getResultList();
    }

    public User findByUsername(String username) {
        try {
            return em.createNamedQuery("User.findByUsername", User.class).setParameter("username", username).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /*
    public List<Reservation> findAllUsersReservations(Integer id) {
        try {
            return em.createNamedQuery("User.findAllUsersReservations", Reservation.class).setParameter("USERid", id).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    */

}

