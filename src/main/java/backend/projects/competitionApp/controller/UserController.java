package backend.projects.competitionApp.controller;

import backend.projects.competitionApp.entity.RoomRequest;
import backend.projects.competitionApp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
@Tag(name = "User", description = "the User API")
public class UserController {

    private UserService userService;

    //Obtener todas las requests de usuarios a las rooms en las que el dueño es user_id
    @GetMapping("/{user_id}/room/requests")
    @Operation( summary = "Get all user request to the rooms where the user is the owner", description = "" +
            "Get all user request to the rooms where the user, with identifier userId, is the owner")
    public ResponseEntity<List<RoomRequest>> getAllRequestsToUserRooms(@PathVariable("user_id") Long userId) {
        return new ResponseEntity<>(this.userService.getAllRequestsToUserRooms(userId), HttpStatus.OK);
    }




}
