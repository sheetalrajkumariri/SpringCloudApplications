package review.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String,Object>> handleNotFoundException(NotFoundException e){
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDate.now());
        errorResponse.put("message", e.getMessage());
        errorResponse.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        errorResponse.put("status", HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String,Object>>  handleBadRequestException(BadRequestException e){
        Map<String,Object> errorResponse = new HashMap<>();
        errorResponse.put("timeStamp", LocalDate.now());
        errorResponse.put("message", e.getMessage());
        errorResponse.put("error", "Very bed request");
        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }
}
