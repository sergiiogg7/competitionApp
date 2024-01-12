package backend.projects.competitionApp.controller;

import backend.projects.competitionApp.entity.DailyProfit;
import backend.projects.competitionApp.entity.DataPlayer;
import backend.projects.competitionApp.entity.Room;
import backend.projects.competitionApp.entity.User;
import backend.projects.competitionApp.exception.UnauthorizedActionException;
import backend.projects.competitionApp.models.UpdateDailyProfitRequest;
import backend.projects.competitionApp.service.DailyProfitService;
import backend.projects.competitionApp.service.DataPlayerService;
import backend.projects.competitionApp.service.RoomService;
import backend.projects.competitionApp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/dailyProfit")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "DailyProfit", description = "the DailyProfit API")
public class DailyProfitController {

    private DailyProfitService dailyProfitService;

    private RoomService roomService;

    private UserService userService;

    private DataPlayerService dataPlayerService;

    @PostMapping("/room/{room_id}/user/{user_id}/profits")
    @Operation(summary = "Update daily profits from a user in a room")
    public ResponseEntity<DailyProfit> updateUserDailyProfit(@PathVariable("room_id") Long roomId,
                                                             @PathVariable("user_id") Long userId,
                                                             @RequestBody UpdateDailyProfitRequest updateDailyProfitRequest) {
        boolean userAuthorized = false;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = this.userService.getUserById(userId);
            if (user.getEmail().equals(username)) {
                userAuthorized = true;
            }
        }

        if (userAuthorized) {
            DailyProfit dailyProfit = this.dailyProfitService.updateUserDailyProfit(roomId, userId, updateDailyProfitRequest);
            return new ResponseEntity<>(dailyProfit, HttpStatus.OK);
        } else {
            throw new UnauthorizedActionException();
        }
    }

}
