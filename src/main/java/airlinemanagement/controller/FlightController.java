package airlinemanagement.controller;

import airlinemanagement.model.Flight;
import airlinemanagement.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FlightController {
    
    private final FlightService flightService;
    
    @GetMapping("/flight")
    public String flightPage(Model model) {
        List<Flight> flights = flightService.getAllFlights();
        model.addAttribute("flights", flights);
        return "flight";
    }
    
    @PostMapping("/flight/search")
    public String searchFlights(
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @RequestParam(required = false) String date,
            Model model) {
        
        List<Flight> flights = flightService.searchFlights(from, to, date);
        model.addAttribute("flights", flights);
        model.addAttribute("searchFrom", from);
        model.addAttribute("searchTo", to);
        model.addAttribute("searchDate", date);
        return "flight";
    }
    
    @GetMapping("/flight/{id}")
    public String flightDetails(@PathVariable Long id, Model model) {
        return flightService.getFlightById(id)
                .map(flight -> {
                    model.addAttribute("flight", flight);
                    return "flight-details";
                })
                .orElse("redirect:/flight");
    }
}
