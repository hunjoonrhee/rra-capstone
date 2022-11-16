package de.neuefische.backend.controller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AppUserControllerTest {

    @Autowired
    MockMvc mockMvc;

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

}