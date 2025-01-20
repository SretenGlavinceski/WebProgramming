package mk.ukim.finki.wp.kol2022.g1.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Employee {

    public Employee(String name, String email, String password, EmployeeType type, List<Skill> skills, LocalDate employmentDate) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
        this.skills = skills;
        this.employmentDate = employmentDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate employmentDate;

    private String name;

    private String email;

    private String password;

    @Setter
    @Enumerated(EnumType.STRING)
    private EmployeeType type;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Skill> skills;

    public Long yearsOfService() {
        return ChronoUnit.YEARS.between( this.employmentDate, LocalDate.now());
    }
}
