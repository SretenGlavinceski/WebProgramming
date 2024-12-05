package mk.finki.ukim.mk.lab.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mk.finki.ukim.mk.lab.model.Category;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.service.impl.EventServiceImpl;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "event-servlet", urlPatterns = "/event")
public class EventListServlet extends HttpServlet {

    private final SpringTemplateEngine springTemplateEngine;
    private final EventServiceImpl eventService;

    public EventListServlet(SpringTemplateEngine springTemplateEngine, EventServiceImpl eventService) {
        this.springTemplateEngine = springTemplateEngine;
        this.eventService = eventService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String nameFilter = req.getParameter("filterName");
        String scoreFilter = req.getParameter("filterRating");
        String selectedCategory = req.getParameter("selectedCategory");

        List<Event> listByCategory = new ArrayList<>();
        if (selectedCategory != null) {
            listByCategory = eventService.searchByCategory(selectedCategory);
        }

        if (nameFilter == null || nameFilter.isEmpty() || scoreFilter == null || scoreFilter.isEmpty())
            renderPage(req, resp, new ArrayList<>(), listByCategory);
        else
            renderPage(req, resp, eventService.searchEventsByNameAndScore(nameFilter, Double.parseDouble(scoreFilter)), listByCategory);

    }

    private void renderPage(HttpServletRequest req, HttpServletResponse resp, List<Event> filteredEvents, List<Event> eventsByCategory) throws IOException {

        boolean isValid = req.getParameter("isValid") == null;

        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);

        WebContext webContext = new WebContext(webExchange);
        webContext.setVariable("events", eventService.listAll());
        webContext.setVariable("filteredEvents", filteredEvents);
        webContext.setVariable("isValid", isValid);
        webContext.setVariable("categories", eventService.listAllCategories());
        webContext.setVariable("eventsByCategory", eventsByCategory);


        this.springTemplateEngine.process("listEvents.html", webContext, resp.getWriter());
    }
}
