package cz.cvut.fel.userService.service;

import cz.cvut.fel.userService.model.Role;
import cz.cvut.fel.userService.model.User;
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
            return null;
        });
    }

    private void generateAdmin() {
        if (userService.exists(ADMIN_USERNAME)) {
            return;
        }
        final User admin = new User();
        admin.setUsername(ADMIN_USERNAME);
        admin.setFirstName("System");
        admin.setLastName("Administrator");
        admin.setPassword("4dm1n");
        admin.setRole(Role.ADMINISTRATOR);
        admin.setEmail("Email@kbss.ear.cvut.cz");
        admin.setRemoved(false);
        LOG.info("Generated admin user with credentials " + admin.getUsername() + "/" + admin.getPassword());
        userService.persist(admin);
    }
}

