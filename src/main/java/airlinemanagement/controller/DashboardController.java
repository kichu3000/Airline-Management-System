package airlinemanagement.controller;

import airlinemanagement.service.*;
import airlinemanagement.model.*;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    FlightService flightService;
    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addUserToModel(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
        }
    }

    @GetMapping("/booking")
    public String bookingPage(@RequestParam(value = "flightId", required = false) Long flightId,
            Model model,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Please login first😊");
            return "redirect:/"; // <- only return here if user is null
        }

        Flight selectedFlight = null;
        if (flightId != null) {
            selectedFlight = flightService.getFlightById(flightId);
        } else {
            selectedFlight = (Flight) session.getAttribute("selectedFlight");
        }

        if (selectedFlight == null) {
            redirectAttributes.addFlashAttribute("error", "Flight not selected");
            return "redirect:/flights";
        }

        model.addAttribute("activeTab", "booking");
        model.addAttribute("flight", selectedFlight);
        model.addAttribute("user", user);
        session.removeAttribute("selectedFlight");

        return "dashboard";
    }

    @GetMapping("/upcoming")
    public String upcomingBookings(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login"; // or home page
        }

        List<Booking> bookings = bookingService.getBookingsByUser(user.getId());
        model.addAttribute("bookings", bookings);
        model.addAttribute("activeTab", "upcoming");

        return "dashboard";
    }

    @GetMapping("/profile")
    public String profilePage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("activeTab", "profile");
        return "dashboard";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute User updatedUser, HttpSession session,
            RedirectAttributes redirectAttributes) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        // Update the user details
        currentUser.setName(updatedUser.getName());
        currentUser.setEmail(updatedUser.getEmail());

        try {
            User savedUser = userService.updateUser(currentUser);
            session.setAttribute("user", savedUser);
            redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update profile. Please try again.");
        }

        return "redirect:/dashboard/profile";
    }

}
