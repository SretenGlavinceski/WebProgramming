package mk.ukim.finki.wp.jan2021.events.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Event {

    public Event(String name, String description, Double price, EventType type, EventLocation location) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
        this.location = location;
        this.likes = 0;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Double price;

    @Enumerated(EnumType.STRING)
    private EventType type;

    @ManyToOne(fetch = FetchType.EAGER)
    private EventLocation location;

    private Integer likes = 0;

}
