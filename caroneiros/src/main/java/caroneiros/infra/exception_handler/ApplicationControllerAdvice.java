package caroneiros.infra.exception_handler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import caroneiros.infra.exceptions.DontDriverException;
import caroneiros.infra.exceptions.InvalidOperationException;
import caroneiros.infra.exceptions.NoSeatsAvaliableException;
import caroneiros.infra.exceptions.NoVehiclesException;
import caroneiros.infra.exceptions.NotFoundException;
import caroneiros.infra.exceptions.VehicleNotOwnedException;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
@ControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(NoSeatsAvaliableException.class)
    public ResponseEntity<ApiError> handleNoSeatsAvaliableException(NoSeatsAvaliableException ex) {
        ApiError apiError = ApiError.builder()
                .timetamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .erro(ex.getMessage())
                .errors(List.of(ex.getMessage()))
                .path(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(NotFoundException ex) {
        ApiError apiError = ApiError.builder()
                .timetamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .erro(ex.getMessage())
                .errors(List.of(ex.getMessage()))
                .path(HttpStatus.NOT_FOUND.getReasonPhrase())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(DontDriverException.class)
    public ResponseEntity<ApiError> handleDontDriverException(DontDriverException ex) {
        ApiError apiError = ApiError.builder()
                .timetamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .erro(ex.getMessage())
                .errors(List.of(ex.getMessage()))
                .path(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(NoVehiclesException.class)
    public ResponseEntity<ApiError> handleNoVehiclesException(NoVehiclesException ex) {
        ApiError apiError = ApiError.builder()
                .timetamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .erro(ex.getMessage())
                .errors(List.of(ex.getMessage()))
                .path(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(VehicleNotOwnedException.class)
    public ResponseEntity<ApiError> handleVehicleNotOwnedException(VehicleNotOwnedException ex) {
        ApiError apiError = ApiError.builder()
                .timetamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .erro(ex.getMessage())
                .errors(List.of(ex.getMessage()))
                .path(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<ApiError> handleInvalidOperationException(InvalidOperationException ex) {
        ApiError apiError = ApiError.builder()
                .timetamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .erro(ex.getMessage())
                .errors(List.of(ex.getMessage()))
                .path(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();
        ApiError apiError = ApiError.builder()
                .timetamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .erro("Validation Error")
                .errors(errors)
                .path(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        List<String> errors = List.of("Data integrity violation: " + ex.getMostSpecificCause().getMessage());
        ApiError apiError = ApiError.builder()
                .timetamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .erro(ex.getMessage())
                .errors(errors)
                .path(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException ex) {

        ApiError apiError = ApiError.builder()
                .timetamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .erro(ex.getMessage())
                .errors(List.of(ex.getLocalizedMessage()))
                .path(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

}
