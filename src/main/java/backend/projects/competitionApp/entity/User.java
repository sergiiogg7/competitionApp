package backend.projects.competitionApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Authority> authorities;
    @OneToMany(mappedBy = "player")
    private Set<DataPlayer> dataPlayers;
    @OneToMany(mappedBy = "owner")
    private Set<Room> ownedRooms;
    @OneToMany(mappedBy = "requestingUser")
    private Set<RoomRequest> roomRequests;

}
