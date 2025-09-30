package airlinemanagement.controller;

import airlinemanagement.model.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import airlinemanagement.service.UserService;

@RestController
@RequestMapping("/")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public String signup(@RequestParam String name,
            @RequestParam String email,
            @RequestParam String password) {
        User user = new User(); 
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        return userService.saveUser(user);
    }
}