package backend.projects.competitionApp.controller;

import backend.projects.competitionApp.entity.Room;
import backend.projects.competitionApp.entity.RoomRequest;
import backend.projects.competitionApp.service.DataPlayerService;
import backend.projects.competitionApp.service.RoomRequestService;
import backend.projects.competitionApp.service.RoomService;
import backend.projects.competitionApp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/api/room")
public class RoomController {

    private RoomService roomService;

    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        Room savedRoom = this.roomService.createRoom(room);
        return new ResponseEntity<>(savedRoom, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable("id") Long id) {
        Room room = this.roomService.getRoomById(id);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = this.roomService.getAllRooms();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Room>> searchRooms(@RequestParam String query) {
        List<Room> rooms = this.roomService.searchRooms(query);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }


    @PostMapping("/{user_id}/room/{room_id}/request")
    public ResponseEntity<RoomRequest> requestAccess(@PathVariable("user_id") Long userId,
                                                     @PathVariable("room_id") Long roomId) {
        return new ResponseEntity<>(this.roomService.createRoomRequest(userId, roomId), HttpStatus.OK);
    }

    //Añadir usuario a room
    //Debe de existir un Request Room
    @PostMapping("/{room_id}/user/{user_id}")
    public ResponseEntity<Room> grantAccess(@PathVariable("room_id") Long roomId,
                                             @PathVariable("user_id") Long userId) {
        Room room = this.roomService.grantAccessToUser(roomId, userId);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @DeleteMapping("/{room_id}/user/{user_id}")
    public ResponseEntity<Room> revokeAccess(@PathVariable("room_id") Long roomId,
                                            @PathVariable("user_id") Long userId) {
        Room room = this.roomService.revokeAccess(roomId, userId);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }
}
