package mk.finki.ukim.mk.test_controller.web;

import mk.finki.ukim.mk.test_controller.service.impl.EventBookingServiceImpl;
import mk.finki.ukim.mk.test_controller.service.impl.EventServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bookedEvents")
public class EventBookingController {

    private final EventServiceImpl eventService;
    private final EventBookingServiceImpl eventBookingService;

    public EventBookingController(EventServiceImpl eventService, EventBookingServiceImpl eventBookingService) {
        this.eventService = eventService;
        this.eventBookingService = eventBookingService;
    }

    @GetMapping
    public String listBookedEvents(Model model) {
        model.addAttribute("bookedEvents", eventBookingService.listAllBookedEvents());
        return "bookedEvents";
    }

    @GetMapping("/back-page")
    public String goBackPage() {
        return "redirect:/events";
    }

}
