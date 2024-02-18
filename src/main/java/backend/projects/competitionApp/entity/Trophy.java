package backend.projects.competitionApp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trophy {

    public Trophy(String trophy, Room room) {
        this.trophy = trophy;
        this.room = room;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    private String trophy;

    @ManyToOne
    @JsonBackReference(value = "room-trophies")
    @JoinColumn(name = "roomId")
    private Room room;
}
