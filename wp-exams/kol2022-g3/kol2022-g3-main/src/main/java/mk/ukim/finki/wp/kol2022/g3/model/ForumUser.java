package mk.ukim.finki.wp.kol2022.g3.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class ForumUser {

    public ForumUser(String name, String email, String password, ForumUserType type, List<Interest> interests, LocalDate birthday) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
        this.interests = interests;
        this.birthday = birthday;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate birthday;

    private String name;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private ForumUserType type;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Interest> interests;

    public Long userAge() {
        return ChronoUnit.YEARS.between(birthday, LocalDate.now());
    }

}
