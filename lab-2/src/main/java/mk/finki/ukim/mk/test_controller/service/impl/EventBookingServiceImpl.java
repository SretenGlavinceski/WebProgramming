package mk.finki.ukim.mk.test_controller.service.impl;

import mk.finki.ukim.mk.test_controller.model.EventBooking;
import mk.finki.ukim.mk.test_controller.model.exceptions.NoValidInputException;
import mk.finki.ukim.mk.test_controller.repository.EventBookingRepository;
import mk.finki.ukim.mk.test_controller.service.EventBookingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventBookingServiceImpl implements EventBookingService {

    private final EventBookingRepository eventBookingRepository;

    public EventBookingServiceImpl(EventBookingRepository eventBookingRepository) {
        this.eventBookingRepository = eventBookingRepository;
    }

    @Override
    public List<EventBooking> listAllBookedEvents() {
        return eventBookingRepository.listAllBookedEvents();
    }

    @Override
    public EventBooking addBookedEvent(String eventName,
                                       String attendeeName,
                                       String attendeeAddress,
                                       Long numberOfTickets) {

        if (eventName == null || eventName.isEmpty() || attendeeName == null || attendeeName.isEmpty()) {
            throw new NoValidInputException("Enter All Fields!");
        }

        return eventBookingRepository.addBookedEvent(eventName, attendeeName, attendeeAddress, numberOfTickets);
    }

    @Override
    public List<EventBooking> peopleAttendingEvent(String eventName) {
        return eventBookingRepository.peopleAttendingEvent(eventName);
    }
}
