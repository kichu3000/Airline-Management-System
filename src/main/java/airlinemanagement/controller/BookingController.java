package airlinemanagement.controller;

import airlinemanagement.model.*;
import airlinemanagement.service.BookingService;
import airlinemanagement.service.FlightService;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;
    private final FlightService flightService;

    public BookingController(BookingService bookingService, FlightService flightService) {
        this.bookingService = bookingService;
        this.flightService = flightService;
    }

    @PostMapping("/confirm")
    public String confirmBooking(@RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String phone,
            @RequestParam String seat,
            @RequestParam Long flightId,
            HttpSession session,
            Model model) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login"; // or wherever you want to redirect
        }

        Flight flight = flightService.getFlightById(flightId);
        String email = user.getEmail();
        Booking booking = new Booking();
        booking.setFirstName(firstName);
        booking.setLastName(lastName);
        booking.setEmail(email);
        booking.setPhone(phone);
        booking.setSeat(seat);
        booking.setFlight(flight);

        bookingService.saveBooking(booking);

        model.addAttribute("message", "Booking Confirmed!");
        return "redirect:/dashboard/upcoming"; // You can create a success page
    }
}
