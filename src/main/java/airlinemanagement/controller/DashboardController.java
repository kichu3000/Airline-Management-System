package airlinemanagement.controller;

import airlinemanagement.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @ModelAttribute
    public void addUserToModel(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
        }
    }

    @GetMapping("/upcoming")
    public String upcoming(Model model) {
        model.addAttribute("activeTab", "upcoming");
        return "dashboard"; // Thymeleaf template
    }

    @GetMapping("/history")
    public String history(Model model) {
        model.addAttribute("activeTab", "history");
        return "dashboard";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        model.addAttribute("activeTab", "profile");
        return "dashboard";
    }
}
