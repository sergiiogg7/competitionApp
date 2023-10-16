package backend.projects.competitionApp.entity;

import backend.projects.competitionApp.enumeration.RoomRequestState;
import jakarta.persistence.*;

@Entity
public class RoomRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private RoomRequestState state;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User requestingUser;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room requestingRoom;
}
