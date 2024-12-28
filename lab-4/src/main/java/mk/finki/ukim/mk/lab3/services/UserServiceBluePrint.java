package mk.finki.ukim.mk.lab3.services;

import mk.finki.ukim.mk.lab3.model.BookedEvent;
import mk.finki.ukim.mk.lab3.model.User;
import mk.finki.ukim.mk.lab3.model.exception.InvalidUserCredentials;

public interface UserServiceBluePrint {
    User findByUsername(String username);
    User authUser(String name, String password) throws InvalidUserCredentials;
    void bookEventForUser(String username, BookedEvent bookedEvent);
}
