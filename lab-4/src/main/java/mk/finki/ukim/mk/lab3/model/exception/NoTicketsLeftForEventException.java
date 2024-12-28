package mk.finki.ukim.mk.lab3.model.exception;

public class NoTicketsLeftForEventException extends Exception {
    public NoTicketsLeftForEventException(String eventName, int numRequested) {
        super(String.format("Not enough tickets for event: %s, you requested: %d. ", eventName, numRequested));
    }
}
