package mk.finki.ukim.mk.lab3.services.impl;

import mk.finki.ukim.mk.lab3.model.BookedEvent;
import mk.finki.ukim.mk.lab3.model.User;
import mk.finki.ukim.mk.lab3.model.exception.InvalidUserCredentials;
import mk.finki.ukim.mk.lab3.repository.UserRepository;
import mk.finki.ukim.mk.lab3.services.UserServiceBluePrint;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserServiceBluePrint {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User authUser(String username, String password) throws InvalidUserCredentials {

        Optional<User> userOptional = userRepository.findUserByUsernameAndPassword(username, password);

        if (userOptional.isEmpty())
            throw new InvalidUserCredentials();

        return userOptional.get();
    }

    @Override
    public void bookEventForUser(String username, BookedEvent bookedEvent) {
        User user = userRepository.findByUsername(username);
        user.getBookedEvents().add(bookedEvent);
    }
}
