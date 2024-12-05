package mk.finki.ukim.mk.test_controller.model.exceptions;

public class NoLocationWithIdException extends Exception{
    public NoLocationWithIdException(Long id) {
        super("No Location with id " + id + " found!");
    }
}
