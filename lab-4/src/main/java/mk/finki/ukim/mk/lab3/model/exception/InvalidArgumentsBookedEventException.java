package mk.finki.ukim.mk.lab3.model.exception;

public class InvalidArgumentsBookedEventException extends Exception {
    public InvalidArgumentsBookedEventException(String eventName) {
        super(String.format("Invalid Arguments Passed to buy tickets to event: %s", eventName));
    }
}
