package de.neuefische.backend.service;

import de.neuefische.backend.model.AppUser;
import de.neuefische.backend.repository.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AppUserDetailServiceTest {

    private final AppUserRepository appUserRepository = mock(AppUserRepository.class);
    private final AppUserDetailService appUserDetailService = new AppUserDetailService(appUserRepository);

    @Test
    void loadUserByUsername_ShouldReturn_UserByGivenUsername(){
        // GIVEN
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        AppUser dummyUser = new AppUser("user1", "xxx", roles);
        when(appUserRepository.findById("user1")).thenReturn(Optional.of(dummyUser));

        // WHEN
        UserDetails actual = appUserDetailService.loadUserByUsername("user1");

        // THEN
        assertEquals(new User(dummyUser.getUsername(), dummyUser.getPasswordHash(), Collections.emptyList()),actual);
    }
    @Test
    void loadUserByUsername_ShouldThrow_UsernameNotFoundException_WhenUsernameDoesntExist(){
        // GIVEN
        when(appUserRepository.findById("user1")).thenReturn(Optional.empty());

        // WHEN THEN
        assertThrows(UsernameNotFoundException.class, ()-> appUserDetailService.loadUserByUsername("user1"));
        verify(appUserRepository).findById("user1");
    }

}