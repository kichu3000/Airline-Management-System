package airlinemanagement.controller;

import airlinemanagement.model.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import airlinemanagement.service.UserService;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public String signup(@RequestParam String name,
            @RequestParam String email,
            @RequestParam String password) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        return userService.saveUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
            @RequestParam String password,
            Model model) {
        User user = userService.validateUser(email, password);
        if (user != null) {
            model.addAttribute("user", user);
            return "dashboard";
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "index";
        }
    }
}