package mk.finki.ukim.mk.lab3.services;

import mk.finki.ukim.mk.lab3.model.Location;
import mk.finki.ukim.mk.lab3.model.exception.NoLocationByIdException;

import java.util.List;

public interface LocationServiceBluePrint {
    List<Location> findAllLocations();
    Location findLocationById(Long id) throws NoLocationByIdException;
}
