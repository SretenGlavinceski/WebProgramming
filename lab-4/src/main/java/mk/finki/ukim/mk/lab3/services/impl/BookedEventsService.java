package mk.finki.ukim.mk.lab3.services.impl;

import mk.finki.ukim.mk.lab3.model.BookedEvent;
import mk.finki.ukim.mk.lab3.model.Event;
import mk.finki.ukim.mk.lab3.model.exception.InvalidArgumentsBookedEventException;
import mk.finki.ukim.mk.lab3.repository.BookedEventRepository;
import mk.finki.ukim.mk.lab3.repository.UserRepository;
import mk.finki.ukim.mk.lab3.services.BookedEventsServiceBluePrint;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookedEventsService implements BookedEventsServiceBluePrint {
    private final BookedEventRepository bookedEventRepository;
    private final UserRepository userRepository;

    public BookedEventsService(BookedEventRepository bookedEventRepository, UserRepository userRepository) {
        this.bookedEventRepository = bookedEventRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<BookedEvent> findAllBookedEvents() {
        return bookedEventRepository.findAll();
    }

    @Override
    public BookedEvent saveBookedEvent(Event event,
                                       Long numTickets,
                                       String username) throws InvalidArgumentsBookedEventException {
        if (event == null || numTickets < 1 || username.isEmpty()) {
            assert event != null;
            throw new InvalidArgumentsBookedEventException(event.getName());
        }

        return bookedEventRepository.save(
                new BookedEvent(
                        event.getName(),
                        userRepository.findByUsername(username),
                        numTickets
                )
        );
    }

    @Override
    public List<BookedEvent> bookedEventsByUser(Long userId) {
        return bookedEventRepository.findAllByUser(userId);
    }

    //    String eventName, User user, String attendeeAddress, Long numberOfTickets
    public List<String> attendeesByEvent(String eventName) {
        return bookedEventRepository.findAllByEventName(eventName)
                .stream().map(bookedEvent -> bookedEvent.getUser().getUsername())
                .toList();
    }



}
