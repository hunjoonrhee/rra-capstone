package de.neuefische.backend.service;

import de.neuefische.backend.model.AppUser;
import de.neuefische.backend.model.CreateUserDTO;
import de.neuefische.backend.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {

    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;

    @Autowired
    public AppUserService(PasswordEncoder passwordEncoder, AppUserRepository appUserRepository) {
        this.passwordEncoder = passwordEncoder;
        this.appUserRepository = appUserRepository;
    }

    public String register(CreateUserDTO createUserDTO){
        String passwordHash = passwordEncoder.encode(createUserDTO.getPassword());

        AppUser newUser = new AppUser();
        if(createUserDTO.getUsername().equals("admin")){
            newUser.setUsername(createUserDTO.getUsername());
            newUser.setPasswordHash(passwordHash);
            newUser.setRoles(List.of("ADMIN"));
        }else{
            newUser.setUsername(createUserDTO.getUsername());
            newUser.setPasswordHash(passwordHash);
            newUser.setRoles(List.of("USER"));
        }

        AppUser persistedAppUser = appUserRepository.save(newUser);
        return persistedAppUser.getUsername();
    }

}
