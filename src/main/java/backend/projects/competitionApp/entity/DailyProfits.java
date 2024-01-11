package backend.projects.competitionApp.entity;

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
@AllArgsConstructor
public class DailyProfits {

    public DailyProfits(Integer day, Double profit, DataPlayer dataPlayer) {
        this.day = day;
        this.profit = profit;
        this.dataPlayer = dataPlayer;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer day;

    @Column(nullable = false)
    private Double profit;

    @ManyToOne
    @JsonBackReference(value = "dataPlayer-profits")
    @JoinColumn(name = "dataPlayer_id")
    private DataPlayer dataPlayer;

}
