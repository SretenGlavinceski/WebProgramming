package mk.finki.ukim.mk.test_controller.web;


import jakarta.servlet.http.HttpServletRequest;
import mk.finki.ukim.mk.test_controller.model.Event;
import mk.finki.ukim.mk.test_controller.model.exceptions.NoEventFoundByIdException;
import mk.finki.ukim.mk.test_controller.model.exceptions.NoLocationWithIdException;
import mk.finki.ukim.mk.test_controller.model.exceptions.NoValidInputException;
import mk.finki.ukim.mk.test_controller.service.impl.EventBookingServiceImpl;
import mk.finki.ukim.mk.test_controller.service.impl.EventServiceImpl;
import mk.finki.ukim.mk.test_controller.service.impl.LocationServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/events")
public class EventController {
    private final EventServiceImpl eventService;
    private final EventBookingServiceImpl eventBookingService;
    private final LocationServiceImpl locationService;

    public EventController(EventServiceImpl eventService, EventBookingServiceImpl eventBookingService, LocationServiceImpl locationService) {
        this.eventService = eventService;
        this.eventBookingService = eventBookingService;
        this.locationService = locationService;
    }

    @GetMapping
    public String getEventsPage(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("events", eventService.listAllEvents());
        model.addAttribute("categories", eventService.listAllCategories());
        model.addAttribute("error", error);
        return "listEvents";
    }

    @PostMapping("/confirmEvent")
    public String submitForm(
            @RequestParam(required = false) String selectedEvent ,
            @RequestParam(required = false) Integer numTickets,
            @RequestParam(required = false) String attendeeName,
            Model model,
            HttpServletRequest request) {

        Event event = eventService.getEventByName(selectedEvent);
        if (!eventService.reserveEventTickets(event.getId(), numTickets)) {
            return "redirect:/events?error=NotEnoughTickets";
        }

        try {
            eventBookingService.addBookedEvent(
                    selectedEvent,
                    attendeeName,
                    request.getRemoteAddr(),
                    (long) numTickets
            );

        } catch (NoValidInputException ex) {
            return "redirect:/events?error=" + ex.getMessage();
        }

        return "redirect:/bookedEvents";
    }

    @GetMapping("/searchEvents")
    public String searchEvents(
            @RequestParam(required = false) String nameSearch,
            @RequestParam(required = false) Double ratingSearch,
            Model model) {

        model.addAttribute("searchedEvents", eventService.searchEventsByNameAndRating(nameSearch, ratingSearch));
        model.addAttribute("events", eventService.listAllEvents());
        model.addAttribute("categories", eventService.listAllCategories());

        return "listEvents";
    }

    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        eventService.deleteEventById(id);
        return "redirect:/events";
    }

    @GetMapping("/edit-form/{id}")
    public String getEditEventForm(@PathVariable Long id, Model model) {
        Event event = null;

        try {
            event = eventService.getEventById(id);
        } catch (NoEventFoundByIdException ex) {
            return "redirect:/events?error=" + ex.getMessage();
        }

        model.addAttribute("event", event);
        model.addAttribute("locations", locationService.findAll());
        model.addAttribute("categories", eventService.listAllCategories());

        return "add-event";
    }

    @GetMapping("/add-form")
    public String getAddEventPage(Model model) {
        model.addAttribute("locations", locationService.findAll());
        model.addAttribute("categories", eventService.listAllCategories());
        return "add-event";
    }


    @PostMapping("/add")
    public String saveEvent(@RequestParam String eventName,
                            @RequestParam String eventDescription,
                            @RequestParam Double eventScore,
                            @RequestParam Long eventLocation,
                            @RequestParam String eventCategory,
                            @RequestParam int numOfCardsEvent) {
        try {
            eventService.saveOrUpdate(null, eventName,
                    eventDescription, eventScore,
                    eventService.getCategoryByName(eventCategory), eventLocation, numOfCardsEvent);
        } catch (NoLocationWithIdException ex) {
            return "redirect:/events?error=" + ex.getMessage();
        }

        return "redirect:/events";
    }

    @PostMapping("/edit/{id}")
    public String editEvent(@PathVariable Long id,
                            @RequestParam String eventName,
                            @RequestParam String eventDescription,
                            @RequestParam Double eventScore,
                            @RequestParam Long eventLocation,
                            @RequestParam String eventCategory,
                            @RequestParam int numOfCardsEvent) {
        try {
            eventService.saveOrUpdate(id, eventName, eventDescription,
                    eventScore, eventService.getCategoryByName(eventCategory), eventLocation, numOfCardsEvent);
        } catch (NoLocationWithIdException ex) {
            return "redirect:/events?error=" + ex.getMessage();
        }

        return "redirect:/events";
    }

    @GetMapping("/searchByCategory")
    public String searchEventByCategory(@RequestParam String selectedCategory, Model model) {

        model.addAttribute("events", eventService.listAllEvents());
        model.addAttribute("categories", eventService.listAllCategories());
        model.addAttribute("eventsByCategory", eventService.eventsByCategory(selectedCategory));

        return "listEvents";
    }

    @GetMapping("/detail-page/{id}")
    public String getDetailsForEventPage(@PathVariable Long id, Model model) {
        Event event = null;
        try {
            event = eventService.getEventById(id);
        } catch (NoEventFoundByIdException ex) {
            return "redirect:/events?error=" + ex.getMessage();
        }

        model.addAttribute("event", event);
        model.addAttribute("peopleAttendingEvent", eventBookingService.peopleAttendingEvent(event.getName()));
        return "detail-event-page";
    }

}
