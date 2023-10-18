package backend.projects.competitionApp.service.impl;

import backend.projects.competitionApp.entity.Room;
import backend.projects.competitionApp.repository.RoomRepository;
import backend.projects.competitionApp.service.RoomService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {

    private RoomRepository roomRepository;

    @Override
    public Room createRoom(Room room) {
        Room savedRoom = this.roomRepository.save(room);
        return savedRoom;
    }
    @Override
    public List<Room> getAllRooms() {
        List<Room> rooms = this.roomRepository.findAll();
        return rooms;
    }
}
