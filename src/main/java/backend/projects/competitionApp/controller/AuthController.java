package backend.projects.competitionApp.controller;

import backend.projects.competitionApp.entity.Authority;
import backend.projects.competitionApp.entity.User;
import backend.projects.competitionApp.exception.UnauthorizedActionException;
import backend.projects.competitionApp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @PutMapping("/user/{user_id}")
    @Operation(summary = "Update User", description = "")
    public ResponseEntity<User> updateUserById(@RequestBody User user, @PathVariable("user_id") Long id) {
        boolean userAuthorized = false;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User existingUser = this.userService.getUserById(id);
            if (username.equals(existingUser.getEmail())) { userAuthorized = true; }
        }

        if (userAuthorized) {
            String hashedPwd = this.passwordEncoder.encode((user.getPassword()));
            user.setPassword(hashedPwd);
            User updatedUser = this.userService.updateUserById(user, id);
            return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
        } else {
            throw new UnauthorizedActionException();
        }
    }

    @DeleteMapping("/user/{user_id}")
    @Operation(summary = "Delete User", description = "")
    public ResponseEntity<String> deleteUserById(@PathVariable("user_id") Long id) {
        boolean userAuthorized = false;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.getUserById(id);

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            if (username.equals(user.getEmail())) { userAuthorized = true; }
        }

        if (userAuthorized) {
            this.userService.deleteUserById(id);
            return new ResponseEntity<String>("User with id " + id + " has been deleted succesfully.", HttpStatus.OK);
        }else {
            throw new UnauthorizedActionException();
        }
    }
}
