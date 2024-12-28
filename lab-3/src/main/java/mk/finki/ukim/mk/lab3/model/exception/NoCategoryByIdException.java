package mk.finki.ukim.mk.lab3.model.exception;

public class NoCategoryByIdException extends Exception {
    public NoCategoryByIdException(Long id) {
        super(String.format("No Category with id=%d is found!", id));
    }
}
