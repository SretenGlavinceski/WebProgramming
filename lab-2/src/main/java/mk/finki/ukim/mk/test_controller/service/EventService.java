package mk.finki.ukim.mk.test_controller.service;

import mk.finki.ukim.mk.test_controller.model.Category;
import mk.finki.ukim.mk.test_controller.model.Event;
import mk.finki.ukim.mk.test_controller.model.exceptions.NoEventFoundByIdException;
import mk.finki.ukim.mk.test_controller.model.exceptions.NoLocationWithIdException;

import java.util.List;

public interface EventService {
    List<Event> listAllEvents();
    List<Event> searchEventsByNameAndRating(String nameSearch, double ratingSearch);
    void deleteEventById(Long id);
    Event getEventById(Long id) throws NoEventFoundByIdException;
    void saveOrUpdate(Long eventId, String name,
                             String description, double popularityScore,
                             Category category, Long locationId, int numOfCards) throws NoLocationWithIdException;

    List<Category> listAllCategories();
    List<Event> eventsByCategory(String typeCategory);

    Category getCategoryByName(String CategoryName);
    boolean reserveEventTickets(Long eventId, int numOfTicketsNeeded);
    Event getEventByName(String name);
}
