package airlinemanagement.controller;

import airlinemanagement.model.Flight;
import airlinemanagement.model.User;
import airlinemanagement.model.Airplane;
import airlinemanagement.service.FlightService;
import airlinemanagement.service.UserService;
import airlinemanagement.service.AirplaneService;
import airlinemanagement.service.ContactMessageService;
import airlinemanagement.model.ContactMessage;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final FlightService flightService;
    private final UserService userService;
    private final AirplaneService airplaneService;
    private final ContactMessageService contactMessageService;

    public AdminController(FlightService flightService,
                          UserService userService,
                          AirplaneService airplaneService,
                          ContactMessageService contactMessageService) {
        this.flightService = flightService;
        this.userService = userService;
        this.airplaneService = airplaneService;
        this.contactMessageService = contactMessageService;
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
        // Add stats for dashboard
        model.addAttribute("flightCount", flightService.getAllFlights().size());
        model.addAttribute("userCount", userService.getAllUsers().size());
        model.addAttribute("messageCount", contactMessageService.getAllMessages().size());
        return "admin-dashboard";
    }

    @GetMapping("/flights")
    public String manageFlights(Model model, HttpSession session) {
        if (!checkAdmin(session))
            return "redirect:/";
        List<Flight> flights = flightService.getAllFlights();
        model.addAttribute("flights", flights);
        model.addAttribute("activeTab", "flights");
        return "admin-dashboard";
    }
    
    @PostMapping("/flights")
    public String addFlight(@RequestParam String flightNumber,
                           @RequestParam String source,
                           @RequestParam String destination,
                           @RequestParam String departureTime,
                           @RequestParam String arrivalTime,
                           @RequestParam double price,
                           @RequestParam int totalSeats,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        if (!checkAdmin(session))
            return "redirect:/";
        try {
            Flight flight = new Flight();
            flight.setFlightNumber(flightNumber);
            flight.setSource(source);
            flight.setDestination(destination);
            flight.setDepartureTime(java.time.LocalDateTime.parse(departureTime));
            flight.setArrivalTime(java.time.LocalDateTime.parse(arrivalTime));
            flight.setPrice(price);
            flight.setTotalSeats(totalSeats);
            
            flightService.createFlight(flight);
            redirectAttributes.addFlashAttribute("successMessage", "Flight added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to add flight: " + e.getMessage());
        }
        return "redirect:/admin/flights";
    }

    @GetMapping("/users")
    public String manageUsers(Model model, HttpSession session) {
        if (!checkAdmin(session))
            return "redirect:/";
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("activeTab", "users");
        return "admin-dashboard";
    }

    @GetMapping("/bookings")
    public String manageBookings(Model model, HttpSession session) {
        if (!checkAdmin(session))
            return "redirect:/";
        model.addAttribute("activeTab", "bookings");
        return "admin-dashboard";
    }

    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        if (!checkAdmin(session))
            return "redirect:/";
        model.addAttribute("activeTab", "profile");
        return "admin-dashboard";
    }

    // ------------------- Airplane Management -----------------------

    @GetMapping("/airplanes")
    public String manageAirplanes(Model model, HttpSession session) {
        if (!checkAdmin(session))
            return "redirect:/";
        List<Airplane> airplanes = airplaneService.getAllAirplanes();
        model.addAttribute("airplanes", airplanes);
        model.addAttribute("newAirplane", new Airplane());
        model.addAttribute("activeTab", "airplanes");
        return "admin-dashboard";
    }

    @PostMapping("/airplanes")
    public String addAirplane(@ModelAttribute Airplane airplane, HttpSession session,
                              RedirectAttributes redirectAttributes) {
        if (!checkAdmin(session))
            return "redirect:/";
        try {
            airplaneService.createAirplane(airplane);
            redirectAttributes.addFlashAttribute("successMessage", "Airplane added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/airplanes";
    }

    @PostMapping("/airplanes/update/{id}")
    public String updateAirplane(@PathVariable Long id, @ModelAttribute Airplane airplane,
                                 HttpSession session, RedirectAttributes redirectAttributes) {
        if (!checkAdmin(session))
            return "redirect:/";
        try {
            airplaneService.updateAirplane(id, airplane);
            redirectAttributes.addFlashAttribute("successMessage", "Airplane updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/airplanes";
    }

    @PostMapping("/airplanes/delete/{id}")
    public String deleteAirplane(@PathVariable Long id, HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        if (!checkAdmin(session))
            return "redirect:/";
        try {
            airplaneService.deleteAirplane(id);
            redirectAttributes.addFlashAttribute("successMessage", "Airplane deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/airplanes";
    }

    // ------------------- Contact Messages Management -----------------------

    @GetMapping("/messages")
    public String viewMessages(Model model, HttpSession session) {
        if (!checkAdmin(session))
            return "redirect:/";
        List<ContactMessage> messages = contactMessageService.getAllMessages();
        model.addAttribute("messages", messages);
        model.addAttribute("activeTab", "messages");
        return "admin-dashboard";
    }

}
