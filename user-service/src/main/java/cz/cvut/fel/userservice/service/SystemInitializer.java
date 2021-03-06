package cz.cvut.fel.userservice.service;

import cz.cvut.fel.userservice.model.Role;
import cz.cvut.fel.userservice.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;

@Component
public class SystemInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(SystemInitializer.class);

    private static final String ADMIN_USERNAME = "MeetingRoomsAdmin";

    private final UserService userService;

    private final PlatformTransactionManager txManager;

    @Autowired
    public SystemInitializer(UserService userService,
                             PlatformTransactionManager txManager) {
        this.userService = userService;
        this.txManager = txManager;
    }

    @PostConstruct
    private void initSystem() {
        TransactionTemplate txTemplate = new TransactionTemplate(txManager);
        txTemplate.execute((status) -> {
            generateAdmin();
            for (int i = 2; i < 5; i++) {
                generateAdmin(i);
            }
            return null;
        });
    }

    private void generateAdmin(Integer integer) {
        final User admin = new User();
        admin.setUsername(ADMIN_USERNAME + integer);
        admin.setFirstName("System"+integer);
        admin.setLastName("Administrator"+integer);
        admin.setPassword("$2a$12$BYycrwQl276By197onZ6OeK5q5Q12pcZNpCIDawOsQc71R7kBp4xK");
        admin.setRole(Role.ADMINISTRATOR);
        admin.setEmail("Email"+integer+"@nss.cvut.cz");
        admin.setRemoved(false);
        LOG.info("Generated admin user with credentials " + admin.getUsername() + "/" + admin.getPassword());
        userService.createUser(admin);
    }

    private void generateAdmin() {
        final User admin = new User();
        admin.setUsername(ADMIN_USERNAME);
        admin.setFirstName("System");
        admin.setLastName("Administrator");
        admin.setPassword("$2a$12$BYycrwQl276By197onZ6OeK5q5Q12pcZNpCIDawOsQc71R7kBp4xK");
        admin.setRole(Role.ADMINISTRATOR);
        admin.setEmail("Email@nss.cvut.cz");
        admin.setRemoved(false);
        LOG.info("Generated admin user with credentials " + admin.getUsername() + "/" + admin.getPassword());
        userService.createUser(admin);
    }

    static User generateUser() {
        final User user = new User();
        user.setUsername("Tom55");
        user.setFirstName("Thomas");
        user.setLastName("Andersen");
        user.setPassword("NoWayHose");
        user.setRole(Role.CUSTOMER);
        user.setEmail("thomas.ande@nss.cvut.cz");
        user.setRemoved(false);
        return user;
    }
}

