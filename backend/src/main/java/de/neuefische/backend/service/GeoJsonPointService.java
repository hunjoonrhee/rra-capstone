package de.neuefische.backend.service;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

@Service
public class GeoJsonPointService {

    public GeoJsonPoint createGeoJsonPoint(double x, double y){
        return new GeoJsonPoint(x, y);
    }
}
