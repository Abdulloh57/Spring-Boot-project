package library.uz.springbootwithjpa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RecordNotFoundException.class)
    public String handlerRecordNotFoundException(RecordNotFoundException ex, Model model){
        model.addAttribute("message" , ex.getMessage());
        return "404";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> notvalid(MethodArgumentNotValidException ex , Model model){
        Map<String , String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> errors.put(err.getField() , err.getDefaultMessage()));
       // model.addAttribute("message", errors);
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public String runtimeExceptionHandler(AlreadyExistException ex, Model model){
        model.addAttribute("message" , ex.getMessage());
        return "404";
    }
}
