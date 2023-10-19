package backend.projects.competitionApp.service;

import backend.projects.competitionApp.entity.Room;
import backend.projects.competitionApp.entity.RoomRequest;

import java.util.List;

public interface RoomService {

    Room createRoom(Room room);
    Room getRoomById(Long id);
    List<Room> searchRooms(String query);
    List<Room> getAllRooms();
    RoomRequest createRoomRequest(Long userId, Long roomId);
    Room grantAccessToUser(Long roomId, Long userId);
    Room revokeAccess(Long roomId, Long userId);

}
