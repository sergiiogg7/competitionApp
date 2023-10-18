package backend.projects.competitionApp.controller;

import backend.projects.competitionApp.entity.Room;
import backend.projects.competitionApp.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/room")
@AllArgsConstructor
public class RoomController {

    private RoomService roomService;
    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        Room savedRoom = this.roomService.createRoom(room);
        return new ResponseEntity<>(savedRoom, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = this.roomService.getAllRooms();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

}
