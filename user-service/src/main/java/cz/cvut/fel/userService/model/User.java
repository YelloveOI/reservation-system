package cz.cvut.fel.userService.model;


import cz.cvut.fel.userService.dto.UserDto;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class User extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private String email;

    @Basic(optional = false)
    @Column(nullable = false)
    private String firstName;

    @Basic(optional = false)
    @Column(nullable = false)
    private String lastName;

    @Basic(optional = false)
    @Column(nullable = false)
    private String password;

    @Basic(optional = false)
    @Column(nullable = false)
    private String username;

    @Enumerated(EnumType.STRING)
    private Role role;

    public UserDto toDto() {
        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setDeleted(isRemoved());
        userDto.setFirstname(firstName);
        userDto.setId(getId());
        userDto.setLastname(lastName);
        return userDto;
    }

    public void erasePassword() {
        this.password = null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
