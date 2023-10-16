package backend.projects.competitionApp.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Entity
public class DataPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private List<Double> profits;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User player;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

}
