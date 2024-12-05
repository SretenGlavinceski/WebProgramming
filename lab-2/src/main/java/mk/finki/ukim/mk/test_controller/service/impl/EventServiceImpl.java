package mk.finki.ukim.mk.test_controller.service.impl;

import mk.finki.ukim.mk.test_controller.bootstrap.DataHolder;
import mk.finki.ukim.mk.test_controller.model.Category;
import mk.finki.ukim.mk.test_controller.model.Event;
import mk.finki.ukim.mk.test_controller.model.exceptions.NoEventFoundByIdException;
import mk.finki.ukim.mk.test_controller.model.exceptions.NoLocationWithIdException;
import mk.finki.ukim.mk.test_controller.repository.EventRepository;
import mk.finki.ukim.mk.test_controller.repository.LocationRepository;
import mk.finki.ukim.mk.test_controller.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;

    public EventServiceImpl(EventRepository eventRepository, LocationRepository locationRepository) {
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Event> listAllEvents() {
        return eventRepository.findAllEvents();
    }

    @Override
    public List<Event> searchEventsByNameAndRating(String nameSearch, double ratingSearch) {
        return eventRepository.searchEventsByNameAndRating(nameSearch, ratingSearch);
    }

    @Override
    public void deleteEventById(Long id) {
        eventRepository.deleteEventById(id);
    }

    @Override
    public Event getEventById(Long id) throws NoEventFoundByIdException {
        Optional<Event> event = eventRepository.getEventById(id);
        if (event.isEmpty()) {
            throw new NoEventFoundByIdException(id);
        }
        return event.get();
    }

    @Override
    public void saveOrUpdate(Long eventId, String name, String description,
                             double popularityScore, Category category,
                             Long locationId, int numOfCards) throws NoLocationWithIdException {
        if (locationRepository.findById(locationId).isPresent()) {
            eventRepository.saveOrUpdate(eventId, name, description,
                    popularityScore, category, locationRepository.findById(locationId).get(), numOfCards);
        } else
            throw new NoLocationWithIdException(locationId);

    }

    @Override
    public List<Category> listAllCategories() {
        return eventRepository.listAllCategories();
    }
    @Override
    public List<Event> eventsByCategory(String typeCategory) {
        return eventRepository.eventsByCategory(typeCategory);
    }

    @Override
    public Category getCategoryByName(String CategoryName) {
        return DataHolder.categoryList
                .stream()
                .filter(category -> category.getTypeCategory().equals(CategoryName))
                .findAny().get();
    }

    @Override
    public boolean reserveEventTickets(Long eventId, int numOfTicketsNeeded) {
        return eventRepository.reserveEventTickets(eventId, numOfTicketsNeeded);
    }

    @Override
    public Event getEventByName(String name) {
        return eventRepository.getEventByName(name);
    }


}
