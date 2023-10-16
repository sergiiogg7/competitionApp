package backend.projects.competitionApp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String description;
    @Column(nullable = false)
    private LocalDateTime startDate;
    @Column(nullable = false)
    private LocalDateTime finishedDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;
    @OneToMany(mappedBy = "room")
    private Set<DataPlayer> players;
    @OneToMany(mappedBy = "requestingRoom")
    private Set<RoomRequest> request;
}
