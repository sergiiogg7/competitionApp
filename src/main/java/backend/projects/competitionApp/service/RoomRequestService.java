package backend.projects.competitionApp.service;

import backend.projects.competitionApp.entity.Room;
import backend.projects.competitionApp.entity.RoomRequest;
import backend.projects.competitionApp.entity.User;
import backend.projects.competitionApp.enumeration.RoomRequestState;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface RoomRequestService {

    RoomRequest save(RoomRequest roomRequest);
    RoomRequest getRoomRequestById(Long id);
    List<RoomRequest> getAllRooms(User user);
    RoomRequest findByUserAndRoom(User user, Room room);

}
