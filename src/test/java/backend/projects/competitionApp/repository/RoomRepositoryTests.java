package backend.projects.competitionApp.repository;

import backend.projects.competitionApp.entity.Room;
import backend.projects.competitionApp.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoomRepositoryTests {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    private Room room;

    private User user;

    @BeforeEach
    public void setUpTestData() {
        user = User.builder()
                .email("user@gmail.com")
                .password("123456")
                .build();

        this.userRepository.save(user);

        room = Room.builder()
                .name("Test1Room")
                .description("Room for testing")
                .owner(user)
                .build();
    }

    @Test
    @DisplayName("JUnit Test Create Room")
    public void givenRoomObject_whenSave_thenReturnSavedRoomObject() {
        Room savedRoom = this.roomRepository.save(room);

        Assertions.assertThat(savedRoom.getId()).isGreaterThan(0);
        Assertions.assertThat(savedRoom).isNotNull();
    }

    @Test
    @DisplayName("JUnit Test FindAll Room")
    public void givenRoomList_whenFindAll_thenReturnRoomList() {
        Room room2 = Room.builder()
                .name("Test2Room")
                .description("Room for testing")
                .owner(user)
                .build();

        Room room3 = Room.builder()
                .name("Test3Room")
                .description("Room for testing")
                .owner(user)
                .build();

        this.roomRepository.save(room);
        this.roomRepository.save(room2);
        this.roomRepository.save(room3);

        List<Room> rooms = this.roomRepository.findAll();

        Assertions.assertThat(rooms).isNotNull();
        Assertions.assertThat(rooms.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("JUnit Test FindById Room")
    public void givenRoomObject_whenFindById_thenReturnRoomObject() {
        this.roomRepository.save(room);

        Room room = this.roomRepository.findById(this.room.getId()).get();

        Assertions.assertThat(room).isNotNull();
        Assertions.assertThat(room.getId()).isEqualTo(room.getId());
    }

    @Test
    @DisplayName("JUnit Test Update Room")
    public void givenUserObject_whenUpdateRoom_thenReturnUpdatedRoom() {
        User updatedUser = User.builder()
                .email("updatedUser@gmail.com")
                .password("123456")
                .build();

        this.roomRepository.save(room);
        Room savedRoom = this.roomRepository.findById(room.getId()).get();
        savedRoom.setName("UpdatedRoom");
        savedRoom.setDescription("Updated Room Description");
        savedRoom.setOwner(updatedUser);
        Room updatedRoom = this.roomRepository.save(savedRoom);

        Assertions.assertThat(updatedRoom.getName()).isEqualTo("UpdatedRoom");
        Assertions.assertThat(updatedRoom.getDescription()).isEqualTo("Updated Room Description");
        Assertions.assertThat(updatedRoom.getOwner()).isEqualTo(updatedUser);
    }

    @Test
    @DisplayName("JUnit Test Delete Room")
    public void givenRoomObject_whenDeleteRoom_thenReturnDeletedRoom() {
        this.roomRepository.save(room);
        this.roomRepository.delete(room);
        Assertions.assertThat(this.roomRepository.findAll().size()).isEqualTo(0);
    }

}
