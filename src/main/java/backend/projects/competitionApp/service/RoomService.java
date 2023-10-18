package backend.projects.competitionApp.service;

import backend.projects.competitionApp.entity.Room;

import java.util.List;

public interface RoomService {

    Room createRoom(Room room);
    List<Room> getAllRooms();

}
