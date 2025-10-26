package airlinemanagement.controller;

import airlinemanagement.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String upcoming(Model model, HttpSession session) {
        // ensure only authenticated users can access dashboard pages
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/"; // not logged in
        }
        model.addAttribute("activeTab", "upcoming");
        return "user-dashboard";
    }

    @GetMapping("/history")
    public String history(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        model.addAttribute("activeTab", "history");
        return "user-dashboard";
    }

    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        model.addAttribute("activeTab", "profile");
        return "user-dashboard";
    }

    @GetMapping("/booking")
    public String bookingPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        model.addAttribute("activeTab", "booking");
        return "user-dashboard";
    }
    
    @GetMapping("/search")
    public String searchFlights(@RequestParam(required = false) String source,
                               @RequestParam(required = false) String destination,
                               @RequestParam(required = false) String date,
                               Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        
        // Get all flights for now (you can implement search logic later)
        model.addAttribute("flights", java.util.Collections.emptyList());
        model.addAttribute("activeTab", "booking");
        return "user-dashboard";
    }

}
