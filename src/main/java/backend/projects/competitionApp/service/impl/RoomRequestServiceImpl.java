package backend.projects.competitionApp.service.impl;

import backend.projects.competitionApp.entity.DataPlayer;
import backend.projects.competitionApp.entity.Room;
import backend.projects.competitionApp.entity.RoomRequest;
import backend.projects.competitionApp.entity.User;
import backend.projects.competitionApp.enumeration.RoomRequestState;
import backend.projects.competitionApp.exception.ResourceNotFoundException;
import backend.projects.competitionApp.repository.RoomRequestRepository;
import backend.projects.competitionApp.service.RoomRequestService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoomRequestServiceImpl implements RoomRequestService {

    private RoomRequestRepository roomRequestRepository;

    @Override
    public RoomRequest save(RoomRequest roomRequest) {
        RoomRequest savedRoomRequest = this.roomRequestRepository.save(roomRequest);
        return savedRoomRequest;
    }

    @Override
    public void deleteRoomRequest(DataPlayer dataPlayer) {
        User user = dataPlayer.getPlayer();
        Room room = dataPlayer.getRoom();
        RoomRequest roomRequest = this.roomRequestRepository.findRoomRequestsByUserAndRoom(user, room).orElseThrow(
                () -> new ResourceNotFoundException("RoomRequest", "User, Room", user.getId()+ " , " + room.getId()+"")
        );
        this.roomRequestRepository.delete(roomRequest);
    }

    public void deleteAcceptedRoomRequest(DataPlayer dataPlayer) {
        User user = dataPlayer.getPlayer();
        Room room = dataPlayer.getRoom();
        RoomRequest roomRequest = this.roomRequestRepository.findAcceptedRoomRequestsByUserAndRoom(user, room).orElseThrow(
                () -> new ResourceNotFoundException("RoomRequest", "User, Room", user.getId()+ " , " + room.getId()+"")
        );
        this.roomRequestRepository.delete(roomRequest);
    }

    public void deletePendingRoomRequest(DataPlayer dataPlayer) {
        User user = dataPlayer.getPlayer();
        Room room = dataPlayer.getRoom();
        RoomRequest roomRequest = this.roomRequestRepository.findPendingRoomRequestsByUserAndRoom(user, room).orElseThrow(
                () -> new ResourceNotFoundException("RoomRequest", "User, Room", user.getId()+ " , " + room.getId()+"")
        );
        this.roomRequestRepository.delete(roomRequest);
    }

    @Override
    public RoomRequest getRoomRequestById(Long id) {
        RoomRequest roomRequest = this.roomRequestRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("RoomRequest", "id", id+"")
        );
        return roomRequest;
    }

    @Override
    public List<RoomRequest> getAllRooms(User user) {
        List<RoomRequest> roomRequests = this.roomRequestRepository.findByUser(user).get();
        return roomRequests;
    }

    @Override
    public RoomRequest findByUserAndRoom(User user, Room room) {
        RoomRequest roomRequest = this.roomRequestRepository.findPendingRoomRequestsByUserAndRoom(user,room).orElseThrow(
                () -> new ResourceNotFoundException("RoomRequest", "User, Room", user.getId()+ " , " + room.getId()+"")
        );
        return roomRequest;
    }

}
