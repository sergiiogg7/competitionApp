package backend.projects.competitionApp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataPlayer {

    public DataPlayer(Long initialBalance, Long equity, User player, Room room) {
        this.player = player;
        this.room = room;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "initial_balance", nullable = false)
    private Long initialBalance;

    @Column(name = "equity", nullable = false)
    private Long equity;

    @ManyToOne
    @JsonBackReference(value = "user-dataPlayers")
    @JoinColumn(name = "user_id")
    private User player;

    @ManyToOne
    @JsonBackReference(value = "room-players")
    @JoinColumn(name = "room_id")
    private Room room;


    @OneToMany(mappedBy = "dataPlayer")
    @JsonManagedReference(value = "dataPlayer-profits")
    private Set<DailyProfit> profits;

    @JsonProperty("playerId")
    public Long getPlayerId() {
        return this.player.getId();
    }
    @JsonProperty("playerName")
    public String getPlayerName() {
        return this.player.getEmail();
    }

    @JsonProperty("roomId")
    public Long getRoomId() {
        return this.room.getId();
    }

    @JsonProperty("roomName")
    public String getRoomName() {
        return this.room.getName();
    }

}
