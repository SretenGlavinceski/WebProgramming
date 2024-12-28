package mk.finki.ukim.mk.lab3.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/access_denied")
public class AccessDeniedController {

    @GetMapping
    public String accessDeniedView(Model model) {
        model.addAttribute("bodyContent", "access_denied");
        return "master-template";
    }
}
