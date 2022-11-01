package de.neuefische.backend.service;

import de.neuefische.backend.model.AppUser;
import de.neuefische.backend.repository.AppUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AppUserDetailService implements UserDetailsService {
    private final AppUserRepository appUserRepository;

    public AppUserDetailService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findById(username)
                .orElse(null);

        if(user == null){
            throw new UsernameNotFoundException("username " +username + " is not found!" );
        }else{
            return new User(user.getUsername(), user.getPasswordHash(), Collections.emptyList());
        }
    }
}
