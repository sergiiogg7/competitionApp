package backend.projects.competitionApp.service.impl;

import backend.projects.competitionApp.entity.*;
import backend.projects.competitionApp.enumeration.RoomRequestState;
import backend.projects.competitionApp.exception.DatesException;
import backend.projects.competitionApp.exception.ResourceNotFoundException;
import backend.projects.competitionApp.exception.RoomRequestAlreadyExistsException;
import backend.projects.competitionApp.exception.UnauthorizedActionException;
import backend.projects.competitionApp.repository.RoomRepository;
import backend.projects.competitionApp.repository.TrophyRepository;
import backend.projects.competitionApp.service.*;
import lombok.AllArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {

    private TrophyService trophyService;

    private UserService userService;

    private DataPlayerService dataPlayerService;

    private RoomRepository roomRepository;

    private RoomRequestService roomRequestService;

    @Override
    @Transactional
    public Room createRoom(Room room) {
        LocalDate initDate = room.getInitialDate();
        LocalDate endDate = room.getEndDate();
        LocalDate now = LocalDate.now();
        if (initDate.isAfter(endDate)) {
            throw new DatesException("InitialDate cannot be after than EndDate");
        } else if (initDate.isEqual(endDate)) {
            throw new DatesException("InitialDate cannot be equal than EndDate");
        } else if (LocalDate.now().isAfter(initDate)) {
            throw new DatesException("InitialDate cannot be before today.");
        }else {
            Room savedRoom = this.roomRepository.save(room);

            // Crea los trofeos dentro de una transacción
            List<Trophy> trophies = new ArrayList<>();
            for (Trophy trophy : room.getTrophies()) {
                Trophy createdTrophy = trophyService.create(new Trophy(trophy.getTrophy(), savedRoom));
                trophies.add(createdTrophy);
            }

            savedRoom.setTrophies(new HashSet<>(trophies)); // Asigna los trofeos a la sala
            return savedRoom;
        }
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
    @Transactional
    public RoomRequest createRoomRequest(Long userId, Long roomId) {
        User user = this.userService.getUserById(userId);
        Room room = this.getRoomById(roomId);
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
            // Set a default value for equity, you might want to adjust this based on your application logic
            Long defaultEquityValue = 0L;
            DataPlayer dataPlayer = new DataPlayer(Long.MAX_VALUE, defaultEquityValue, user, room);
            this.dataPlayerService.save(dataPlayer);
        }

        return room;
    }

    @Override
    @Transactional
    public Room revokeAccess(Long roomId, Long userId) {
        Room room = this.getRoomById(roomId);
        User user = this.userService.getUserById(userId);
        RoomRequest roomRequest = this.roomRequestService.findByUserAndRoom(user, room);

        if (roomRequest.getState() == RoomRequestState.PENDING) {
            roomRequest.setState(RoomRequestState.DECLINED);
            this.roomRequestService.save(roomRequest);
        }

        return room;
    }
}
