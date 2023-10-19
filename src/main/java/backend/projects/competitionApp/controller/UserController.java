package backend.projects.competitionApp.controller;

import backend.projects.competitionApp.entity.RoomRequest;
import backend.projects.competitionApp.service.UserService;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    //Obtener todas las requests de usuarios a las rooms en las que el dueño es user_id
    @GetMapping("/{user_id}/room/requests")
    public ResponseEntity<List<RoomRequest>> getAllRequestsToUserRooms(@PathVariable("user_id") Long userId) {
        return new ResponseEntity<>(this.userService.getAllRequestsToUserRooms(userId), HttpStatus.OK);
    }




}
