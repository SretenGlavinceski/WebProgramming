package mk.finki.ukim.mk.lab3.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import mk.finki.ukim.mk.lab3.model.BookedEvent;
import mk.finki.ukim.mk.lab3.model.Event;
import mk.finki.ukim.mk.lab3.model.User;
import mk.finki.ukim.mk.lab3.model.exception.InvalidArgumentsBookedEventException;
import mk.finki.ukim.mk.lab3.model.exception.NoEventFoundException;
import mk.finki.ukim.mk.lab3.model.exception.NoTicketsLeftForEventException;
import mk.finki.ukim.mk.lab3.services.impl.*;
import org.hibernate.boot.query.BootQueryLogging;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;

@Controller
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final BookedEventsService bookedEventsService;
    private final CategoryService categoryService;
    private final LocationService locationService;
    private final UserService userService;

    public EventController(EventService eventService, BookedEventsService bookedEventsService, CategoryService categoryService, LocationService locationService, UserService userService) {
        this.eventService = eventService;
        this.bookedEventsService = bookedEventsService;
        this.categoryService = categoryService;
        this.locationService = locationService;
        this.userService = userService;
    }

    @GetMapping
    public String viewMainEventsPage(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("allEvents", eventService.findAllEvents());
        model.addAttribute("locations", locationService.findAllLocations());
        model.addAttribute("error", error);
        model.addAttribute("bodyContent", "main_events");
        return "master-template";
    }

    @Transactional
    @PostMapping("/confirmEvent")
    public String confirmEventReservation(@RequestParam Long selectedEvent,
                                          @RequestParam Long numTickets,
                                          HttpServletRequest request,
                                          Model model) {

        User currentUser = userService.findByUsername(request.getRemoteUser());

        try {

            eventService.bookTicketsForEvent(selectedEvent, numTickets.intValue());

            bookedEventsService.saveBookedEvent(
                    eventService.findEventById(selectedEvent),
                    numTickets,
                    currentUser.getUsername());

        } catch (NoEventFoundException | InvalidArgumentsBookedEventException | NoTicketsLeftForEventException ex) {
            return "redirect:/events?error=" + ex.getMessage();
        }

        model.addAttribute("bookedEvents", bookedEventsService.bookedEventsByUser(currentUser.getId()));
        model.addAttribute("bodyContent", "bookedEvents");

        return "master-template";
    }

    @GetMapping("/detail-page/{id}")
    public String getEventDetails(@PathVariable Long id, Model model) {
        Event event = null;
        try {
            event = eventService.findEventById(id);
        } catch (NoEventFoundException ex) {
            return "redirect:/events?error=" + ex.getMessage();
        }
        model.addAttribute("currentEvent", event);
        model.addAttribute("peopleAttendingEvent", bookedEventsService.attendeesByEvent(event.getName()));
        model.addAttribute("bodyContent", "details-event-page");

        return "master-template";
    }

    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        try {
            eventService.deleteEventById(id);
        } catch (NoEventFoundException ex) {
            return "redirect:/events?error=" + ex.getMessage();
        }

        return "redirect:/events";
    }

    @GetMapping("/edit-form/{id}")
    public String editEventForm(@PathVariable Long id, Model model) {
        Event event = null;
        try {
            event = eventService.findEventById(id);
        } catch (NoEventFoundException ex) {
            return "redirect:/events?error=" + ex.getMessage();
        }

        model.addAttribute("event", event);
        model.addAttribute("categories", categoryService.findAllCategories());
        model.addAttribute("locations", locationService.findAllLocations());
        model.addAttribute("bodyContent", "add-event");

        return "master-template";
    }


    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addEventForm(Model model) {
        model.addAttribute("categories", categoryService.findAllCategories());
        model.addAttribute("locations", locationService.findAllLocations());
        model.addAttribute("bodyContent", "add-event");

        return "master-template";
    }

    @GetMapping("/searchEventsByLocation")
    public String searchEventsByLocation(@RequestParam Long searchEventsByLocation, Model model) {
        model.addAttribute("allEvents", eventService.findAllEvents());
        model.addAttribute("locations", locationService.findAllLocations());
        model.addAttribute("searchedEventsByLocation", eventService.eventsByLocation(searchEventsByLocation));
        model.addAttribute("bodyContent", "main_events");

        return "master-template";
    }
}
