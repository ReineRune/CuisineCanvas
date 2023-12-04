package group4.cuisineCanvas.exceptionsHandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    /*@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleArgumentNotValidException(MethodArgumentNotValidException e) {

        if (e.getBindingResult().getFieldError() != null) {
            String message = "Invalid! " + e.getBindingResult().getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(message);


        }
        return ResponseEntity.badRequest().body("ERROR");
    }*/
    @ExceptionHandler(ValueCanNotBeNullException.class)
    public ResponseEntity<Object> handleNullValueException(ValueCanNotBeNullException e, WebRequest request){
        var error= e.getMessage();
        return handleExceptionInternal(e, error, new HttpHeaders(),
                HttpStatusCode.valueOf(400), request);
    }

    @ExceptionHandler(ValueAlreadyExistsException.class)
    public ResponseEntity<Object> handleExistingValueException(ValueCanNotBeNullException e, WebRequest request){
        var error= e.getMessage();
        return handleExceptionInternal(e, error, new HttpHeaders(),
                HttpStatusCode.valueOf(400), request);
    }

    }

