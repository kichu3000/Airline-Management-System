package airlinemanagement.controller;

import airlinemanagement.model.*;

import java.nio.channels.Pipe.SourceChannel;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import airlinemanagement.service.UserService;
import jakarta.servlet.http.HttpSession;

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

    // This is login
    @PostMapping("/login")
    public String login(@RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        User user = userService.validateUser(email, password);
        if (user != null) {
            session.setAttribute("user", user);
            redirectAttributes.addFlashAttribute("success", "Login successful!");
            return "redirect:/dashboard";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid credentials");
            return "redirect:/";
        }
    }

    // Code for dashboard
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");// get user from session
        if (user == null) {
            return "redirect:/";// not logged in → redirect to ..
        }
        model.addAttribute("user", user);
        System.out.println("User -- " + user);
        return "dashboard";
    }

    // code for logout😊
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        System.out.println("Before invalidate: " + session.getAttribute("user"));
        session.invalidate();
        System.out.println("After invalidate: " + session.getAttribute("user"));
        return "redirect:/";
    }

}