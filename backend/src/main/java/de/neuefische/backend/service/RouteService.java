package de.neuefische.backend.service;

import de.neuefische.backend.model.LocationReturn;
import de.neuefische.backend.model.Route;
import de.neuefische.backend.model.RouteDTO;
import de.neuefische.backend.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {

    private RouteRepository routeRepository;
    private IdService idService;
    private LocationService locationService;
    private GeoJsonPointService geoJsonPointService;
    private MongoTemplate template;

    public RouteService(RouteRepository routeRepository, IdService idService, LocationService locationService, GeoJsonPointService geoJsonPointService) {
        this.routeRepository = routeRepository;
        this.idService = idService;
        this.locationService = locationService;
        this.geoJsonPointService = geoJsonPointService;
    }
    @Autowired
    public RouteService(RouteRepository routeRepository, IdService idService, LocationService locationService, GeoJsonPointService geoJsonPointService, MongoTemplate template) {
        this.routeRepository = routeRepository;
        this.idService = idService;
        this.locationService = locationService;
        this.geoJsonPointService = geoJsonPointService;
        this.template = template;
    }


    public Route addNewRoute(RouteDTO routeDTO) {
        Route newRoute = Route.builder()
                .id(idService.generateId())
                .routeName(routeDTO.getRouteName())
                .hashtags(routeDTO.getHashtags())
                .startPosition(routeDTO.getStartPosition())
                .imageThumbnail(routeDTO.getImageThumbnail())
                .position(new GeoJsonPoint(routeDTO.getStartPosition().getLat(),
                        routeDTO.getStartPosition().getLon()))
                .build();
        routeRepository.save(newRoute);
        return newRoute;
    }

    public List<Route> findByRoutesNear(String address) {
        List<LocationReturn> locations = locationService.getLocations(address);
        double lat = locations.get(0).getLat();
        double lon = locations.get(0).getLon();
        Point searchPoint = new Point(lat, lon);
        template
                .indexOps(Route.class)
                .ensureIndex(new GeospatialIndex("position").typed(GeoSpatialIndexType.GEO_2DSPHERE));

        return routeRepository.findByPositionNear(searchPoint, new Distance(2, Metrics.KILOMETERS));

    }
}
