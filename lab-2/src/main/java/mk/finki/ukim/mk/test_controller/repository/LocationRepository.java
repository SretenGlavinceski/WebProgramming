package mk.finki.ukim.mk.test_controller.repository;

import mk.finki.ukim.mk.test_controller.bootstrap.DataHolder;
import mk.finki.ukim.mk.test_controller.model.Location;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LocationRepository {

    public List<Location> findAllLocations() {
        return DataHolder.locations;
    }
    public Optional<Location> findById(Long id) {
        return DataHolder.locations.stream().filter(location -> location.getId() == id).findFirst();
    }

}
