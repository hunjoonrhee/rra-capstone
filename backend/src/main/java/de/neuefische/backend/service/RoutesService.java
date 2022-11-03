package de.neuefische.backend.service;

import de.neuefische.backend.model.EndPosition;
import de.neuefische.backend.model.Routes;
import de.neuefische.backend.model.RoutesReturn;
import de.neuefische.backend.model.StartPosition;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class RoutesService {

    private final WebClient client = WebClient.create("https://routing.openstreetmap.de/routed-foot/route/v1/foot/");

    public List<Routes> getRoutes(StartPosition startPosition, EndPosition endPosition) {

        RoutesReturn routesReturn =
                client.get()
                        .uri(startPosition.getLon() + ","+startPosition.getLat() + ";" +
                                endPosition.getLon()+ ","+endPosition.getLat() +
                                "?overview=full&geometries=geojson")
                        .retrieve()
                        .toEntity(RoutesReturn.class)
                        .block()
                        .getBody();
        return routesReturn.getRoutes();
    }
}
