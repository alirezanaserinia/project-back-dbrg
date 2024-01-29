package ir.ac.ut.project.controller;

import ir.ac.ut.project.data.JwtResponse;
import ir.ac.ut.project.data.User;
import ir.ac.ut.project.repository.UserRepository;
import ir.ac.ut.project.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/register")
public class RegisterController {
    private UserRepository userRepository;
    private PasswordEncoder encoder;
    private AuthenticationService authenticationService;


    public RegisterController(UserRepository userRepository, PasswordEncoder encoder, AuthenticationService authenticationService){

        this.userRepository = userRepository;
        this.encoder = encoder;
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestParam String username, @RequestParam String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }

        // Create new user's account
        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));

        userRepository.save(user);

        String jwt = authenticationService.authenticateUser(username,password);

        return ResponseEntity.ok(new JwtResponse(jwt, username));
    }
}
