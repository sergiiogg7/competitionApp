package backend.projects.competitionApp.service.impl;

import backend.projects.competitionApp.entity.DataPlayer;
import backend.projects.competitionApp.entity.Room;
import backend.projects.competitionApp.entity.RoomRequest;
import backend.projects.competitionApp.entity.User;
import backend.projects.competitionApp.enumeration.RoomRequestState;
import backend.projects.competitionApp.exception.ResourceNotFoundException;
import backend.projects.competitionApp.exception.RoomRequestAlreadyExistsException;
import backend.projects.competitionApp.exception.UnauthorizedActionException;
import backend.projects.competitionApp.repository.RoomRepository;
import backend.projects.competitionApp.service.DataPlayerService;
import backend.projects.competitionApp.service.RoomRequestService;
import backend.projects.competitionApp.service.RoomService;
import backend.projects.competitionApp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        boolean userAuthorized = false;
        User user = this.userService.getUserById(userId);
        Room room = this.roomRepository.findById(roomId).orElseThrow( () -> new ResourceNotFoundException("Room", "id", roomId+""));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            if (username.equals(user.getEmail())) { userAuthorized = true; }
        }

        //Si el usuario que hace la peticion es el mismo que el que solicita hacer la roomrequest
        if (userAuthorized) {
            //Buscar que no exista una room request con el mismo roomId y UserId
            List<RoomRequest> roomRequests = this.roomRequestService.getAllRooms(user);
            boolean roomRequestExists = roomRequests.stream().anyMatch(roomRequest -> roomRequest.getRequestingUser().equals(user) &&
                    roomRequest.getRequestingRoom().equals(room));
            if (roomRequestExists) {
                throw new RoomRequestAlreadyExistsException("RoomRequest", "userId", userId+"", "roomId", roomId+"");
            }

            RoomRequest savedRoomRequest = this.roomRequestService.save(new RoomRequest(RoomRequestState.PENDING, user, room));
            return savedRoomRequest;
        } else {
            throw new UnauthorizedActionException();
        }

    }

    @Override
    @Transactional
    public Room grantAccessToUser(Long roomId, Long userId) {
        boolean userAuthorized = false;

        Room room = this.getRoomById(roomId);
        User user = this.userService.getUserById(userId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            if (username.equals(room.getOwner().getEmail())) { userAuthorized = true; }
        }

        if (userAuthorized) {
            RoomRequest roomRequest = this.roomRequestService.findByUserAndRoom(user, room);
            if (roomRequest.getState() == RoomRequestState.PENDING) {
                roomRequest.setState(RoomRequestState.ACCEPTED);
                this.roomRequestService.save(roomRequest);
                List<Double> profits = new ArrayList<>();
                DataPlayer dataPlayer = new DataPlayer(profits, user, room);
                this.dataPlayerService.save(dataPlayer);
            }

            return room;
        }else {
            throw new UnauthorizedActionException();
        }

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
