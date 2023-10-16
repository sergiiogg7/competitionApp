package backend.projects.competitionApp.service.impl;

import backend.projects.competitionApp.entity.User;
import backend.projects.competitionApp.exception.ResourceNotFoundException;
import backend.projects.competitionApp.repository.UserRepository;
import backend.projects.competitionApp.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public User getUserByEmail(String email) {
        User existingUser = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User", "email", email)
        );

        return existingUser;
    }
}
