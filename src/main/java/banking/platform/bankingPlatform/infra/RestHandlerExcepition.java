package banking.platform.bankingPlatform.infra;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestHandlerExcepition {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> methodArgumentNotValid (MethodArgumentNotValidException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("invalid email");
    }

}
