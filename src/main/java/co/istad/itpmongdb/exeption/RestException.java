package co.istad.itpmongdb.exeption;

import co.istad.itpmongdb.base.BaseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.databind.json.JsonMapper;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class RestException {

    //    @ExceptionHandler(ResponseStatusException.class)
//    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException e){
//        Map<String, Object> error = new HashMap<>();
//        error.put("status", e.getStatusCode().value());
//        error.put("message", e.getReason());
//        error.put("timestamp", System.currentTimeMillis());
//        return ResponseEntity.status(e.getStatusCode()).body(error);
//    }
    @ExceptionHandler(ResponseStatusException.class)
    public BaseError<?> handleResponseStatusException(ResponseStatusException e){
        return BaseError.builder()
                .status(false)
                .code(e.getStatusCode().value())
                .message(e.getReason())
                .timestamp(LocalDateTime.now())
                .error(e.getMessage())
                .build();
    }
}
