package backend.projects.competitionApp.service.impl;

import backend.projects.competitionApp.entity.DailyProfit;
import backend.projects.competitionApp.entity.DataPlayer;
import backend.projects.competitionApp.entity.Room;
import backend.projects.competitionApp.entity.User;
import backend.projects.competitionApp.models.UpdateDailyProfitRequest;
import backend.projects.competitionApp.repository.DailyProfitRepository;
import backend.projects.competitionApp.service.DailyProfitService;
import backend.projects.competitionApp.service.DataPlayerService;
import backend.projects.competitionApp.service.RoomService;
import backend.projects.competitionApp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DailyProfitServiceImpl implements DailyProfitService {

    // Me pasan un DataPlayer que deberia de tener un account number y password
    // desde el cual podre llamar este metodo cada dia para ver cual es el profit del dia
    // y entocnes crear un DailyProfit para ese usuario y eso hacerlo para todos los usuarios
    // de salas activas.

    // Seria como hacer un proceso batch diario.

    // Faltaria
    // Crear estados de sala (activa, inactica, pendiente)
    // Añadir atributos accountNumber y password

    private RoomService roomService;

    private UserService userService;

    private DataPlayerService dataPlayerService;

    private DailyProfitRepository dailyProfitRepository;

    @Override
    @Transactional
    public DailyProfit updateUserDailyProfit(Long roomId, Long userId, UpdateDailyProfitRequest updateDailyProfitRequest) {
        Room room = this.roomService.getRoomById(roomId);
        User user = this.userService.getUserById(userId);
        DataPlayer dataPlayer = this.dataPlayerService.findDataPlayerByUserAndRoom(user, room);
        Optional<DailyProfit> existingOptionalDailyProfit = this.dailyProfitRepository.findDailyProfitByDataPlayerIdAndDate(updateDailyProfitRequest.getDate(), dataPlayer);
        //Existe el DailyProfit para esa fecha y dataplayer
        if (existingOptionalDailyProfit.isPresent()) {
            DailyProfit updatedDailyProfit = existingOptionalDailyProfit.get();
            //Existe el DailyProfit para esa fecha y dataplayer pero el profit que se ha enviado es diferente que el que existe
            if (!updateDailyProfitRequest.getProfit().equals(updatedDailyProfit.getProfit())) {
                this.dataPlayerService.updateEquity(dataPlayer, updateDailyProfitRequest.getProfit());
                updatedDailyProfit.setProfit(updateDailyProfitRequest.getProfit());
                return this.saveDailyProfit(updatedDailyProfit);
            }
            //Existe el DailyProfit para esa fecha y dataplayer pero el profit que se ha enviado es el mismo que el que existe.
            return updatedDailyProfit;
        }else {
            //No Existe el DailyProfit para esa fecha y dataplayer
            this.dataPlayerService.updateEquity(dataPlayer, updateDailyProfitRequest.getProfit());
            return this.saveDailyProfit(updateDailyProfitRequest.getDate(), updateDailyProfitRequest.getProfit(), dataPlayer);
        }
    }

    public DailyProfit saveDailyProfit(DailyProfit dailyProfit) {
        DailyProfit savedDailyProfit = this.dailyProfitRepository.save(dailyProfit);
        return savedDailyProfit;
    }

    public DailyProfit saveDailyProfit(LocalDate date, Long profit, DataPlayer dataPlayer) {
        DailyProfit dailyProfit = DailyProfit.builder()
                .date(date)
                .profit(profit)
                .dataPlayer(dataPlayer)
                .build();
        DailyProfit savedDailyProfit = this.dailyProfitRepository.save(dailyProfit);
        return savedDailyProfit;
    }
}
