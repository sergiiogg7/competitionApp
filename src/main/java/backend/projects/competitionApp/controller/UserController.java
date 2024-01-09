package backend.projects.competitionApp.controller;

import backend.projects.competitionApp.entity.RoomRequest;
import backend.projects.competitionApp.entity.User;
import backend.projects.competitionApp.exception.UnauthorizedActionException;
import backend.projects.competitionApp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
@Tag(name = "User", description = "the User API")
public class UserController {

    private UserService userService;

    private PasswordEncoder passwordEncoder;

    @PutMapping("/{user_id}")
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

    @DeleteMapping("/{user_id}")
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

    //Obtener todas las requests de usuarios a las rooms en las que el dueño es user_id
    @GetMapping("/{user_id}/room/requests")
    @Operation( summary = "Get all user request to the rooms where the user is the owner", description = "" +
            "Get all user request to the rooms where the user, with identifier userId, is the owner")
    public ResponseEntity<List<RoomRequest>> getAllRequestsToUserRooms(@PathVariable("user_id") Long userId) {
        return new ResponseEntity<>(this.userService.getAllRequestsToUserRooms(userId), HttpStatus.OK);
    }

}
