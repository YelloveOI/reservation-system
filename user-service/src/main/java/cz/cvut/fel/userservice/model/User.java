package cz.cvut.fel.userservice.model;


import cz.cvut.fel.userservice.dto.UserDto;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "RRS_USER")
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

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User u = (User) o;
        return getId().equals(u.getId())
                && Objects.equals(getEmail(), u.getEmail())
                && Objects.equals(getFirstName(), u.getFirstName())
                && Objects.equals(getLastName(), u.getLastName())
                && Objects.equals(getPassword(), u.getPassword())
                && Objects.equals(getUsername(), u.getUsername())
                && Objects.equals(getRole(), u.getRole())
                && Objects.equals(isRemoved(), u.isRemoved());
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
                .append(getId())
                .append(getEmail())
                .append(getFirstName())
                .append(getLastName())
                .append(getPassword())
                .append(getUsername())
                .append(getRole())
                .append(isRemoved())
                .toHashCode();
    }
}
