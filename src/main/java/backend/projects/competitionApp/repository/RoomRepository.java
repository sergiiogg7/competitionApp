package backend.projects.competitionApp.repository;


import backend.projects.competitionApp.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query(value = "SELECT * FROM room r WHERE " +
            "r.name LIKE CONCAT('%', :query,  '%') " +
            "Or r.description LIKE CONCAT('%', :query,  '%')", nativeQuery = true)
    List<Room> searchRoomsSQL(String query);

    @Query("SELECT r FROM Room r WHERE " +
            "r.name LIKE CONCAT('%', :query, '%') " +
            "OR r.description LIKE CONCAT('%', :query, '%')")
    List<Room> searchRoomsJPQL(@Param("query") String query);

}
