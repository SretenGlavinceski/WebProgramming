package mk.ukim.finki.wp.kol2022.g2.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Student {

    public Student(String name, String email, String password, StudentType type, List<Course> courses, LocalDate enrollmentDate) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
        this.courses = courses;
        this.enrollmentDate = enrollmentDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate enrollmentDate;

    private String name;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private StudentType type;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Course> courses;

    public Long yearsOfStudy() {
        return ChronoUnit.YEARS.between(this.enrollmentDate, LocalDate.now());
    }
}
