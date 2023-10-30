package backend.projects.competitionApp.service.impl;

import backend.projects.competitionApp.entity.DataPlayer;
import backend.projects.competitionApp.entity.Room;
import backend.projects.competitionApp.entity.RoomRequest;
import backend.projects.competitionApp.entity.User;
import backend.projects.competitionApp.enumeration.RoomRequestState;
import backend.projects.competitionApp.exception.ResourceNotFoundException;
import backend.projects.competitionApp.repository.RoomRepository;
import backend.projects.competitionApp.service.DataPlayerService;
import backend.projects.competitionApp.service.RoomRequestService;
import backend.projects.competitionApp.service.RoomService;
import backend.projects.competitionApp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {

    private UserService userService;

    private DataPlayerService dataPlayerService;

    private RoomRepository roomRepository;

    private RoomRequestService roomRequestService;

    @Override
    public Room createRoom(Room room) {
        Room savedRoom = this.roomRepository.save(room);
        return savedRoom;
    }

    @Override
    public Room getRoomById(Long id) {
        Room room = this.roomRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Room", "id", id+"")
        );
        return room;
    }

    @Override
    public List<Room> searchRooms(String query) {
        return this.roomRepository.searchRoomsJPQL(query);
    }

    @Override
    public List<Room> getAllRooms() {
        List<Room> rooms = this.roomRepository.findAll();
        return rooms;
    }

    @Override
    public RoomRequest createRoomRequest(Long userId, Long roomId) {

        Room room = this.roomRepository.findById(roomId).orElseThrow(
                () -> new ResourceNotFoundException("Room", "id", roomId+"")
        );
        User user = this.userService.getUserById(userId);
        RoomRequest savedRoomRequest = this.roomRequestService.save(new RoomRequest(RoomRequestState.PENDING, user, room));
        return savedRoomRequest;
    }

    @Override
    @Transactional
    public Room grantAccessToUser(Long roomId, Long userId) {
        Room room = this.getRoomById(roomId);
        User user = this.userService.getUserById(userId);
        RoomRequest roomRequest = this.roomRequestService.findByUserAndRoom(user, room);
        if (roomRequest.getState() == RoomRequestState.PENDING) {
            roomRequest.setState(RoomRequestState.ACCEPTED);
            this.roomRequestService.save(roomRequest);
            List<Double> profits = new ArrayList<>();
            DataPlayer dataPlayer = new DataPlayer(profits, user, room);
            this.dataPlayerService.save(dataPlayer);
        }

        return room;
    }

    @Override
    @Transactional
    public Room revokeAccess(Long roomId, Long userId) {
        Room room = this.getRoomById(roomId);
        User user = this.userService.getUserById(userId);
        DataPlayer dp = this.dataPlayerService.findDataPlayerByUserAndRoom(user, room);
        this.dataPlayerService.remove(dp);
        return room;
    }

}
