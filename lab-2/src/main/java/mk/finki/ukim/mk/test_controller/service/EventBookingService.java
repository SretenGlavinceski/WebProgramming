package mk.finki.ukim.mk.test_controller.service;


import mk.finki.ukim.mk.test_controller.model.EventBooking;

import java.util.List;

public interface EventBookingService {
    List<EventBooking> listAllBookedEvents();
    EventBooking addBookedEvent(String eventName,
                                String attendeeName,
                                String attendeeAddress,
                                Long numberOfTickets);
    List<EventBooking> peopleAttendingEvent(String eventName);
}
