package backend.projects.competitionApp.controller;

import backend.projects.competitionApp.entity.Authority;
import backend.projects.competitionApp.entity.User;
import backend.projects.competitionApp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@AllArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private UserService userService;

    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public ResponseEntity<User> loginUser(Authentication authentication) {
        return new ResponseEntity<User>(this.userService.getUserByEmail(authentication.getName()), HttpStatus.OK);
    }

    @PostMapping("/register")
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
