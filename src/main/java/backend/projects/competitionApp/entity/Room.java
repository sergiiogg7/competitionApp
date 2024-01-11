package backend.projects.competitionApp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;

    @ManyToOne
    @NotNull(message = "Owner is required")
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
