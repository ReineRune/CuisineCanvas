package group4.cuisineCanvas.exceptionsHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ValueCanNotBeNullException extends RuntimeException {
    public ValueCanNotBeNullException(String message) {
        super(message);
    }
}
