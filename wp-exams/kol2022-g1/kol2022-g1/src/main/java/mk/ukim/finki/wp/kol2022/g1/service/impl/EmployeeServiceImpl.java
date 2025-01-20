package mk.ukim.finki.wp.kol2022.g1.service.impl;

import mk.ukim.finki.wp.kol2022.g1.model.Employee;
import mk.ukim.finki.wp.kol2022.g1.model.EmployeeType;
import mk.ukim.finki.wp.kol2022.g1.model.exceptions.InvalidEmployeeIdException;
import mk.ukim.finki.wp.kol2022.g1.repository.EmployeeRepository;
import mk.ukim.finki.wp.kol2022.g1.service.EmployeeService;
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
public class EmployeeServiceImpl implements EmployeeService, UserDetailsService {
    private final EmployeeRepository employeeRepository;
    private final SkillServiceImpl skillService;
    private final PasswordEncoder passwordEncoder;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, SkillServiceImpl skillService, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.skillService = skillService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Employee> listAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(InvalidEmployeeIdException::new);
    }

    @Override
    public Employee create(String name, String email, String password, EmployeeType type, List<Long> skillId, LocalDate employmentDate) {
        Employee employee = new Employee(
                name,
                email,
                passwordEncoder.encode(password),
                type,
                skillId.stream().map(skillService::findById).collect(Collectors.toList()),
                employmentDate
        );

        employeeRepository.save(employee);
        return employee;
    }

    @Override
    public Employee update(Long id, String name, String email, String password, EmployeeType type, List<Long> skillId, LocalDate employmentDate) {
        Employee employee = findById(id);
        employee.setName(name);
        employee.setEmail(email);
        employee.setPassword(passwordEncoder.encode(password));
        employee.setType(type);
        employee.setSkills(skillId.stream().map(skillService::findById).collect(Collectors.toList()));
        employee.setEmploymentDate(employmentDate);

        employeeRepository.save(employee);

        return employee;
    }
    @Override
    public Employee delete(Long id) {
        Employee employee = findById(id);
        employeeRepository.delete(employee);
        return employee;
    }

    @Override
    public List<Employee> filter(Long skillId, Integer yearsOfService) {
        List<Employee> employees = listAll();

        if (skillId != null) {
            employees = employeeRepository.findAllBySkillsContaining(skillService.findById(skillId));
        }

        if (yearsOfService != null) {
            employees = employees.stream()
                    .filter(employee -> employee.yearsOfService() > yearsOfService)
                    .collect(Collectors.toList());
        }

        return employees;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));

        return new User(employee.getEmail(),
                employee.getPassword(),
                Stream.of(new SimpleGrantedAuthority("ROLE_" + employee.getType())).collect(Collectors.toList())
        );
    }
}
