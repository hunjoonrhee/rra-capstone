package de.neuefische.backend.controller;

import de.neuefische.backend.model.AppUserDTO;
import de.neuefische.backend.model.CreateUserDTO;
import de.neuefische.backend.service.AppUserDetailService;
import de.neuefische.backend.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/user")
public class AppUserController {

    private final AppUserService appUserService;
    private final AppUserDetailService appUserDetailService;

    @Autowired
    public AppUserController(AppUserService appUserService, AppUserDetailService appUserDetailService) {
        this.appUserService = appUserService;
        this.appUserDetailService = appUserDetailService;
    }


    @GetMapping("/login")
    public String login(){
        return appUserDetailService.loadUserByUsername(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName())
                .getUsername();
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
