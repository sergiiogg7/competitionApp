package backend.projects.competitionApp.service.impl;

import backend.projects.competitionApp.entity.Authority;
import backend.projects.competitionApp.entity.User;
import backend.projects.competitionApp.exception.EmailAlreadyExistsException;
import backend.projects.competitionApp.exception.ResourceNotFoundException;
import backend.projects.competitionApp.repository.AuthorityRepository;
import backend.projects.competitionApp.repository.UserRepository;
import backend.projects.competitionApp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private AuthorityRepository authorityRepository;

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

}
