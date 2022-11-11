package de.neuefische.backend.service;

import de.neuefische.backend.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RoutesService {

    private final WebClient client = WebClient.create("https://routing.openstreetmap.de/routed-foot/route/v1/foot/");

    public List<Routes> getRoutes(StartPosition startPosition, List<Position> betweenPositions,  EndPosition endPosition) {

        ResponseEntity<RoutesReturn> response;
        if(betweenPositions.isEmpty()){
            response = client.get()
                            .uri(startPosition.getLon() + ","+startPosition.getLat() + ";" +
                                    endPosition.getLon()+ ","+endPosition.getLat() +
                                    "?overview=full&geometries=geojson")
                            .retrieve()
                            .toEntity(RoutesReturn.class)
                            .block();

        } else{
            StringBuilder betweenURI = new StringBuilder();
            for(Position betweenPosition:betweenPositions) {
                betweenURI.append(betweenPosition.getLon() + "," + betweenPosition.getLat() + ";");
            }
                response = client.get()
                                .uri(startPosition.getLon() + ","+startPosition.getLat() + ";" +
                                        betweenURI+
                                        endPosition.getLon()+ ","+endPosition.getLat() +
                                        "?overview=full&geometries=geojson")
                                .retrieve()
                                .toEntity(RoutesReturn.class)
                                .block();

        }
        if(response== null || response.getBody() == null){
            throw new NoSuchElementException("Error while getting route with start position " + "start lat: " +
                    startPosition.getLat() + "start lon: " + startPosition.getLon() + " and with end position " +
                    "end lat: " + endPosition.getLat() + "end lon:" + endPosition.getLon());
        }
        return response.getBody().getRoutes();


    }
}
