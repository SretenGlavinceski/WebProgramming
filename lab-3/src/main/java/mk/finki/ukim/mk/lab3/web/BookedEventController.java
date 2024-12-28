package mk.finki.ukim.mk.lab3.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bookedEvents")
public class BookedEventController {

    @GetMapping("/view-all")
    public String viewAllBookedEventsList() {
        return "bookedEvents";
    }

    @GetMapping("/back-page")
    public String backPageViewEventsInfo() {
        return "redirect:/events";
    }

}
