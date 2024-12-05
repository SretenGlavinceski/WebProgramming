package mk.finki.ukim.mk.test_controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan

public class TestControllerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestControllerApplication.class, args);
    }

}
