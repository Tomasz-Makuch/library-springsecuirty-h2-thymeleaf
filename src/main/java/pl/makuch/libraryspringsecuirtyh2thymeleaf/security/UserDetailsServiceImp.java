package pl.makuch.libraryspringsecuirtyh2thymeleaf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.makuch.libraryspringsecuirtyh2thymeleaf.model.User;
import pl.makuch.libraryspringsecuirtyh2thymeleaf.repository.UserRepo;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private UserRepo userRepo;

    @Autowired
    public UserDetailsServiceImp(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User: " + username + " not found"));
        return user;
    }
}
