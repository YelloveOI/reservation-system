package cz.cvut.fel.userService.util;

import cz.cvut.fel.userService.model.Role;

public final class Constants {

    public static final Role DEFAULT_ROLE = Role.CUSTOMER;

    private Constants() {
        throw new AssertionError();
    }
}

