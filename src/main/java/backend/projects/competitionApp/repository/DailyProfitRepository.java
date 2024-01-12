package backend.projects.competitionApp.repository;

import backend.projects.competitionApp.entity.DailyProfit;
import backend.projects.competitionApp.entity.DataPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyProfitRepository extends JpaRepository<DailyProfit, Long> {

    @Query("SELECT dp FROM DailyProfit dp WHERE dp.date = :date AND dp.dataPlayer = :dataPlayer")
    Optional<DailyProfit> findDailyProfitByDataPlayerIdAndDate(@Param("date") LocalDate date,
                                                               @Param("dataPlayer") DataPlayer dataPlayer);

}
