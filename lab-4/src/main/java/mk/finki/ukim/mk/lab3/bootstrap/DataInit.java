package mk.finki.ukim.mk.lab3.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.finki.ukim.mk.lab3.model.Category;
import mk.finki.ukim.mk.lab3.model.Event;
import mk.finki.ukim.mk.lab3.model.Location;
import mk.finki.ukim.mk.lab3.model.User;
import mk.finki.ukim.mk.lab3.repository.CategoryRepository;
import mk.finki.ukim.mk.lab3.repository.EventRepository;
import mk.finki.ukim.mk.lab3.repository.LocationRepository;
import mk.finki.ukim.mk.lab3.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInit {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInit(CategoryRepository categoryRepository, EventRepository eventRepository, LocationRepository locationRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.categoryRepository = categoryRepository;
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
//String username, String name, String surname, String email, String password
    private void initUsers() {
        userRepository.save(new User("user1", "user", "user123", "email@gmail.com", passwordEncoder.encode("u1")));
        userRepository.save(new User("user2", "user", "user123", "email@gmail.com", passwordEncoder.encode("u2")));
        userRepository.save(new User("user3", "user", "user123", "email@gmail.com", passwordEncoder.encode("u3")));
        userRepository.save(new User("user4", "user", "user123", "email@gmail.com", passwordEncoder.encode("u4")));
        userRepository.save(new User("user5", "user", "user123", "email@gmail.com", passwordEncoder.encode("u5")));
        userRepository.save(new User("admin", "admin", "admin", "email@gmail.com", passwordEncoder.encode("admin")));
    }

    private void initLocations() {
        locationRepository.save(new Location("Boris Trajkovski", "Skopje 10/231", "7000", "Sport Center"));
        locationRepository.save(new Location("Jane Sandanski", "Skopje 32/132", "5000", "Sport Center"));
        locationRepository.save(new Location("Night Club Marakana", "Skopje 90/120", "1000", "Night Club"));
        locationRepository.save(new Location("Hotel Karpos", "Skopje 1000/23", "600", "Hotel"));
        locationRepository.save(new Location("Filharmonija", "Skopje 1000/909", "1200", "Concert hall"));
    }

    private void initCategories()  {
        categoryRepository.save(new Category("Music", 9.1));
        categoryRepository.save(new Category("IT", 2.1));
        categoryRepository.save(new Category("BookStore", 3.2));
        categoryRepository.save(new Category("Festival", 6.7));
        categoryRepository.save(new Category("Movie Festivals", 9.8));
    }

    private void initEvents() {

        eventRepository.save(new Event(
                "La Tomatina",
                "The most vibrant and uncommon festivals in Europe. ",
                9.7,
                categoryRepository.findById(2L).get(),
                locationRepository.findById(2L).get(),
                100));

        eventRepository.save(new Event("Oktoberfest",
                "Fun Festival to be at.",
                8.3,
                categoryRepository.findById(1L).get(),
                locationRepository.findById(2L).get(),
                20));

        eventRepository.save(new Event(
                "Carnival of Venice",
                "Historical elegance and mystery. ",
                7.8,
                categoryRepository.findById(1L).get(),
                locationRepository.findById(5L).get(),
                15));

        eventRepository.save(new Event("St. Patrick’s Festival",
                "Colorful festival of Irish culture and heritage.",
                5.6,
                categoryRepository.findById(4L).get(),
                locationRepository.findById(4L).get(),
                10));

        eventRepository.save(new Event("Festival of Lights",
                "Captivating festival with amazing light installations.  ",
                7.0,
                categoryRepository.findById(5L).get(),
                locationRepository.findById(3L).get(),
                18));

        eventRepository.save(new Event("Glastonbury Festival",
                "Iconic music, art, and culture celebration.",
                7.7,
                categoryRepository.findById(1L).get(),
                locationRepository.findById(1L).get(),
                89));

        eventRepository.save(new Event("Roskilde Festival",
                "The largest music and culture event in Northern Europe.",
                3.2,
                categoryRepository.findById(4L).get(),
                locationRepository.findById(3L).get(),
                99));

        eventRepository.save(new Event("Sziget Festival",
                "Festival of music, art, and love.",
                9.9,
                categoryRepository.findById(5L).get(),
                locationRepository.findById(4L).get(),
                74));

        eventRepository.save(new Event("Cannes Film Festival",
                "Pinnacle of filmmaking congregates. ",
                6.8,
                categoryRepository.findById(2L).get(),
                locationRepository.findById(4L).get(),
                502));

        eventRepository.save(new Event("Edinburgh International Festival",
                "Celebrate the performing arts.",
                6.3,
                categoryRepository.findById(1L).get(),
                locationRepository.findById(5L).get(),
                303));

    }

    @PostConstruct
    public void init(){
        initCategories();
        initLocations();
        initEvents();
        initUsers();
    }
}
