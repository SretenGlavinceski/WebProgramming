package mk.finki.ukim.mk.lab.repository;

import mk.finki.ukim.mk.lab.bootstrap.DataHolder;
import mk.finki.ukim.mk.lab.model.Category;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.EventBooking;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EventRepository {
    public List<Event> findAll(){
        return DataHolder.eventList;
    }

    public List<Event> searchEvents(String text){
        return DataHolder.eventList.stream()
                .filter(event -> event.getName().contains(text) || event.getDescription().contains(text))
                .collect(Collectors.toList());
    }

    public List<Category> listAllCategories() {
        return DataHolder.categoryList;
    }

    public List<Event> searchByCategory(String category){
        return DataHolder.eventList.stream()
                .filter(event -> event.getCategory().getTypeCategory().equals(category))
                .collect(Collectors.toList());
    }

}
