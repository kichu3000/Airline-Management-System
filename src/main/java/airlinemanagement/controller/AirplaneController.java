package airlinemanagement.controller;

import airlinemanagement.model.Airplane;
import airlinemanagement.service.AirplaneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AirplaneController {
    private final AirplaneService service;

    public AirplaneController(AirplaneService service) { this.service = service; }

    @GetMapping("/airplanes")
    public String list(Model model) {
        model.addAttribute("airplanes", service.findAll());
        return "airplanes";
    }

    @GetMapping("/airplanes/new")
    public String createForm(Model model) {
        model.addAttribute("airplane", new Airplane());
        return "airplane_form";
    }

    @PostMapping("/airplanes")
    public String save(@ModelAttribute Airplane airplane) {
        service.save(airplane);
        return "redirect:/airplanes";
    }

    @GetMapping("/airplanes/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        var ap = service.findById(id).orElse(new Airplane());
        model.addAttribute("airplane", ap);
        return "airplane_form";
    }

    @GetMapping("/airplanes/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/airplanes";
    }
}
