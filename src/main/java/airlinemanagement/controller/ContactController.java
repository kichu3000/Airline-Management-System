package airlinemanagement.controller;

import airlinemanagement.model.*;
import airlinemanagement.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @PostMapping("/contact")
    public String submitContact(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String message,
            RedirectAttributes redirectAttributes) {

        System.out.println("data is here");
        Contact contact = new Contact();
        contact.setName(name);
        contact.setEmail(email);
        contact.setMessage(message);
        contactRepository.save(contact);

        redirectAttributes.addFlashAttribute("success", "Message sent successfully!");
        return "redirect:/contact";
    }
}
