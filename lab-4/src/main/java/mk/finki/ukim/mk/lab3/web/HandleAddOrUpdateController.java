package mk.finki.ukim.mk.lab3.web;

import mk.finki.ukim.mk.lab3.model.exception.NoCategoryByIdException;
import mk.finki.ukim.mk.lab3.model.exception.NoEventFoundException;
import mk.finki.ukim.mk.lab3.model.exception.NoLocationByIdException;
import mk.finki.ukim.mk.lab3.services.impl.CategoryService;
import mk.finki.ukim.mk.lab3.services.impl.EventService;
import mk.finki.ukim.mk.lab3.services.impl.LocationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/addOrUpdate")
public class HandleAddOrUpdateController {
    private final EventService eventService;
    private final CategoryService categoryService;
    private final LocationService locationService;

    public HandleAddOrUpdateController(EventService eventService, CategoryService categoryService, LocationService locationService) {
        this.eventService = eventService;
        this.categoryService = categoryService;
        this.locationService = locationService;
    }

    @GetMapping("/not-valid-args")
    public String viewAddEventPage(@RequestParam(required = false) String error,
                                   @RequestParam(required = false) Long eventId,
                                   Model model) throws NoEventFoundException {
        model.addAttribute("categories", categoryService.findAllCategories());
        model.addAttribute("locations", locationService.findAllLocations());
        model.addAttribute("event", eventService.findEventById(eventId));
        model.addAttribute("error", error);
        model.addAttribute("bodyContent", "main_events");
        return "master-template";
    }

    @PostMapping("/edit/{id}")
    public String editEvent(@PathVariable Long id,
                            @RequestParam String eventName,
                            @RequestParam String eventDescription,
                            @RequestParam double popularityScore,
                            @RequestParam int numOfTickets,
                            @RequestParam Long eventCategory,
                            @RequestParam Long eventLocation) {

        try {
            eventService.editEvent(
                    id,
                    eventName,
                    eventDescription,
                    popularityScore,
                    categoryService.findCategoryById(eventCategory),
                    locationService.findLocationById(eventLocation),
                    numOfTickets);
        } catch (NoCategoryByIdException | NoLocationByIdException | NoEventFoundException ex) {
            return "redirect:/addOrUpdate/not-valid-args?eventId=" + id + "&error=" + ex.getMessage();
        }

        return "redirect:/events";
    }

    @PostMapping("/add")
    public String addEvent(@RequestParam String eventName,
                            @RequestParam String eventDescription,
                            @RequestParam double popularityScore,
                            @RequestParam int numOfTickets,
                            @RequestParam Long eventCategory,
                            @RequestParam Long eventLocation) {

        try {
            eventService.addEvent(
                    eventName,
                    eventDescription,
                    popularityScore,
                    categoryService.findCategoryById(eventCategory),
                    locationService.findLocationById(eventLocation),
                    numOfTickets);
        } catch (NoCategoryByIdException | NoLocationByIdException ex) {
            return "redirect:/addOrUpdate/not-valid-args?error=" + ex.getMessage();
        }

        return "redirect:/events";
    }

}
