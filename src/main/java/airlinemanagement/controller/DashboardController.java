package airlinemanagement.controller;

import airlinemanagement.service.*;
import airlinemanagement.model.*;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private BookingService bookingService;
    @Autowired
    FlightService flightService;

    @ModelAttribute
    public void addUserToModel(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
        }
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



    

    @GetMapping("/booking")
    public String bookingPage(@RequestParam(value = "flightId", required = false) Long flightId,
            Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return "redirect:/";

        Flight selectedFlight = null;
        if (flightId != null) {
            selectedFlight = flightService.getFlightById(flightId);
        } else {
            selectedFlight = (Flight) session.getAttribute("selectedFlight");
        }

        if (selectedFlight == null)
            return "redirect:/flights";

        model.addAttribute("activeTab", "booking");
        model.addAttribute("flight", selectedFlight);
        model.addAttribute("user", user);
        session.removeAttribute("selectedFlight");

        return "dashboard";
    }

}
