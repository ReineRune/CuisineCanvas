package group4.cuisineCanvas.exceptionsHandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleArgumentNotValidException(MethodArgumentNotValidException e) {

        if (e.getBindingResult().getFieldError() != null) {
            String message = "Invalid! " + e.getBindingResult().getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(message);


        }
        return ResponseEntity.badRequest().body("ERROR");
    }


}
