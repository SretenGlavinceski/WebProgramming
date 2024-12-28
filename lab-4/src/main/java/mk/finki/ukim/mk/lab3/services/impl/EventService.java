package mk.finki.ukim.mk.lab3.services.impl;

import mk.finki.ukim.mk.lab3.model.Category;
import mk.finki.ukim.mk.lab3.model.Event;
import mk.finki.ukim.mk.lab3.model.Location;
import mk.finki.ukim.mk.lab3.model.exception.NoEventFoundException;
import mk.finki.ukim.mk.lab3.model.exception.NoTicketsLeftForEventException;
import mk.finki.ukim.mk.lab3.repository.EventRepository;
import mk.finki.ukim.mk.lab3.services.EventServiceBluePrint;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService implements EventServiceBluePrint {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Event> findAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Event findEventById(Long id) throws NoEventFoundException {
        return eventRepository.findById(id).orElseThrow(() -> new NoEventFoundException(id));
    }

    @Override
    public void bookTicketsForEvent(Long eventId, int numOfTicketsNeeded) throws NoEventFoundException, NoTicketsLeftForEventException {
        Event event = findEventById(eventId);

        if (event.getNumOfCardsLeft() < numOfTicketsNeeded)
            throw new NoTicketsLeftForEventException(event.getName(), numOfTicketsNeeded);

        event.setNumOfCardsLeft(event.getNumOfCardsLeft() - numOfTicketsNeeded);
        eventRepository.save(event);
    }

    @Override
    public void deleteEventById(Long eventId) throws NoEventFoundException {
        eventRepository.delete(findEventById(eventId));
    }

    @Override
    public void editEvent(Long eventId, String name, String description, double popularityScore,
                          Category category, Location location, int numOfCardsLeft) throws NoEventFoundException {
        Event event = findEventById(eventId);
        event.setName(name);
        event.setDescription(description);
        event.setNumOfCardsLeft(numOfCardsLeft);
        event.setPopularityScore(popularityScore);
        event.setCategory(category);
        event.setLocation(location);

        eventRepository.save(event);
    }

    @Override
    public void addEvent(String name, String description, double popularityScore, Category category, Location location, int numOfCardsLeft) {
        eventRepository.save(new Event(name, description, popularityScore, category, location, numOfCardsLeft));
    }

    public List<Event> eventsByLocation(Long locationId) {
        return eventRepository.findEventsByLocation_Id(locationId);
    }


}
