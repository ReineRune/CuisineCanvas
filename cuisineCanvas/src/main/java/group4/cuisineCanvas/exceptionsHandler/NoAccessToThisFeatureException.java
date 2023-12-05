package group4.cuisineCanvas.exceptionsHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NoAccessToThisFeatureException extends RuntimeException {

    public NoAccessToThisFeatureException(String message) {
        super(message);
    }
}
