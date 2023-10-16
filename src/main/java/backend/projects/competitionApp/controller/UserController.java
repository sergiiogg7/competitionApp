package backend.projects.competitionApp.controller;

import backend.projects.competitionApp.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/user")
public class UserController {



    @GetMapping
    public ResponseEntity<User> loginUser(Authentication authentication) {
        return null;
    }

    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return null;
    }

}
