package mk.finki.ukim.mk.lab3.model.exception;

public class InvalidUserCredentials extends Exception{
    public InvalidUserCredentials() {
        super("Invalid user credentials provided, try again!");
    }
}
