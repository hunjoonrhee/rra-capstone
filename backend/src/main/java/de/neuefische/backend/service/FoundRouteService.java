package de.neuefische.backend.service;

import de.neuefische.backend.model.FoundRoutes;
import de.neuefische.backend.repository.FoundRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoundRouteService {

    private final FoundRouteRepository foundRouteRepository;
    private final RouteService routeService;
    private final IdService idService;

    @Autowired
    public FoundRouteService(FoundRouteRepository foundRouteRepository, RouteService routeService, IdService idService) {
        this.foundRouteRepository = foundRouteRepository;
        this.routeService = routeService;
        this.idService = idService;
    }

    public List<FoundRoutes> getAllFoundRoutes() {
        return foundRouteRepository.findAll();
    }

    public FoundRoutes saveFoundRoutes(String address){
        FoundRoutes foundRoutes = FoundRoutes.builder()
                .id(idService.generateId())
                .foundRoutes(routeService.findByRoutesNear(address))
                .build();
        return foundRouteRepository.save(foundRoutes);
    }
}
