package backend.projects.competitionApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class RoomRequestAlreadyExistsException extends  RuntimeException {

    private String resourceName;
    private String userFieldName;
    private String userFieldValue;
    private String roomFieldName;
    private String roomFieldValue;

    public RoomRequestAlreadyExistsException(String resourceName, String userFieldName, String userFieldValue, String roomFieldName, String roomFieldValue) {
        super(String.format("%s already exists with %s, %s: %s, %s",resourceName,userFieldName,roomFieldName,userFieldValue,roomFieldValue));
        this.resourceName = resourceName;
        this.roomFieldName = roomFieldName;
        this.roomFieldValue = roomFieldValue;
        this.userFieldName = userFieldName;
        this.userFieldValue = userFieldValue;
    }
}
