package mk.finki.ukim.mk.lab3.model.exception;

public class NoLocationByIdException extends Exception {
    public NoLocationByIdException(Long id) {
        super(String.format("No Location with id=%d is found!", id));
    }
}
