package backend.projects.competitionApp.service;

import backend.projects.competitionApp.entity.User;

import java.util.Optional;

public interface UserService {
    User getUserByEmail(String email);
}
