package de.neuefische.backend.service;

import de.neuefische.backend.model.*;
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

    private final RouteRepository routeRepository;
    private final IdService idService;
    private final LocationService locationService;
    private final PhotoService photoService;
    private MongoTemplate template;

    private final RoutesService routesService;

    public RouteService(RouteRepository routeRepository, IdService idService, LocationService locationService, PhotoService photoService, RoutesService routesService) {
        this.routeRepository = routeRepository;
        this.idService = idService;
        this.locationService = locationService;
        this.photoService = photoService;
        this.routesService = routesService;
    }
    @Autowired
    public RouteService(RouteRepository routeRepository, IdService idService, LocationService locationService, PhotoService photoService, MongoTemplate template, RoutesService routesService) {
        this.routeRepository = routeRepository;
        this.idService = idService;
        this.locationService = locationService;
        this.photoService = photoService;
        this.template = template;
        this.routesService = routesService;
    }




    public Route addNewRoute(RouteDTO routeDTO) {
        Route newRoute = Route.builder()
                .id(idService.generateId())
                .routeName(routeDTO.getRouteName())
                .hashtags(routeDTO.getHashtags())
                .startPosition(routeDTO.getStartPosition())
                .endPosition(routeDTO.getEndPosition())
                .routes(routesService.getRoutes(routeDTO.getStartPosition(), routeDTO.getEndPosition()))
                .imageThumbnail(routeDTO.getImageThumbnail())
                .position(new GeoJsonPoint(routeDTO.getStartPosition().getLat(),
                        routeDTO.getStartPosition().getLon()))
                .build();
        newRoute.setPhotos(photoService.getAllPhotosByRouteId(newRoute.getId()));
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

    public List<Route> getAllRoutesInRepo() {
        return routeRepository.findAll();
    }
}
