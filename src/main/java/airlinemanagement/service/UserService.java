package airlinemanagement.service;

import airlinemanagement.model.*;
import airlinemanagement.repository.UserRepository;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    public String saveUser(User user) {
        if (userRepo.existsByEmail(user.getEmail())) {
            return "User alredy exist😠";
        }
        userRepo.save(user);
        return "Account created successfully!😚";
    }

    public User validateUser(String email, String password) {
        User user = userRepo.findByEmail(email); // auto-generated findByEmail
        if (user != null && BCrypt.checkpw(password, user.getPassword())) { // ✅ use checkpw
            return user;
        }
        return null;
    }
}
