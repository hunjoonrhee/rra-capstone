package de.neuefische.backend.service;

import de.neuefische.backend.model.AppUser;
import de.neuefische.backend.model.AppUserDTO;
import de.neuefische.backend.model.CreateUserDTO;
import de.neuefische.backend.repository.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class AppUserServiceTest {

    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private final AppUserRepository appUserRepository = mock(AppUserRepository.class);

    private final AppUserService appUserService = new AppUserService(passwordEncoder, appUserRepository);

    @Test
    void register_AsAdmin_ShouldReturn_Username_WithRoleADMIN(){
        // GIVEN
        CreateUserDTO createUserDTO = new CreateUserDTO("admin", "password");
        when(passwordEncoder.encode(any())).thenReturn("passwordHash");
        when(appUserRepository.save(any())).thenReturn(new AppUser("admin", "passwordHash", List.of("ADMIN")));
        // WHEN

        String actual = appUserService.register(createUserDTO);

        // THEN
        assertEquals("admin", actual);
    }
    @Test
    void register_AsUser_ShouldReturn_Username_WithRoleUSER(){
        // GIVEN
        CreateUserDTO createUserDTO = new CreateUserDTO("user1", "password");
        when(passwordEncoder.encode(any())).thenReturn("passwordHash");
        when(appUserRepository.save(any())).thenReturn(new AppUser("user1", "passwordHash", List.of("USER")));
        // WHEN

        String actual = appUserService.register(createUserDTO);

        // THEN
        assertEquals("user1", actual);
    }
    @Test
    void getUserByUsername_ShouldReturn_AppUserDTO(){
        // GIVEN
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        AppUser dummyUser = new AppUser("user1", "xxx", roles);
        when(appUserRepository.findById("user1")).thenReturn(Optional.of(dummyUser));
        // WHEN

        AppUserDTO actual = appUserService.getUserByUsername("user1");

        // THEN
        AppUserDTO expected = AppUserDTO.builder()
                .username(dummyUser.getUsername())
                .roles(dummyUser.getRoles())
                .build();
        assertEquals(expected, actual);
    }
    @Test
    void getUserByUsername_ShouldThrow_NoSuchElementException_WhenUsernameDoesntExist(){
        // GIVEN

        when(appUserRepository.findById("user1")).thenReturn(Optional.empty());

        // WHEN THEN
        assertThrows(NoSuchElementException.class, ()-> appUserService.getUserByUsername("user1"));
        verify(appUserRepository).findById("user1");

    }
}