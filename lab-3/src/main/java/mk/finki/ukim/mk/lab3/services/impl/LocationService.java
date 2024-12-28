package mk.finki.ukim.mk.lab3.services.impl;

import mk.finki.ukim.mk.lab3.model.Location;
import mk.finki.ukim.mk.lab3.model.exception.NoLocationByIdException;
import mk.finki.ukim.mk.lab3.repository.LocationRepository;
import mk.finki.ukim.mk.lab3.services.LocationServiceBluePrint;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LocationService implements LocationServiceBluePrint {
    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Location> findAllLocations() {
        return locationRepository.findAll();
    }

    @Override
    public Location findLocationById(Long id) throws NoLocationByIdException {
        return locationRepository.findById(id)
                .orElseThrow(() -> new NoLocationByIdException(id));
    }
}
