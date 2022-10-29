package de.neuefische.backend.service;

import de.neuefische.backend.model.FoundRoutes;
import de.neuefische.backend.model.Route;
import de.neuefische.backend.repository.FoundRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoundRouteService {

    private final FoundRouteRepository foundRouteRepository;
    private final RouteService routeService;
//    private final IdService idService;

    @Autowired
    public FoundRouteService(FoundRouteRepository foundRouteRepository, RouteService routeService) {
        this.foundRouteRepository = foundRouteRepository;
        this.routeService = routeService;
    }

    public List<FoundRoutes> getAllFoundRoutes() {
        return foundRouteRepository.findAll();
    }

    public FoundRoutes saveFoundRoutes(String address){
        FoundRoutes foundRoutes = FoundRoutes.builder()
                .id(address)
                .routes(routeService.findByRoutesNear(address))
                .build();
        return foundRouteRepository.save(foundRoutes);
    }

    public List<Route> getFoundRoutesByAddress(String address) {
        return foundRouteRepository.findById(address).get().getRoutes();
    }
}
