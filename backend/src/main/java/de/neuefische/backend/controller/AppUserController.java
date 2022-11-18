package de.neuefische.backend.controller;

import de.neuefische.backend.model.AppUserDTO;
import de.neuefische.backend.model.CreateUserDTO;
import de.neuefische.backend.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/user")
public class AppUserController {

    private final AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }


    @GetMapping("/login")
    public AppUserDTO login(){
        return me();

    }

    @GetMapping("/logout")
    public void logout(HttpSession session){
        // invalidate session
        session.invalidate();
    }

    @GetMapping("me")
    public AppUserDTO me(){
        return appUserService.getUserByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());
    }


    @PostMapping("/register")
    public String register(@RequestBody CreateUserDTO createUserDTO){
        return appUserService.register(createUserDTO);
    }
}
