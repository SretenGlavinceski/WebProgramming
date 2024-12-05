package mk.finki.ukim.mk.test_controller.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data

public class Event {
    private static long ID_GENERATOR = 1;
    private long id;
    private String name;
    private String description;
    private double popularityScore;
    private Category category;
    private Location location;
    private int numOfCardsLeft;

    public Event(String name, String description, double popularityScore, Category category, Location location, int numOfCardsLeft) {
        this.id = ID_GENERATOR++;
        this.name = name;
        this.description = description;
        this.popularityScore = popularityScore;
        this.category = category;
        this.location = location;
        this.numOfCardsLeft = numOfCardsLeft;
    }

    public boolean reserveTickets(int numOfTicketsNeeded) {
        if (this.numOfCardsLeft - numOfTicketsNeeded < 0) {
            return false;
        }
        this.numOfCardsLeft -= numOfTicketsNeeded;
        return true;
    }
}
