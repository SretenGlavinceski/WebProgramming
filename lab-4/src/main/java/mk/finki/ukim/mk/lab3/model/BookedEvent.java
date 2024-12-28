package mk.finki.ukim.mk.lab3.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "booked_event_by_users")
public class BookedEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String eventName;
    private Long numberOfTickets;


    @ManyToOne
    private User user;

    public BookedEvent() {
    }

    public BookedEvent(String eventName, User user, Long numberOfTickets) {
        this.eventName = eventName;
        this.numberOfTickets = numberOfTickets;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Long getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(Long numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
