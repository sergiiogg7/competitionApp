package backend.projects.competitionApp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User owner;
    @OneToMany(mappedBy = "room")
    private Set<DataPlayer> players;
    @OneToMany(mappedBy = "requestingRoom")
    private Set<RoomRequest> requests;
}
