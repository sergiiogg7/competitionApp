package backend.projects.competitionApp.repository;

import backend.projects.competitionApp.entity.Trophy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TrophyRepository extends JpaRepository<Trophy, Long> {

    @Query("SELECT t FROM Trophy t WHERE t.room.id = :roomId")
    List<Trophy> findAllByRoomId(@Param("roomId") Long roomId);

}
