package mk.finki.ukim.mk.lab.web;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.finki.ukim.mk.lab.model.EventBooking;
import mk.finki.ukim.mk.lab.service.impl.EventBookingServiceImpl;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name = "eventBookingServlet", urlPatterns = "/eventBooking")
public class EventBookingServlet extends HttpServlet {
    private final SpringTemplateEngine springTemplateEngine;
    private EventBookingServiceImpl eventBookingService;

    public EventBookingServlet(SpringTemplateEngine springTemplateEngine, EventBookingServiceImpl eventBookingService) {
        this.springTemplateEngine = springTemplateEngine;
        this.eventBookingService = eventBookingService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String eventName = req.getParameter("selectedEvent");
        String numOfTickets = req.getParameter("numTickets");
        String attendeeName = req.getParameter("attendeeName");

        if (eventName == null || numOfTickets == null || attendeeName == null || attendeeName.isEmpty()) {
            resp.sendRedirect("/event?isValid=false");
            return;
        }

        EventBooking bookedEvent = eventBookingService.placeBooking(
                eventName, attendeeName, req.getRemoteAddr(), Integer.parseInt(numOfTickets)
        );

        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);

        WebContext webContext = new WebContext(webExchange);
        webContext.setVariable("bookedEvent", bookedEvent);

        springTemplateEngine.process("bookingConfirmation.html", webContext, resp.getWriter());
    }
}
