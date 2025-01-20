package mk.ukim.finki.wp.kol2022.g1.service;

import mk.ukim.finki.wp.kol2022.g1.model.Employee;
import mk.ukim.finki.wp.kol2022.g1.model.EmployeeType;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDate;
import java.util.List;

public interface UserService extends UserDetailsService {
    Employee register(String name, String email, String password, EmployeeType type, List<Long> skillId, LocalDate employmentDate);
}

