package airlinemanagement.controller;

import airlinemanagement.model.ContactMessage;
import airlinemanagement.service.ContactMessageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PageController {

    private final ContactMessageService contactMessageService;

    public PageController(ContactMessageService contactMessageService) {
        this.contactMessageService = contactMessageService;
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/flights")
    public String flights() {
        return "flight";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("contactMessage", new ContactMessage());
        return "contact";
    }

    @PostMapping("/contact")
    public String submitContactForm(@ModelAttribute ContactMessage contactMessage,
                                   RedirectAttributes redirectAttributes) {
        try {
            contactMessageService.saveMessage(contactMessage);
            redirectAttributes.addFlashAttribute("successMessage", "Thank you for contacting us! We'll get back to you soon.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to send message. Please try again.");
        }
        return "redirect:/contact";
    }
}
