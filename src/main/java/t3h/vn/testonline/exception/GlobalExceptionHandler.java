//package t3h.vn.testonline.exception;
//
//
//import jakarta.validation.ConstraintViolationException;
//import org.springframework.http.HttpStatus;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorResponse handleValidationException(Exception e, WebRequest request){
//        ErrorResponse errorResponse = new ErrorResponse();
//
//        errorResponse.setTimestamp(new Date());
//        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
//        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
//
//        String message = e.getMessage();
//        if (e instanceof MethodArgumentNotValidException ex){
//            errorResponse.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
//            Map<String, String> errors = new HashMap<>();
//            ex.getBindingResult().getAllErrors().forEach((error)->{
//                String fieldName =((FieldError) error).getField();
//                String errorMessage = error.getDefaultMessage();
//                errors.put(fieldName, errorMessage);
//            });
//            errorResponse.setMessage(errors.toString());
//        }else if (e instanceof  ConstraintViolationException){
//            message = message.substring(message.indexOf(" ") + 1);
//            errorResponse.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
//            errorResponse.setMessage(message);
//        }
//        return errorResponse;
//    }
//
//}
