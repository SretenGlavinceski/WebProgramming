package mk.finki.ukim.mk.lab3.services;

import mk.finki.ukim.mk.lab3.model.Category;
import mk.finki.ukim.mk.lab3.model.Event;
import mk.finki.ukim.mk.lab3.model.Location;
import mk.finki.ukim.mk.lab3.model.exception.NoEventFoundException;
import mk.finki.ukim.mk.lab3.model.exception.NoTicketsLeftForEventException;

import java.util.List;

public interface EventServiceBluePrint {
    List<Event> findAllEvents();
    Event findEventById(Long id) throws NoEventFoundException;

    void bookTicketsForEvent(Long eventId, int numOfTicketsNeeded) throws NoEventFoundException, NoTicketsLeftForEventException;
    void deleteEventById(Long eventId) throws NoEventFoundException;
    void editEvent(Long eventId, String name, String description, double popularityScore,
                          Category category, Location location, int numOfCardsLeft) throws NoEventFoundException;

    void addEvent(String name, String description, double popularityScore,
                  Category category, Location location, int numOfCardsLeft);
}
