package backend.projects.competitionApp.service.impl;

import backend.projects.competitionApp.entity.DataPlayer;
import backend.projects.competitionApp.entity.Room;
import backend.projects.competitionApp.entity.User;
import backend.projects.competitionApp.exception.ResourceNotFoundException;
import backend.projects.competitionApp.repository.DataPlayerRepository;
import backend.projects.competitionApp.service.DataPlayerService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DataPlayerServiceImpl implements DataPlayerService {

    private DataPlayerRepository dataPlayerRepository;
    @Override
    public DataPlayer save(DataPlayer dataPlayer) {
        DataPlayer savedDataPlayer = this.dataPlayerRepository.save(dataPlayer);
        return savedDataPlayer;
    }

    @Override
    public DataPlayer findDataPlayerByUserAndRoom(User user, Room room) {
        DataPlayer dataPlayer = this.dataPlayerRepository.findDataPlayerByUserAndRoom(user, room).orElseThrow(
                () -> new ResourceNotFoundException("DataPlayer", "User, Room", user.toString()+ " , " + room.toString())
        );
        return dataPlayer;
    }

    @Override
    public void remove(DataPlayer dataPlayer) {
        this.dataPlayerRepository.delete(dataPlayer);
    }
}
