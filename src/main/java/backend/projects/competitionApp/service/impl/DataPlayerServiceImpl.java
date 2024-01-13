package backend.projects.competitionApp.service.impl;

import backend.projects.competitionApp.entity.DailyProfit;
import backend.projects.competitionApp.entity.DataPlayer;
import backend.projects.competitionApp.entity.Room;
import backend.projects.competitionApp.entity.User;
import backend.projects.competitionApp.exception.ResourceNotFoundException;
import backend.projects.competitionApp.repository.DataPlayerRepository;
import backend.projects.competitionApp.service.DataPlayerService;
import backend.projects.competitionApp.service.RoomRequestService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class DataPlayerServiceImpl implements DataPlayerService {

    private DataPlayerRepository dataPlayerRepository;

    private RoomRequestService roomRequestService;

    @Override
    public DataPlayer save(DataPlayer dataPlayer) {
        DataPlayer savedDataPlayer = this.dataPlayerRepository.save(dataPlayer);
        return savedDataPlayer;
    }

    @Override
    public DataPlayer updateEquity(DataPlayer dataPlayer, Long profit) {
        Set<DailyProfit> profits = dataPlayer.getProfits();
        Long equity = dataPlayer.getInitialBalance() + profit + profits.stream()
                .mapToLong(DailyProfit::getProfit)
                .sum();
        dataPlayer.setEquity(equity);
        return this.dataPlayerRepository.save(dataPlayer);
    }

    @Override
    public DataPlayer findDataPlayerByUserAndRoom(User user, Room room) {
        DataPlayer dataPlayer = this.dataPlayerRepository.findDataPlayerByUserAndRoom(user, room).orElseThrow(
                () -> new ResourceNotFoundException("DataPlayer", "User, Room", user.getName()+ " , " + room.getName())
        );
        return dataPlayer;
    }

    @Override
    public void remove(DataPlayer dataPlayer) {
        this.dataPlayerRepository.findById(dataPlayer.getId()).orElseThrow(
                () -> new ResourceNotFoundException("DataPlayer", "id", dataPlayer.getId()+"")
        );

        this.dataPlayerRepository.delete(dataPlayer);
    }
}
