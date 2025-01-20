package mk.ukim.finki.wp.kol2022.g3.service.impl;

import mk.ukim.finki.wp.kol2022.g3.model.ForumUser;
import mk.ukim.finki.wp.kol2022.g3.model.ForumUserType;
import mk.ukim.finki.wp.kol2022.g3.model.exceptions.InvalidForumUserIdException;
import mk.ukim.finki.wp.kol2022.g3.model.exceptions.InvalidInterestIdException;
import mk.ukim.finki.wp.kol2022.g3.repository.ForumUserRepository;
import mk.ukim.finki.wp.kol2022.g3.service.ForumUserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ForumUserServiceImpl implements ForumUserService, UserDetailsService {
    private final ForumUserRepository forumUserRepository;
    private final InterestServiceImpl interestService;
    private final PasswordEncoder passwordEncoder;

    public ForumUserServiceImpl(ForumUserRepository forumUserRepository, InterestServiceImpl interestService, PasswordEncoder passwordEncoder) {
        this.forumUserRepository = forumUserRepository;
        this.interestService = interestService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<ForumUser> listAll() {
        return forumUserRepository.findAll();
    }

    @Override
    public ForumUser findById(Long id) {
        return forumUserRepository.findById(id)
                .orElseThrow(InvalidForumUserIdException::new);
    }

    @Override
    public ForumUser create(String name, String email, String password, ForumUserType type, List<Long> interestId, LocalDate birthday) {
        ForumUser forumUser = new ForumUser(
                name,
                email,
                passwordEncoder.encode(password),
                type,
                interestId.stream().map(interestService::findById).collect(Collectors.toList()),
                birthday
        );

        forumUserRepository.save(forumUser);
        return forumUser;
    }

    @Override
    public ForumUser update(Long id, String name, String email, String password, ForumUserType type, List<Long> interestId, LocalDate birthday) {
        ForumUser forumUser = findById(id);
        forumUser.setName(name);
        forumUser.setEmail(email);
        forumUser.setPassword(passwordEncoder.encode(password));
        forumUser.setType(type);
        forumUser.setInterests(interestId.stream().map(interestService::findById).collect(Collectors.toList()));
        forumUser.setBirthday(birthday);

        forumUserRepository.save(forumUser);
        return forumUser;
    }

    @Override
    public ForumUser delete(Long id) {
        ForumUser forumUser = findById(id);
        forumUserRepository.delete(forumUser);
        return forumUser;
    }

    @Override
    public List<ForumUser> filter(Long interestId, Integer age) {
        List<ForumUser> users = listAll();

        if (interestId != null) {
            users = users.stream()
                    .filter(forumUser -> forumUser.getInterests().contains(interestService.findById(interestId)))
                    .collect(Collectors.toList());
        }

        if (age != null) {
            users = users.stream()
                    .filter(forumUser -> forumUser.userAge() > age)
                    .collect(Collectors.toList());
        }

        return users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ForumUser user = forumUserRepository.findByEmail(username);

        return new User(
                user.getEmail(),
                user.getPassword(),
                Stream.of(new SimpleGrantedAuthority("ROLE_" + user.getType())).collect(Collectors.toList())
        );
    }
}
