package backend.projects.competitionApp.controller;

import backend.projects.competitionApp.entity.Room;
import backend.projects.competitionApp.entity.RoomRequest;
import backend.projects.competitionApp.exception.UnauthorizedActionException;
import backend.projects.competitionApp.service.DataPlayerService;
import backend.projects.competitionApp.service.RoomRequestService;
import backend.projects.competitionApp.service.RoomService;
import backend.projects.competitionApp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/room")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Room", description = "the room API")
public class RoomController {

    private RoomService roomService;

    @PostMapping
    @Operation(summary = "Create a competition room", description = "")
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        Room savedRoom = this.roomService.createRoom(room);
        return new ResponseEntity<>(savedRoom, HttpStatus.CREATED);
    }

    @PutMapping("/{room_id}")
    @Operation(summary = "Update a competition room", description = "")
    public ResponseEntity<Room> updateRoomById(@RequestBody Room room, @PathVariable("room_id") Long id) {
        boolean userAuthorized = false;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Room existingRoom = this.roomService.getRoomById(id);
            if (username.equals(existingRoom.getOwner().getEmail())) {
                userAuthorized = true;
            }
        }
        if (userAuthorized) {
            Room updatedRoom = this.roomService.updateRoomById(room, id);
            return new ResponseEntity<>(updatedRoom, HttpStatus.CREATED);
        } else {
            throw new UnauthorizedActionException();
        }
    }

    @DeleteMapping("/{room_id}")
    @Operation(summary = "Delete a competition room", description = "")
    public ResponseEntity<String> deleteRoomById(@PathVariable("room_id") Long id) {
        boolean userAuthorized = false;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Room existingRoom = this.roomService.getRoomById(id);
            if (username.equals(existingRoom.getOwner().getEmail())) {
                userAuthorized = true;
            }
        }

        if (userAuthorized) {
            this.roomService.deleteRoomById(id);
            return new ResponseEntity<String>("Room with id " + id + " has been deleted successfully.", HttpStatus.OK);
        } else {
            throw new UnauthorizedActionException();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get room by identifier", description = "")
    public ResponseEntity<Room> getRoomById(@PathVariable("id") Long id) {
        Room room = this.roomService.getRoomById(id);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get all rooms", description = "")
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = this.roomService.getAllRooms();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @GetMapping("/search")
    @Operation(summary = "Search rooms by name and description", description = "")
    public ResponseEntity<List<Room>> searchRooms(@RequestParam String query) {
        List<Room> rooms = this.roomService.searchRooms(query);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @PostMapping("/{room_id}/user/{user_id}/request")
    @Operation(summary = "User requests access to a room", description = "The user identified with userId will request " +
            "access to the owner of the room with roomId identifier")
    public ResponseEntity<RoomRequest> requestAccess(@PathVariable("user_id") Long userId,
                                                     @PathVariable("room_id") Long roomId) {
        return new ResponseEntity<>(this.roomService.createRoomRequest(userId, roomId), HttpStatus.OK);
    }

    @PostMapping("/{room_id}/user/{user_id}")
    @Operation(summary = "Grant user access to a room", description = "Owner of the room will grant access" +
            "to the user with user_id to the room with roomId")
    public ResponseEntity<Room> grantAccess(@PathVariable("room_id") Long roomId,
                                             @PathVariable("user_id") Long userId) {
        Room room = this.roomService.grantAccessToUser(roomId, userId);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @DeleteMapping("/{room_id}/user/{user_id}")
    @Operation(summary = "Revoke user access to a room", description = "Owner of the room will revoke access" +
            "to the user with user_id to the room with roomId")
    public ResponseEntity<Room> revokeAccess(@PathVariable("room_id") Long roomId,
                                            @PathVariable("user_id") Long userId) {
        Room room = this.roomService.revokeAccess(roomId, userId);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }
}
