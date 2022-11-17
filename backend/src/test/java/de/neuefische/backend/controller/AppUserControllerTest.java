package de.neuefische.backend.controller;
import de.neuefische.backend.model.AppUser;
import de.neuefische.backend.model.AppUserDTO;
import de.neuefische.backend.model.CreateUserDTO;
import de.neuefische.backend.repository.AppUserRepository;
import de.neuefische.backend.service.AppUserDetailService;
import de.neuefische.backend.service.AppUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AppUserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AppUserRepository appUserRepository;

    @MockBean
    AppUserDetailService appUserDetailService;

    @MockBean
    AppUserService appUserService;

    @Test
    void WithoutLogin_ShouldReturn_Unauthorized() throws Exception {

        String requestBody = """
                        {
                           "routeName": "Castle2",
                           "hashtags": [
                             "tree",
                             "river",
                             "city"
                           ],
                           "imageThumbnail": "IMG_4906",
                           "startPosition": {
                             "lat": 49.44854177005591,
                             "lon": 11.063655277425456
                           },
                           "betweenPositions":[
                               {
                                   "lat":49.46098385110948,
                                   "lon": 11.072590464358841
                               },
                               {
                                   "lat":49.461374358580485,
                                   "lon": 11.09223325911263
                               }
                           ],
                           "endPosition": {
                             "lat": 49.44837436626852,
                             "lon": 11.063927048332076
                           }
                         }
                """;

        mockMvc.perform(
                        post("/api/route")
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "hrhee", password = "ABC123")
    void WithLogin_ShouldReturn_Authorized() throws Exception {

        String requestBody = """
                        {
                           "routeName": "Castle2",
                           "hashtags": [
                             "tree",
                             "river",
                             "city"
                           ],
                           "imageThumbnail": "IMG_4906",
                           "startPosition": {
                             "lat": 49.44854177005591,
                             "lon": 11.063655277425456
                           },
                           "betweenPositions":[
                               {
                                   "lat":49.46098385110948,
                                   "lon": 11.072590464358841
                               },
                               {
                                   "lat":49.461374358580485,
                                   "lon": 11.09223325911263
                               }
                           ],
                           "endPosition": {
                             "lat": 49.44837436626852,
                             "lon": 11.063927048332076
                           }
                         }
                """;

        mockMvc.perform(
                post("/api/route")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                        .andExpect(status().isOk());
    }

    @Test
    void ShouldReturn_username_When_Request_With_Login() throws Exception {
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        AppUser dummyUser = new AppUser("user1", "xxx", roles);
        when(appUserRepository.findById(any())).thenReturn(Optional.of(dummyUser));

        when(appUserDetailService.loadUserByUsername(any())).thenReturn(
                new User(dummyUser.getUsername(), dummyUser.getPasswordHash(), Collections.emptyList())
        );

        mockMvc.perform(get("/api/user/login"))
                .andExpect(status().isOk())
                .andExpect(content().string("user1"));
    }
    @Test
    void logout() throws Exception {

        mockMvc.perform(get("/api/user/logout"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void me_ShouldReturn_AppUserDTO() throws Exception {
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        AppUser dummyUser = new AppUser("user1", "xxx", roles);
        when(appUserRepository.findById(any())).thenReturn(Optional.of(dummyUser));

        when(appUserService.getUserByUsername(any())).thenReturn(new AppUserDTO(dummyUser.getUsername(), dummyUser.getRoles()));

        String expectedJson = """
                    {
                        "username":"user1",
                        "roles":["USER"]
                    }
                """;
        mockMvc.perform(get("/api/user/me"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void register() throws Exception {
        CreateUserDTO createUserDTO = new CreateUserDTO("user1", "password");

        when(appUserService.register(any())).thenReturn(createUserDTO.getUsername());

        String requestBody = """
                        {
                            "username":"user1",
                            "password":"password"
                        }
                """;

        String expected = "user1";

        mockMvc.perform(
                        post("/api/user/register")
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

}