package co.istad.itpmongdb.exeption;

import co.istad.itpmongdb.base.BaseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.databind.json.JsonMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestControllerAdvice
public class RestException {


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ResponseStatusException.class)
    public BaseError<?> handleResponseStatusException(ResponseStatusException e){
        return BaseError.builder()
                .status(false)
                .code(e.getStatusCode().value())
                .message("Something went wrong!, please try again later")
                .timestamp(LocalDateTime.now())
                .error(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseError<?> handleValidationException(MethodArgumentNotValidException e) {

        List<Map<String, String>> errors = new ArrayList<>();

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            Map<String, String> errorDetail = new HashMap<>();
            errorDetail.put("name", fieldError.getField());
            errorDetail.put("message", fieldError.getDefaultMessage());
            errors.add(errorDetail);
        }

        return BaseError.builder()
                .status(false)
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Validation error, please check the fields!")
                .timestamp(LocalDateTime.now())
                .error(errors)
                .build();
    }

    //    @ExceptionHandler(ResponseStatusException.class)
//    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException e){
//        Map<String, Object> error = new HashMap<>();
//        error.put("status", e.getStatusCode().value());
//        error.put("message", e.getReason());
//        error.put("timestamp", System.currentTimeMillis());
//        return ResponseEntity.status(e.getStatusCode()).body(error);
//    }
}
