package cz.cvut.fel.invoice.model;

import cz.cvut.fel.invoice.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class User extends AbstractEntity {

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private List<Role> roles;
}
