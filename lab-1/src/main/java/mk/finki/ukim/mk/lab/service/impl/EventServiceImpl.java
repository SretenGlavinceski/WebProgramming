package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.Category;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.repository.EventRepository;
import mk.finki.ukim.mk.lab.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Event> listAll() {
        return eventRepository.findAll();
    }
    @Override
    public List<Category> listAllCategories() {
        return eventRepository.listAllCategories();
    }

    @Override
    public List<Event> searchByCategory(String name) {
        return eventRepository.searchByCategory(name);
    }

    @Override
    public List<Event> searchEvents(String text) {
        return eventRepository.searchEvents(text);
    }

    @Override
    public List<Event> searchEventsByNameAndScore(String name, double score) {
        return listAll().stream().filter(event ->
                event.getName().toLowerCase().contains(name.toLowerCase()) && event.getPopularityScore() >= score
        ).collect(Collectors.toList());
    }



}
