package mk.finki.ukim.mk.lab.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data

public class Event {
    private String name;
    private String description;
    private double popularityScore;
    private Category category;

    public Event(String name, String description, double popularityScore, Category category) {
        this.name = name;
        this.description = description;
        this.popularityScore = popularityScore;
        this.category = category;
    }
}
