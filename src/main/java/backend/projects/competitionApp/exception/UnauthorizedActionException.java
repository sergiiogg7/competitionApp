package backend.projects.competitionApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class UnauthorizedActionException extends RuntimeException{
    public UnauthorizedActionException() {
        super(String.format("You do not have permission to perform this action."));
    }

}
