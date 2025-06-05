package godsang.backend.controller.advice;

import godsang.backend.entity.member.exception.MemberTaskException;
import godsang.backend.exception.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@Log4j2
public class ControllerAdvice {


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?>
    handlerEntityNotFoundException(EntityNotFoundException exception) {
        Map<String, String> errors = Map.of("message", "Entity Not Found");
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MemberTaskException.class)
    public ResponseEntity<Map<String, String>> handleTaskException(MemberTaskException ex) {
        log.error(ex.getMessage());

        String msg = ex.getMsg();
        int status = ex.getCode();

        Map<String, String> map = Map.of("error", msg);
        return ResponseEntity.status(status).body(map);
    }
}
