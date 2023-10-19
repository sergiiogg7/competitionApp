package backend.projects.competitionApp.service;

import backend.projects.competitionApp.entity.DataPlayer;
import backend.projects.competitionApp.entity.Room;
import backend.projects.competitionApp.entity.User;

import java.util.Optional;

public interface DataPlayerService {

    DataPlayer save(DataPlayer dataPlayer);
    DataPlayer findDataPlayerByUserAndRoom(User user, Room room);
}
