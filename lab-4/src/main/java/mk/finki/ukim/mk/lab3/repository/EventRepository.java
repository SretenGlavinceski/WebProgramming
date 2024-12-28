package mk.finki.ukim.mk.lab3.repository;

import mk.finki.ukim.mk.lab3.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findEventsByLocation_Id(Long locationId);
}
