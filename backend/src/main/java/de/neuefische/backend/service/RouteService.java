package de.neuefische.backend.service;

import de.neuefische.backend.model.*;
import de.neuefische.backend.repository.FoundRouteRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class RouteService {

    private final RouteRepository routeRepository;
    private final IdService idService;
    private final LocationService locationService;
    private MongoTemplate template;

    private final RoutesService routesService;

    private final FoundRouteRepository foundRouteRepository;

    public RouteService(RouteRepository routeRepository, IdService idService, LocationService locationService, RoutesService routesService, FoundRouteRepository foundRouteRepository) {
        this.routeRepository = routeRepository;
        this.idService = idService;
        this.locationService = locationService;
        this.routesService = routesService;
        this.foundRouteRepository = foundRouteRepository;
    }
    @Autowired
    public RouteService(RouteRepository routeRepository, IdService idService, LocationService locationService, MongoTemplate template, RoutesService routesService, FoundRouteRepository foundRouteRepository) {
        this.routeRepository = routeRepository;
        this.idService = idService;
        this.locationService = locationService;
        this.template = template;
        this.routesService = routesService;
        this.foundRouteRepository = foundRouteRepository;
    }




    public Route addNewRoute(RouteDTO routeDTO) {
        Route newRoute = Route.builder()
                .id(idService.generateId())
                .routeName(routeDTO.getRouteName())
                .hashtags(routeDTO.getHashtags())
                .startPosition(routeDTO.getStartPosition())
                .betweenPositions(routeDTO.getBetweenPositions())
                .endPosition(routeDTO.getEndPosition())
                .routes(routesService.getRoutes(routeDTO.getStartPosition(), routeDTO.getBetweenPositions(), routeDTO.getEndPosition()))
                .photos(new ArrayList<>())
                .imageThumbnail(routeDTO.getImageThumbnail())
                .position(new GeoJsonPoint(routeDTO.getStartPosition().getLat(),
                        routeDTO.getStartPosition().getLon()))
                .createdBy(routeDTO.getCreatedBy())
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

        List<Route> routes = routeRepository.findByPositionNear(searchPoint, new Distance(2, Metrics.KILOMETERS));
        FoundRoutes foundRoutes = FoundRoutes.builder()
                .id(address).routes(routes).build();
        foundRouteRepository.save(foundRoutes);

        return routes;
    }

    public List<Route> getAllRoutesInRepo() {
        return routeRepository.findAll();
    }

    public void deleteRouteById(String id, String address) {
        routeRepository.deleteById(id);
        Optional<FoundRoutes> foundRoutesOptional = foundRouteRepository.findById(address);
        if(foundRoutesOptional.isPresent()){
            FoundRoutes foundRoutes = foundRoutesOptional.get();
            List<Route> routes = foundRoutes.getRoutes();
            routes.removeIf(route -> route.getId().equals(id));
            foundRoutes.setRoutes(routes);
            foundRouteRepository.save(foundRoutes);
        } else{
            throw new NoSuchElementException("No Routes with address " + address + " was found!");
        }


    }

    public List<Photo> getPhotosOfRoute(String id) {
        Optional<Route> routeOptional = routeRepository.findById(id);
        if(routeOptional.isPresent()){
            return routeOptional.get().getPhotos();
        }else{
            throw new NoSuchElementException("No Route with id " + id + " was found!");
        }
    }

    public Photo addANewPhotoForRoute(String id, String name) {
        Optional<Route> routeOptional = routeRepository.findById(id);
        if(routeOptional.isPresent()){
            Route route = routeOptional.get();
            Photo newPhoto = Photo.builder().id(idService.generateId()).name(name).build();
            List<Photo> photos = route.getPhotos();
            photos.add(newPhoto);
            route.setPhotos(photos);
            routeRepository.save(route);
            return newPhoto;
        }else{
            throw new NoSuchElementException("No Route with id " + id + " was found!");
        }
    }
}
