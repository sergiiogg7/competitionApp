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
    public void deleteRoomById(Long id) {
        this.roomRepository.deleteById(id);
    }

    @Override
    public Room updateRoomById(Room room, Long id) {
        Room existingRoom = this.getRoomById(id);
        existingRoom.setName(room.getName());
        existingRoom.setDescription(room.getDescription());
        //TO DO
        //existingRoom.setOwner(room.getOwner());
        //TO DO
        //existingRoom.setPlayers(room.getPlayers());
        this.roomRepository.save(existingRoom);
        return existingRoom;
    }

    @Override
    @Transactional
    public void deletePlayerFromRoomByUserId(Long roomId, Long userId) {
        Room existingRoom = this.getRoomById(roomId);
        User existingUser = this.userService.getUserById(userId);
        DataPlayer dataPlayer = this.dataPlayerService.findDataPlayerByUserAndRoom(existingUser, existingRoom);
        //Comprobar que el room request este acceptado para poder borrar si no no
        this.roomRequestService.deleteAcceptedRoomRequest(dataPlayer);
        this.dataPlayerService.remove(dataPlayer);
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

        if (userAuthorized) {
            List<RoomRequest> roomRequests = this.roomRequestService.getAllRooms(user);
            boolean roomRequestExists = roomRequests.stream().anyMatch(roomRequest ->
                    roomRequest.getRequestingUser().equals(user) &&
                    roomRequest.getRequestingRoom().equals(room) &&
                    !roomRequest.getState().equals(RoomRequestState.DECLINED));

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
                DataPlayer dataPlayer = new DataPlayer(user, room);
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
                roomRequest.setState(RoomRequestState.DECLINED);
                this.roomRequestService.save(roomRequest);
            }

            return room;
        } else {
            throw new UnauthorizedActionException();
        }
    }
}
