package backend.projects.competitionApp.repository;

import backend.projects.competitionApp.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;


//Autocreacion de BD embedida
//Cargas las clases anotadas con @Entity y @Repository
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setupTestData(){
        // Given : Setup object or precondition
        user = User.builder()
                .email("user1@gmail.com")
                .password("123456")
                .build();
    }

    @Test
    @DisplayName("JUnit Test Create User")
    public void givenUser_whenSave_thenReturnSavedUser() {
        User userSaved = this.userRepository.save(user);
        Assertions.assertThat(userSaved).isNotNull();
        Assertions.assertThat(userSaved.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("JUnit Test FindAllUsers")
    public void givenUsersList_whenFindAll_thenReturnUsersList() {
        User user2 = User.builder()
                .email("user2@gmail.com")
                .password("123456")
                .build();
        User user3 = User.builder()
                .email("user3@gmail.com")
                .password("123456")
                .build();

        User userSaved1 = this.userRepository.save(user);
        User userSaved2 = this.userRepository.save(user2);
        User userSaved3 = this.userRepository.save(user3);
        List<User> usersList = this.userRepository.findAll();
        Assertions.assertThat(usersList).isNotNull();
        Assertions.assertThat(usersList.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("JUnit Test FindUserById")
    public void givenUserObject_whenFindById_thenReturnUserObject() {
        this.userRepository.save(user);
        User userDB = this.userRepository.findById(user.getId()).get();
        Assertions.assertThat(userDB).isNotNull();
        Assertions.assertThat(userDB.getId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("JUnit Test Update User")
    public void givenUserObject_whenUpdateUser_thenReturnUpdatedUser() {
        this.userRepository.save(user);
        User savedUser = this.userRepository.findById(user.getId()).get();
        savedUser.setEmail("updated@gmail.com");
        User updatedUser = this.userRepository.save(savedUser);
        Assertions.assertThat(updatedUser.getEmail()).isEqualTo("updated@gmail.com");
    }

    @Test
    @DisplayName("JUnit Test Delete User")
    public void givenUserObject_whenDeleteUser_thenReturnDeletedUser() {
        this.userRepository.save(user);
        this.userRepository.delete(user);
        Assertions.assertThat(this.userRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("JUnit Test Delete User")
    public void givenUserObject_whenFindByEmail_thenReturnUserObject() {
        this.userRepository.save(user);
        User findedUser = this.userRepository.findByEmail(user.getEmail()).get();
        Assertions.assertThat(user).isEqualTo(findedUser);
    }

}
