package backend.projects.competitionApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    @JsonManagedReference(value = "user-authorities")
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Authority> authorities = new HashSet<>();

    @JsonManagedReference(value = "user-dataPlayers")
    @OneToMany(mappedBy = "player")
    private Set<DataPlayer> dataPlayers;

    @JsonManagedReference(value = "user-ownedRooms")
    @OneToMany(mappedBy = "owner")
    private Set<Room> ownedRooms;

    @JsonManagedReference(value = "user-roomRequests")
    @OneToMany(mappedBy = "requestingUser")
    private Set<RoomRequest> roomRequests;

}
