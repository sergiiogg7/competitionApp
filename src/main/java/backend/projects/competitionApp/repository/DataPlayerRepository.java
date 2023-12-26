package backend.projects.competitionApp.repository;

import backend.projects.competitionApp.entity.DataPlayer;
import backend.projects.competitionApp.entity.Room;
import backend.projects.competitionApp.entity.RoomRequest;
import backend.projects.competitionApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DataPlayerRepository extends JpaRepository<DataPlayer, Long> {
    @Query("SELECT dp FROM DataPlayer dp " +
            "WHERE dp.player = :user AND dp.room = :room")
    Optional<DataPlayer> findDataPlayerByUserAndRoom(@Param("user") User user, @Param("room") Room room);
}
