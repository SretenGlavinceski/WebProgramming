package mk.ukim.finki.wp.kol2022.g1.service.impl;

import mk.ukim.finki.wp.kol2022.g1.model.Employee;
import mk.ukim.finki.wp.kol2022.g1.model.EmployeeType;
import mk.ukim.finki.wp.kol2022.g1.repository.EmployeeRepository;
import mk.ukim.finki.wp.kol2022.g1.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final SkillServiceImpl skillService;

    public UserServiceImpl(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder, SkillServiceImpl skillService) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.skillService = skillService;
    }

    @Override
    public Employee register(String name, String email, String password, EmployeeType type, List<Long> skillId, LocalDate employmentDate) {
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            throw new RuntimeException();
        }

        if (this.employeeRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException();
        }

        Employee user = new Employee(
                name,
                email,
                passwordEncoder.encode(password),
                type,
                skillId.stream().map(skillService::findById).toList(),
                employmentDate
        );

        return employeeRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return employeeRepository.findByEmail(username).orElseThrow(RuntimeException::new);
    }
}
