package mk.finki.ukim.mk.lab3.web;

import jakarta.servlet.http.HttpServletRequest;
import mk.finki.ukim.mk.lab3.model.User;
import mk.finki.ukim.mk.lab3.services.impl.BookedEventsService;
import mk.finki.ukim.mk.lab3.services.impl.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.http.HttpRequest;

@Controller
@RequestMapping("/bookedEvents")
public class BookedEventController {

    private final BookedEventsService bookedEventsService;
    private final UserService userService;

    public BookedEventController(BookedEventsService bookedEventsService, UserService userService) {
        this.bookedEventsService = bookedEventsService;
        this.userService = userService;
    }

    @GetMapping("/view-all")
    @PreAuthorize("hasRole('ADMIN')")
    public String viewAllBookedEventsList(Model model) {
        model.addAttribute("bodyContent", "bookedEvents");
        model.addAttribute("bookedEvents", bookedEventsService.findAllBookedEvents());
        return "master-template";
    }

    @GetMapping("/back-page")
    public String backPageViewEventsInfo() {
        return "redirect:/events";
    }

    @GetMapping("/view-user-bookings")
    @PreAuthorize("hasRole('USER')")
    public String viewUserBookedEvents(HttpServletRequest request, Model model) {
        User currentUser = userService.findByUsername(request.getRemoteUser());
        model.addAttribute("bookedEvents", bookedEventsService.bookedEventsByUser(currentUser.getId()));
        model.addAttribute("bodyContent", "bookedEvents");

        return "master-template";
    }

}
