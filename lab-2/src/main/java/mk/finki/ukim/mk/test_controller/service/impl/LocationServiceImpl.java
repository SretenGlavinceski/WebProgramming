package mk.finki.ukim.mk.test_controller.service.impl;

import mk.finki.ukim.mk.test_controller.model.Location;
import mk.finki.ukim.mk.test_controller.repository.LocationRepository;
import mk.finki.ukim.mk.test_controller.service.LocationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Location> findAll() {
        return locationRepository.findAllLocations();
    }
}
