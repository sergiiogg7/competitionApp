package backend.projects.competitionApp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @ManyToOne
    @JsonBackReference(value = "user-ownedRooms")
    @JoinColumn(name = "user_id")
    private User owner;
    @JsonManagedReference(value = "room-players")
    @OneToMany(mappedBy = "room")
    private Set<DataPlayer> players;
    @JsonManagedReference(value = "room-requests")
    @OneToMany(mappedBy = "requestingRoom")
    private Set<RoomRequest> requests;
}
