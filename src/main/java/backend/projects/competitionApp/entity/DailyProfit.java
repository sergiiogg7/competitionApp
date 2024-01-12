package backend.projects.competitionApp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"date", "dataPlayer_id"}))
public class DailyProfit {

    public DailyProfit(LocalDate date, Long profit, DataPlayer dataPlayer) {
        this.date = date;
        this.profit = profit;
        this.dataPlayer = dataPlayer;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Long profit;

    @ManyToOne
    @JsonBackReference(value = "dataPlayer-profits")
    @JoinColumn(name = "dataPlayer_id")
    private DataPlayer dataPlayer;

}
