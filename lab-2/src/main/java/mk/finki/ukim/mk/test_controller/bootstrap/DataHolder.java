package mk.finki.ukim.mk.test_controller.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.finki.ukim.mk.test_controller.model.Category;
import mk.finki.ukim.mk.test_controller.model.Event;
import mk.finki.ukim.mk.test_controller.model.EventBooking;
import mk.finki.ukim.mk.test_controller.model.Location;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {

    public static List<Event> eventList;
    public static List<EventBooking> bookedEvents;
    public static List<Category> categoryList;
    public static List<Location> locations;

    private void initLocations() {
        locations = new ArrayList<>();

        locations.add(new Location("Boris Trajkovski", "Skopje 10/231", "7000", "Sport Center"));
        locations.add(new Location("Jane Sandanski", "Skopje 32/132", "5000", "Sport Center"));
        locations.add(new Location("Night Club Marakana", "Skopje 90/120", "1000", "Night Club"));
        locations.add(new Location("Hotel Karpos", "Skopje 1000/23", "600", "Hotel"));
        locations.add(new Location("Filharmonija", "Skopje 1000/909", "1200", "Concert hall"));
    }

    private void initCategories()  {
        categoryList = new ArrayList<>();
        categoryList.add(new Category("Music", 9.1));
        categoryList.add(new Category("IT", 2.1));
        categoryList.add(new Category("BookStore", 3.2));
        categoryList.add(new Category("Festival", 6.7));
        categoryList.add(new Category("Movie Festivals", 9.8));
    }

    private void initEvents() {

        eventList = new ArrayList<>();

        eventList.add(new Event(
                "La Tomatina",
                "The most vibrant and uncommon festivals in Europe. ",
                9.7,
                categoryList.get(1),
                locations.get(1), 100));
        eventList.add(new Event("Oktoberfest",
                "Fun Festival to be at.",
                8.3,
                categoryList.get(0),
                locations.get(2), 20));
        eventList.add(new Event(
                "Carnival of Venice",
                "Historical elegance and mystery. ",
                7.8,
                categoryList.get(4),
                locations.get(0), 15));
        eventList.add(new Event("St. Patrick’s Festival",
                "Colorful festival of Irish culture and heritage.",
                5.6,
                categoryList.get(3),
                locations.get(3), 10));
        eventList.add(new Event("Festival of Lights",
                "Captivating festival with amazing light installations.  ",
                7.0,
                categoryList.get(2),
                locations.get(4), 18));
        eventList.add(new Event("Glastonbury Festival",
                "Iconic music, art, and culture celebration.",
                7.7,
                categoryList.get(0),
                locations.get(0), 89));
        eventList.add(new Event("Roskilde Festival",
                "The largest music and culture event in Northern Europe.",
                3.2,
                categoryList.get(3),
                locations.get(2), 99));
        eventList.add(new Event("Sziget Festival",
                "Festival of music, art, and love.",
                9.9,
                categoryList.get(4),
                locations.get(3), 74));
        eventList.add(new Event("Cannes Film Festival",
                "Pinnacle of filmmaking congregates. ",
                6.8,
                categoryList.get(1),
                locations.get(3), 502));
        eventList.add(new Event("Edinburgh International Festival",
                "Celebrate the performing arts.",
                6.3,
                categoryList.get(0),
                locations.get(4), 303));
    }
    private void initBookedEvents() {
        bookedEvents = new ArrayList<>();
    }

    @PostConstruct
    public void init(){
        initCategories();
        initLocations();
        initEvents();
        initBookedEvents();
    }
}
