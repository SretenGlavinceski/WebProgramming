package mk.finki.ukim.mk.lab3.repository;

import mk.finki.ukim.mk.lab3.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
