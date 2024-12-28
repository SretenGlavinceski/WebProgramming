package mk.finki.ukim.mk.lab3.services;

import mk.finki.ukim.mk.lab3.model.BookedEvent;
import mk.finki.ukim.mk.lab3.model.Event;
import mk.finki.ukim.mk.lab3.model.exception.InvalidArgumentsBookedEventException;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface BookedEventsServiceBluePrint {
    List<BookedEvent> findAllBookedEvents();
    BookedEvent saveBookedEvent(Event event, Long numTickets, String username)
            throws InvalidArgumentsBookedEventException;

    List<BookedEvent> bookedEventsByUser(Long userId);
}
