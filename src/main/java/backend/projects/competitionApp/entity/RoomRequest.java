package backend.projects.competitionApp.entity;

import backend.projects.competitionApp.enumeration.RoomRequestState;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RoomRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private RoomRequestState state;
    @ManyToOne
    @JsonBackReference(value = "user-roomRequests")
    @JoinColumn(name = "user_id")
    private User requestingUser;
    @ManyToOne
    @JsonBackReference(value = "room-requests")
    @JoinColumn(name = "room_id")
    private Room requestingRoom;
    public RoomRequest(RoomRequestState state, User requestingUser, Room requestingRoom) {
        this.state = state;
        this.requestingUser = requestingUser;
        this.requestingRoom = requestingRoom;
    }
}
