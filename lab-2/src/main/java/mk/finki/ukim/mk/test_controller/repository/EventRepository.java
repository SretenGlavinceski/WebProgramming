package mk.finki.ukim.mk.test_controller.repository;

import mk.finki.ukim.mk.test_controller.bootstrap.DataHolder;
import mk.finki.ukim.mk.test_controller.model.Category;
import mk.finki.ukim.mk.test_controller.model.Event;
import mk.finki.ukim.mk.test_controller.model.Location;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EventRepository {
    public List<Event> findAllEvents() {
        return DataHolder.eventList;
    }

    public List<Event> searchEventByName (String name) {
        return DataHolder.eventList.stream().filter(event -> event.getName().equals(name)).toList();
    }

    public Event getEventByName(String name) {
        return DataHolder.eventList.stream().filter(event -> event.getName().equals(name)).findAny().get();
    }

    public List<Event> searchEventsByNameAndRating(String nameSearch, double ratingSearch) {
        return DataHolder.eventList.stream()
                .filter(event -> event.getName().toLowerCase().contains(nameSearch.toLowerCase())
                        && event.getPopularityScore() >= ratingSearch)
                .toList();
    }

    public void deleteEventById(Long id) {
        DataHolder.eventList.removeIf(event -> event.getId() == id);
    }

    public Optional<Event> getEventById(Long eventId) {
        return DataHolder.eventList.stream().filter(event -> event.getId() == eventId).findAny();
    }

    public void saveOrUpdate(Long eventId, String name,
                             String description, double popularityScore,
                             Category category, Location location, int numOfCards) {
        if (eventId != null)
            DataHolder.eventList.removeIf(event -> event.getId() == eventId);
        DataHolder.eventList.add(new Event(name, description, popularityScore, category, location, numOfCards));
    }

    public List<Category> listAllCategories() {
        return DataHolder.categoryList;
    }

    public List<Event> eventsByCategory(String typeCategory) {
        return DataHolder.eventList.stream()
                .filter(event -> event.getCategory().getTypeCategory().equals(typeCategory))
                .toList();
    }

    public boolean reserveEventTickets(Long eventId, int numOfTickets) {
        return getEventById(eventId).get().reserveTickets(numOfTickets);
    }

}
