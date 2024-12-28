package mk.finki.ukim.mk.lab3.web;

import jakarta.servlet.http.HttpServletRequest;
import mk.finki.ukim.mk.lab3.model.User;
import mk.finki.ukim.mk.lab3.model.exception.InvalidUserCredentials;
import mk.finki.ukim.mk.lab3.services.impl.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getLoginPage(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("error", error);
        return "login";
    }

    @PostMapping
    public String loginSuccess(@RequestParam("password") String password,
                               @RequestParam("username") String username,
                               Model model,
                               HttpServletRequest request) {

        User user = null;

        try {
            user = userService.authUser(username, password);
            request.getSession().setAttribute("user", user);
        } catch (InvalidUserCredentials ex) {
            return "redirect:/login?error=" + ex.getMessage();
        }

        return "redirect:/events";

    }

}
