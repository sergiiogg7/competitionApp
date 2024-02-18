package backend.projects.competitionApp.service;

import backend.projects.competitionApp.entity.RoomRequest;
import backend.projects.competitionApp.entity.Trophy;

import java.util.List;
import java.util.Optional;

public interface TrophyService {

    Trophy create(Trophy trophy);
    Trophy save(Trophy trophy);
    List<Trophy> getTrophiesFromRoom(Long roomId);
    void deleteTrophyById(Long id);



}
