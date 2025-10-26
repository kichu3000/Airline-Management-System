package airlinemanagement.controller;

import airlinemanagement.Util.*;
import airlinemanagement.model.*;
import airlinemanagement.service.FlightService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FlightController {

    // Constructor injection of FlightService
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @Autowired
    private FlightService flightService;

    // Show all available flights
    @GetMapping("/flights")
    public String showFlights(Model model) {
        model.addAttribute("flights", flightService.getAllFlights());
        return "showFlight"; // the Thymeleaf template name
    }

    @GetMapping("/flights/book")
    public String bookFlight(@RequestParam("flightId") Long flightId,
            HttpSession session,
            RedirectAttributes redirectAttributes,
            Model model) {

        Object loggedUser = session.getAttribute("user");
        if (loggedUser == null) {
            redirectAttributes.addFlashAttribute("error", "Please login first to book a flight.");
            return "redirect:/login";
        }

        Flight flight = flightService.getFlightById(flightId);
        if (flight == null) {
            redirectAttributes.addFlashAttribute("error", "Flight not found.");
            return "redirect:/flight";
        }

        model.addAttribute("flight", flight);

        System.out.println(ConsoleColor.GREEN + "Executed the booking...." +
                ConsoleColor.RESET);// debugg

        return "redirect:/dashboard/booking"; // your user dashboard booking page
    }



    

}
