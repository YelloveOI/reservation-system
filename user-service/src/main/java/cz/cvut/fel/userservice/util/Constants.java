package cz.cvut.fel.userservice.util;

import cz.cvut.fel.userservice.model.Role;

public final class Constants {

    public static final Role DEFAULT_ROLE = Role.CUSTOMER;

    private Constants() {
        throw new AssertionError();
    }
}

