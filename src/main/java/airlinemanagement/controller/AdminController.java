package airlinemanagement.controller;

import airlinemanagement.model.User;
import airlinemanagement.model.Flight;
import airlinemanagement.service.FlightService;
import airlinemanagement.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final FlightService flightService;
    private final UserService userService;

    public AdminController(FlightService flightService, UserService userService) {
        this.flightService = flightService;
        this.userService = userService;
    }

    @ModelAttribute
    public void addAdminToModel(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.isAdmin()) {
            model.addAttribute("admin", user);
        }
    }

    private boolean checkAdmin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user != null && user.isAdmin();
    }

    @GetMapping
    public String dashboard(Model model, HttpSession session) {
        if (!checkAdmin(session))
            return "redirect:/";
        model.addAttribute("activeTab", "home");
        return "admin";
    }

    @GetMapping("/flights")
    public String manageFlights(Model model, HttpSession session) {
        if (!checkAdmin(session))
            return "redirect:/";
        List<Flight> flights = flightService.getAllFlights();
        model.addAttribute("flights", flights);
        model.addAttribute("activeTab", "flights");
        return "admin";
    }

    @GetMapping("/users")
    public String manageUsers(Model model, HttpSession session) {
        if (!checkAdmin(session))
            return "redirect:/";
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("activeTab", "users");
        return "admin";
    }

    @GetMapping("/bookings")
    public String manageBookings(Model model, HttpSession session) {
        if (!checkAdmin(session))
            return "redirect:/";
        model.addAttribute("activeTab", "bookings");
        return "admin";
    }

    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        if (!checkAdmin(session))
            return "redirect:/";
        model.addAttribute("activeTab", "profile");
        return "admin";
    }

    @PostMapping("/flights") // <-- NEW POST MAPPING
    public String addFlight(@ModelAttribute Flight flight, HttpSession session) {
        if (!checkAdmin(session))
            return "redirect:/";
        flightService.createFlight(flight); // Save flight to DB
        return "redirect:/admin"; // Redirect back to flights page
    }
}
