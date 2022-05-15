package cz.cvut.fel.userService.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "SELECT u FROM User AS u"),
        @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User AS u WHERE u.username = :username"),
})
public class User extends AbstractEntity {

    @Getter
    @Setter
    @Basic(optional = false)
    @Column(nullable = false)
    private String email;

    @Getter
    @Setter
    @Basic(optional = false)
    @Column(nullable = false)
    private String firstName;

    @Getter
    @Setter
    @Basic(optional = false)
    @Column(nullable = false)
    private String lastName;

    @Getter
    @Setter
    @Basic(optional = false)
    @Column(nullable = false)
    private String password;

    @Getter
    @Setter
    @Basic(optional = false)
    @Column(nullable = false)
    private String username;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private Role role;

    public void erasePassword() {
        this.password = null;
    }

}
