package mk.finki.ukim.mk.lab3.repository;

import mk.finki.ukim.mk.lab3.model.BookedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookedEventRepository extends JpaRepository<BookedEvent, Long> {
    List<BookedEvent> findAllByEventName(String name);

    @Query(value = "select * from booked_event_by_users o where o.USER_ID = ?1", nativeQuery = true)
    List<BookedEvent> findAllByUser(Long userId);
}
