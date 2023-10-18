package backend.projects.competitionApp.repository;


import backend.projects.competitionApp.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
