package group4.cuisineCanvas.exceptionsHandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(ValueCanNotBeNullException.class)
    public ResponseEntity<Object> handleNullValueException(ValueCanNotBeNullException e, WebRequest request) {
        var error = e.getMessage();
        return handleExceptionInternal(e, error, new HttpHeaders(),
                HttpStatusCode.valueOf(400), request);
    }

    @ExceptionHandler(ValueAlreadyExistsException.class)
    public ResponseEntity<Object> handleExistingValueException(ValueAlreadyExistsException e, WebRequest request) {
        var error = e.getMessage();
        return handleExceptionInternal(e, error, new HttpHeaders(),
                HttpStatusCode.valueOf(400), request);
    }

    @ExceptionHandler(NoAccessToThisFeatureException.class)
    public ResponseEntity<Object> handleNoAccessToThisFeatureException(NoAccessToThisFeatureException e, WebRequest request) {
        var error = e.getMessage();
        return handleExceptionInternal(e, error, new HttpHeaders(),
                HttpStatusCode.valueOf(400), request);
    }



}

