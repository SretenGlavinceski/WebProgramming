package mk.finki.ukim.mk.test_controller.repository;

import mk.finki.ukim.mk.test_controller.bootstrap.DataHolder;
import mk.finki.ukim.mk.test_controller.model.EventBooking;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EventBookingRepository {
    public List<EventBooking> listAllBookedEvents() {
        return DataHolder.bookedEvents;
    }

    public EventBooking addBookedEvent(
            String eventName,
            String attendeeName,
            String attendeeAddress,
            Long numberOfTickets){
        EventBooking eventBooking = new EventBooking(eventName, attendeeName, attendeeAddress, numberOfTickets);
        DataHolder.bookedEvents.add(eventBooking);
        return eventBooking;
    }

    public List<EventBooking> peopleAttendingEvent(String eventName) {
        return DataHolder.bookedEvents.stream()
                .filter(eventBooking -> eventBooking.getEventName().equals(eventName))
                .toList();
    }

}
