package mk.ukim.finki.wp.kol2022.g2.service.impl;

import mk.ukim.finki.wp.kol2022.g2.model.Student;
import mk.ukim.finki.wp.kol2022.g2.model.StudentType;
import mk.ukim.finki.wp.kol2022.g2.model.exceptions.InvalidStudentIdException;
import mk.ukim.finki.wp.kol2022.g2.repository.StudentRepository;
import mk.ukim.finki.wp.kol2022.g2.service.StudentService;
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
public class StudentServiceImpl implements StudentService, UserDetailsService {
    private final StudentRepository studentRepository;
    private final CourseServiceImpl courseService;
    private final PasswordEncoder passwordEncoder;

    public StudentServiceImpl(StudentRepository studentRepository, CourseServiceImpl courseService, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.courseService = courseService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Student> listAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id).orElseThrow(InvalidStudentIdException::new);
    }

    @Override
    public Student create(String name, String email, String password, StudentType type, List<Long> courseId, LocalDate enrollmentDate) {
        Student student = new Student(
                name,
                email,
                passwordEncoder.encode(password),
                type,
                courseId.stream().map(id -> courseService.findById(id)).collect(Collectors.toList()),
                enrollmentDate
        );

        studentRepository.save(student);

        return student;
    }

    @Override
    public Student update(Long id, String name, String email, String password, StudentType type, List<Long> coursesId, LocalDate enrollmentDate) {
        Student student = findById(id);
        student.setName(name);
        student.setEmail(email);
        student.setPassword(passwordEncoder.encode(password));
        student.setType(type);
        student.setCourses(coursesId.stream().map(i -> courseService.findById(i)).collect(Collectors.toList()));
        student.setEnrollmentDate(enrollmentDate);

        studentRepository.save(student);
        return student;
    }

    @Override
    public Student delete(Long id) {
        Student student = findById(id);
        studentRepository.delete(student);
        return student;
    }

    @Override
    public List<Student> filter(Long courseId, Integer yearsOfStudying) {
        List<Student> students = listAll();

        if (courseId != null) {
            students = students.stream()
                    .filter(student -> student.getCourses().contains(courseService.findById(courseId)))
                    .collect(Collectors.toList());
        }

        if (yearsOfStudying != null) {
            students = students.stream()
                    .filter(student -> student.yearsOfStudy() > yearsOfStudying)
                    .collect(Collectors.toList());
        }

        return students;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentRepository.findByEmail(username);

        return new User(
                student.getEmail(),
                student.getPassword(),
                Stream.of(new SimpleGrantedAuthority("ROLE_" + student.getType())).collect(Collectors.toList())
        );

    }
}
