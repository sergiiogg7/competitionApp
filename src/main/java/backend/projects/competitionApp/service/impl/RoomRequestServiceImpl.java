package backend.projects.competitionApp.service.impl;

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
        RoomRequest roomRequest = this.roomRequestRepository.findByUserAndRoom(user,room).orElseThrow(
                () -> new ResourceNotFoundException("RoomRequest", "User, Room", user.getId()+ " , " + room.getId()+"")
        );
        return roomRequest;
    }

}
