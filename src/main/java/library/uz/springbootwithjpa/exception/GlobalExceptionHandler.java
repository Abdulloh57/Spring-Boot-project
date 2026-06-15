package library.uz.springbootwithjpa.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RecordNotFoundException.class)
    public String handlerRecordNotFoundException(RecordNotFoundException ex, Model model){
        model.addAttribute("message" , ex.getMessage());
        return "404";
    }
}
