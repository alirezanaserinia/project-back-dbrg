package ir.ac.ut.project.controller;

import ir.ac.ut.project.data.JwtResponse;
import ir.ac.ut.project.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/login")
public class LoginController {
    private AuthenticationService authenticationService;

    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<?> authenticateUser(@RequestParam String username, @RequestParam String password) {

        String jwt = authenticationService.authenticateUser(username,password);

        return ResponseEntity.ok(new JwtResponse(jwt, username));
    }
}
