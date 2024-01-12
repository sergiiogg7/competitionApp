package backend.projects.competitionApp.service;

import backend.projects.competitionApp.entity.DailyProfit;
import backend.projects.competitionApp.entity.DataPlayer;
import backend.projects.competitionApp.models.UpdateDailyProfitRequest;

import java.time.LocalDate;

public interface DailyProfitService {
    DailyProfit saveDailyProfit(DailyProfit dailyProfit);
    DailyProfit saveDailyProfit(LocalDate date, Long profit, DataPlayer dataPlayer);
    DailyProfit updateUserDailyProfit(Long roomId, Long userId, UpdateDailyProfitRequest updateDailyProfitRequest);
}
