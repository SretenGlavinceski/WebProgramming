package mk.ukim.finki.wp.june2021.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor

public class Match {

    public Match(String name, String description, Double price, MatchType type, MatchLocation location) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
        this.location = location;
        this.follows = 0;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Double price;

    @Enumerated(EnumType.STRING)
    private MatchType type;

    @ManyToOne(fetch = FetchType.EAGER)
    private MatchLocation location;

    private Integer follows = 0;

}
