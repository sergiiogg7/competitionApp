package backend.projects.competitionApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DatesException extends RuntimeException {
    private String exceptionMessage;
    public DatesException(String message) {
        super(String.format(message));
    }
}

