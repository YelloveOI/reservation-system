package cz.cvut.fel.invoice.security.service;

import cz.cvut.fel.invoice.model.User;
import cz.cvut.fel.invoice.security.UserDetails;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    final private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username)
                .orElseThrow(
                       () -> new UsernameNotFoundException(String.format("User '%s' not found.", username))
                );

        return new UserDetails(user);
    }
}
