package backend.projects.competitionApp.controller;

import backend.projects.competitionApp.entity.Authority;
import backend.projects.competitionApp.entity.User;
import backend.projects.competitionApp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Tag(name = "Auth", description = "the Auth API")
public class AuthController {

    private UserService userService;

    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    @SecurityRequirement(name = "basicAuth")
    @Operation(summary = "Login user", description = "")
    public ResponseEntity<User> loginUser(Authentication authentication) {
        return new ResponseEntity<User>(this.userService.getUserByEmail(authentication.getName()), HttpStatus.OK);
    }

    @PostMapping("/register")
    @Operation(summary = "Register user", description = "")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User savedUser = null;
        ResponseEntity response = null;
        String hashedPwd = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPwd);
        savedUser = this.userService.createUser(user);
        if (savedUser.getId() > 0) {
            response = ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("User succesfully registered");
        }
        return response;
    }

}
