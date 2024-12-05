package mk.finki.ukim.mk.test_controller.model.exceptions;

public class NoEventFoundByIdException extends Exception{
    public NoEventFoundByIdException(Long id) {
        super("No Event found with id: " + id);
    }
}
