package mk.finki.ukim.mk.lab3.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "booked_event_by_users")
public class BookedEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String eventName;
    private String attendeeName;

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

    public String getAttendeeName() {
        return attendeeName;
    }

    public void setAttendeeName(String attendeeName) {
        this.attendeeName = attendeeName;
    }

    public String getAttendeeAddress() {
        return attendeeAddress;
    }

    public void setAttendeeAddress(String attendeeAddress) {
        this.attendeeAddress = attendeeAddress;
    }

    public Long getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(Long numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    private String attendeeAddress;
    private Long numberOfTickets;

    public BookedEvent(String eventName, String attendeeName, String attendeeAddress, Long numberOfTickets) {
        this.eventName = eventName;
        this.attendeeName = attendeeName;
        this.attendeeAddress = attendeeAddress;
        this.numberOfTickets = numberOfTickets;
    }

    public BookedEvent() {

    }
}
