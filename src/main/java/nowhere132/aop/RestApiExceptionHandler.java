package nowhere132.aop;

import nowhere132.exceptions.ApiError;
import nowhere132.exceptions.HoldException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getDefaultMessage()); // I typically include the field name in the error message.
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        var apiError = new ApiError(HttpStatus.BAD_REQUEST, "-1", "Invalid arguments", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(HoldException.class)
    public ResponseEntity<Object> handleHoldException(HoldException ex) {
        var apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getCode(), ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }
}
