package io.github.rostikq.gamehub.exception_handlers;

import jakarta.persistence.PersistenceException;
import org.hibernate.QueryTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class DatabaseExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler({DataAccessException.class, PersistenceException.class})
    public ResponseEntity<String> handleQueryTimeoutException(Exception e) {
        logger.warn(e.getMessage());
        Map<String, Object> errorBody = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "error", "Internal Server Error",
                "message", "A database error occurred while processing your request."
        );
        return ResponseEntity.internalServerError().body(
                errorBody.toString()
        );
    }
}
