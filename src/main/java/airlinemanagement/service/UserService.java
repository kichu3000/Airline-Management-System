package airlinemanagement.service;

import java.util.List;
import airlinemanagement.Util.*;
import airlinemanagement.model.User;
import airlinemanagement.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public String saveUser(User user) {
        if (userRepo.existsByEmail(user.getEmail())) {
            return "User already exists 😠";
        }

        // Hash the password here
        user.setPassword(user.getPassword());
        userRepo.save(user);
        return "Account created successfully! 😚";
    }

    public User validateUser(String email, String password) {
        User user = userRepo.findByEmail(email);

        System.out.println(ConsoleColor.GREEN + "Validate User : " + user + ConsoleColor.RESET);

        if (user != null && password.equals(user.getPassword())) {
            return user;
        }
        return null;
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @PostConstruct
    public void createDefaultAdmin() {
        String adminEmail = "admin@gmail.com";

        if (!userRepo.existsByEmail(adminEmail)) {
            User admin = new User();
            admin.setName("Admin");
            admin.setEmail(adminEmail);
            admin.setPassword("admin123");
            admin.setAdmin(true);

            userRepo.save(admin);
            System.out.println(
                    ConsoleColor.GREEN + " Default admin created: " + adminEmail + " / admin123" + ConsoleColor.RESET);
        } else {
            System.out.println(ConsoleColor.GREEN + " Admin already exists." + ConsoleColor.RESET);
        }
    }
}
