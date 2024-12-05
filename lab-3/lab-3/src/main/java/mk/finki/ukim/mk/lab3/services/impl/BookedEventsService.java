package mk.finki.ukim.mk.lab3.services.impl;

import mk.finki.ukim.mk.lab3.model.BookedEvent;
import mk.finki.ukim.mk.lab3.model.Event;
import mk.finki.ukim.mk.lab3.model.exception.InvalidArgumentsBookedEventException;
import mk.finki.ukim.mk.lab3.repository.BookedEventRepository;
import mk.finki.ukim.mk.lab3.services.BookedEventsServiceBluePrint;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookedEventsService implements BookedEventsServiceBluePrint {
    private final BookedEventRepository bookedEventRepository;

    public BookedEventsService(BookedEventRepository bookedEventRepository) {
        this.bookedEventRepository = bookedEventRepository;
    }

    @Override
    public List<BookedEvent> findAllBookedEvents() {
        return bookedEventRepository.findAll();
    }

    @Override
    public BookedEvent saveBookedEvent(Event event, Long numTickets, String attendeeAddress, String attendeeName) throws InvalidArgumentsBookedEventException {
        if (event == null || numTickets < 1 || attendeeName.isEmpty()) {
            assert event != null;
            throw new InvalidArgumentsBookedEventException(event.getName());
        }

        return bookedEventRepository.save(new BookedEvent(event.getName(), attendeeName, attendeeAddress, numTickets));
    }

    public List<String> attendeesByEvent(String eventName) {
        return bookedEventRepository.findAllByEventName(eventName)
                .stream().map(BookedEvent::getAttendeeName).toList();
    }

}
