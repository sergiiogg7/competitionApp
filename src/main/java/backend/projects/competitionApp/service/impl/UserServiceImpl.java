package backend.projects.competitionApp.service.impl;

import backend.projects.competitionApp.entity.Authority;
import backend.projects.competitionApp.entity.Room;
import backend.projects.competitionApp.entity.RoomRequest;
import backend.projects.competitionApp.entity.User;
import backend.projects.competitionApp.enumeration.RoomRequestState;
import backend.projects.competitionApp.exception.EmailAlreadyExistsException;
import backend.projects.competitionApp.exception.ResourceNotFoundException;
import backend.projects.competitionApp.exception.UnauthorizedActionException;
import backend.projects.competitionApp.repository.AuthorityRepository;
import backend.projects.competitionApp.repository.RoomRepository;
import backend.projects.competitionApp.repository.RoomRequestRepository;
import backend.projects.competitionApp.repository.UserRepository;
import backend.projects.competitionApp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private RoomRepository roomRepository;

    private AuthorityRepository authorityRepository;

    private RoomRequestRepository roomRequestRepository;

    @Override
    public User getUserById(Long id) {
        return this.userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id+"")
        );
    }

    @Override
    @Transactional
    public User save(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        User existingUser = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User", "email", email)
        );
        return existingUser;
    }

    @Override
    @Transactional
    public User createUser(User user) {
        Optional<User> existingUser = this.userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists for users");
        }
        User createdUser = this.userRepository.save(user);
        Authority userAuthority = new Authority("ROLE_USER", user);
        this.authorityRepository.save(userAuthority);
        return createdUser;
    }

    @Override
    @Transactional
    public User updateUserById(User user, Long id) {
        User existingUser = this.getUserById(id);
        existingUser.setName(user.getName());
        existingUser.setSurnames(user.getSurnames());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        this.userRepository.save(existingUser);

        return existingUser;
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        this.userRepository.deleteById(id);
    }


    @Override
    public List<RoomRequest> getAllRequestsToUserRooms(Long userId) {
        boolean userAuthorized = false;
        User user = this.getUserById(userId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            if (username.equals(user.getEmail())) { userAuthorized = true; }
        }

        if (userAuthorized) {
            List<RoomRequest> roomRequests = this.userRepository.getAllRequestsToUserRooms(userId);
            return roomRequests;
        } else{
            throw new UnauthorizedActionException();
        }
    }


}
