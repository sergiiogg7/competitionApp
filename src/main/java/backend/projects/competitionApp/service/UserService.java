package backend.projects.competitionApp.service;

import backend.projects.competitionApp.entity.Authority;
import backend.projects.competitionApp.entity.RoomRequest;
import backend.projects.competitionApp.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User getUserById(Long id);
    User save(User user);
    User getUserByEmail(String email);
    User createUser(User user);
    User updateUserById(User user, Long id);
    void deleteUserById(Long id);
    List<RoomRequest> getAllRequestsToUserRooms(Long userId);
}
