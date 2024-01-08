package backend.projects.competitionApp.repository;

import backend.projects.competitionApp.entity.Room;
import backend.projects.competitionApp.entity.RoomRequest;
import backend.projects.competitionApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomRequestRepository extends JpaRepository<RoomRequest, Long> {

    @Query("SELECT rr FROM RoomRequest rr " +
            "WHERE rr.requestingUser = :user AND rr.requestingRoom = :room AND rr.state = 0")
    Optional<RoomRequest> findByUserAndRoom(@Param("user") User user, @Param("room") Room room);

    @Query("SELECT rr FROM RoomRequest rr " +
            "WHERE rr.requestingUser = :user")
    Optional<List<RoomRequest>> findByUser(@Param("user") User user);

}
