package backend.projects.competitionApp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataPlayer {

    public DataPlayer(List<Double> profits, User player, Room room) {
        this.profits = profits;
        this.player = player;
        this.room = room;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private List<Double> profits;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User player;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "room_id")
    private Room room;

}
