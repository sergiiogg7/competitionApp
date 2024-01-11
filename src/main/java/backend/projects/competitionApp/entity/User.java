package backend.projects.competitionApp.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "Name is required")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Surnames are required")
    private String surnames;

    @Column(nullable = false)
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Password is required")
    @Size(min=6, message = "Password must be at least 6 characters long")
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
