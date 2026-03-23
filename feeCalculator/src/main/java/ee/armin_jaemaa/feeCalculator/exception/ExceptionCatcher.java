package ee.armin_jaemaa.feeCalculator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionCatcher {

    @ExceptionHandler(VehicleForbiddenException.class)
    public ResponseEntity<ErrorMessage> handleVehicleForbiddenException(VehicleForbiddenException ex) {
        ErrorMessage error = new ErrorMessage(
                ex.getMessage(),
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    //TODO: add excpetionhandler for baseFeeNotFound and illegalArgumentException

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception ex) {
        System.out.println(ex.getMessage());
        ErrorMessage error = new ErrorMessage(
                "internal error",
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
