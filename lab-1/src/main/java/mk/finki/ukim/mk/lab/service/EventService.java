package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.Category;
import mk.finki.ukim.mk.lab.model.Event;

import java.util.List;

public interface EventService {
    List<Event> listAll();
    List<Event> searchEvents(String text);
    List<Event> searchEventsByNameAndScore(String name, double score);
     List<Category> listAllCategories();

     List<Event> searchByCategory(String name);
}
