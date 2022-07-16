package pl.mbrzozowski.vulcanizer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private DefaultErrorAttributes defaultErrorAttributes;

//    @ExceptionHandler(JsonMappingException.class)
//    public ResponseEntity<ResponseError> handleConflict(RuntimeException exception) {
////        return new ResponseEntity<>(exception.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
////        ResponseError responseError = new ResponseError("404",
////                HttpStatus.BAD_REQUEST.getReasonPhrase(),
////                "Enum can not be null or value is not accepted for Enum class.");
//        ResponseError responseError = new ResponseError("404",
//                HttpStatus.BAD_REQUEST.getReasonPhrase(),
//                exception.getMessage());
//        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
//    }

}
