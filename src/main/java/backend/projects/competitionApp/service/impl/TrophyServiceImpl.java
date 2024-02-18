package backend.projects.competitionApp.service.impl;

import backend.projects.competitionApp.entity.Trophy;
import backend.projects.competitionApp.exception.ResourceNotFoundException;
import backend.projects.competitionApp.repository.RoomRepository;
import backend.projects.competitionApp.repository.TrophyRepository;
import backend.projects.competitionApp.service.TrophyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TrophyServiceImpl implements TrophyService {

    private TrophyRepository trophyRepository;

    @Override
    public Trophy create(Trophy trophy) {
        return save(trophy);
    }

    @Override
    public Trophy save(Trophy trophy) {
        return trophyRepository.save(trophy);
    }

    @Override
    public List<Trophy> getTrophiesFromRoom(Long roomId) {
        List<Trophy> trophies = this.trophyRepository.findAllByRoomId(roomId);
        return trophies;
    }

    @Override
    public void deleteTrophyById(Long id) {
        this.trophyRepository.deleteById(id);
    }
}
