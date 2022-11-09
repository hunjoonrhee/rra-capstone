package de.neuefische.backend.service;

import de.neuefische.backend.model.LocationDTO;
import de.neuefische.backend.model.LocationReturn;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class LocationService {
    private final WebClient client = WebClient.create("https://nominatim.openstreetmap.org/search?");

    public List<LocationReturn> getLocations(String address) {
        LocationDTO locationDTO = LocationDTO.builder()
                .q(address)
                .format("json")
                .addressdetails("1")
                .polygon_geojson("0")
                .build();

        List<LocationReturn> locationReturn =
                client.get()
                        .uri(locationDTO.toString())
                        .retrieve()
                        .toEntityList(LocationReturn.class)
                        .block()
                        .getBody();
        return locationReturn;
    }
}
