package backend.projects.competitionApp.repository;


import backend.projects.competitionApp.entity.RoomRequest;
import backend.projects.competitionApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    @Query("SELECT rr FROM RoomRequest rr WHERE rr.requestingRoom.owner.id = :userId AND rr.state = 0")
    List<RoomRequest> getAllRequestsToUserRooms(Long userId);

}
