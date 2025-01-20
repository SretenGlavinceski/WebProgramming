package mk.ukim.finki.wp.jan2021.events.service.impl;

import mk.ukim.finki.wp.jan2021.events.model.Event;
import mk.ukim.finki.wp.jan2021.events.model.EventType;
import mk.ukim.finki.wp.jan2021.events.model.exceptions.InvalidEventIdException;
import mk.ukim.finki.wp.jan2021.events.model.exceptions.InvalidEventLocationIdException;
import mk.ukim.finki.wp.jan2021.events.repository.EventRepository;
import mk.ukim.finki.wp.jan2021.events.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventLocationServiceImpl locationService;

    public EventServiceImpl(EventRepository eventRepository, EventLocationServiceImpl locationService) {
        this.eventRepository = eventRepository;
        this.locationService = locationService;
    }

    @Override
    public List<Event> listAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Event findById(Long id) {
        return eventRepository.findById(id).orElseThrow(InvalidEventIdException::new);
    }

    @Override
    public Event create(String name, String description, Double price, EventType type, Long location) {
        Event event = new Event(name, description, price, type, locationService.findById(location));
        eventRepository.save(event);
        return event;
    }

    @Override
    public Event update(Long id, String name, String description, Double price, EventType type, Long location) {
        Event event = findById(id);
        event.setName(name);
        event.setDescription(description);
        event.setPrice(price);
        event.setType(type);
        event.setLocation(locationService.findById(location));

        eventRepository.save(event);
        return event;
    }

    @Override
    public Event delete(Long id) {
        Event event = findById(id);
        eventRepository.delete(event);
        return event;
    }

    @Override
    public Event like(Long id) {
        Event event = findById(id);
        event.setLikes(event.getLikes() + 1);
        eventRepository.save(event);
        return event;
    }

    @Override
    public List<Event> listEventsWithPriceLessThanAndType(Double price, EventType type) {
        List<Event> events = listAllEvents();

        if (price != null) {
            events = events.stream()
                    .filter(event -> event.getPrice() < price)
                    .collect(Collectors.toList());
        }

        if (type != null) {
            events = events.stream()
                    .filter(event -> event.getType() == type)
                    .collect(Collectors.toList());
        }

        return events;

    }
}
