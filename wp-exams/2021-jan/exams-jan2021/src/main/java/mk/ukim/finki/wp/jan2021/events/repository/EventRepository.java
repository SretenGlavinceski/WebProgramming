package mk.ukim.finki.wp.jan2021.events.repository;

import mk.ukim.finki.wp.jan2021.events.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
