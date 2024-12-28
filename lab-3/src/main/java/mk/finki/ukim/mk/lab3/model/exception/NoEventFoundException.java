package mk.finki.ukim.mk.lab3.model.exception;

public class NoEventFoundException extends Exception {
    public NoEventFoundException(Long id) {
        super(String.format("No Event found with id: %d.", id));
    }
}
