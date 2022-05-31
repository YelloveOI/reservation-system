package cz.cvut.fel.userService.model;

public enum Role {
    CUSTOMER("ROLE_CUSTOMER"), EMPLOYEE("ROLE_EMPLOYEE"), ADMINISTRATOR("ROLE_ADMINISTRATOR");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }
}
