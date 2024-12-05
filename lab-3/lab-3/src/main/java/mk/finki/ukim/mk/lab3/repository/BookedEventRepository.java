package mk.finki.ukim.mk.lab3.repository;

import mk.finki.ukim.mk.lab3.model.BookedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookedEventRepository extends JpaRepository<BookedEvent, Long> {
    List<BookedEvent> findAllByEventName(String name);
}
