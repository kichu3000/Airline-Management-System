package airlinemanagement.service;

import airlinemanagement.model.*;
import airlinemanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    public String saveUser(User user) {
        if (userRepo.existsByEmail(user.getEmail())) {
            return "User alredy exist";
        }
        userRepo.save(user);
        return "Stored sucessfully";
    }

    public User validateUser(String email, String password) {
        User user = userRepo.findByEmail(email);// the findByEmail method will be auto-generated
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
