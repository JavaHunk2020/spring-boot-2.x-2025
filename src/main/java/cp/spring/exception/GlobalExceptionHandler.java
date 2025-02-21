package cp.spring.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


//HTTP STATUS =200
//HTTP STATUS = 201
//HTTP STATUS = 302
//HTTP STATUS = 400 - BAD REQUEST
//HTTP STATUS = 401 - Authentication failed
//403 Status  = FORBIDDEN
//500 - SERVER INTERNAL ERROR
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ServiceNotFoundException.class)
	public ResponseEntity<ErrorMessage> serviceNotFound(ServiceNotFoundException ex){
		ErrorMessage errorMessage=new ErrorMessage();
		errorMessage.setMessage(ex.getMessage());
		errorMessage.setCode("C0192");
		return new ResponseEntity<>(errorMessage,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DogNotFoundException.class)
	public ResponseEntity<Map<String, Object>> generateIt(DogNotFoundException ex,WebRequest request){
		    Map<String, Object> response = new HashMap<>();
	        response.put("timestamp", LocalDateTime.now());
	        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
	        response.put("error", "Internal Server Error");
	        response.put("uri", request.getDescription(false));
	        response.put("message", ex.getMessage());
	        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorMessage> generateIt(AccessDeniedException ex,WebRequest request){
		ErrorMessage errorMessage=new ErrorMessage();
		errorMessage.setMessage(ex.getMessage());
		errorMessage.setCode("C400");
		errorMessage.setUri(request.getDescription(false));
		errorMessage.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(errorMessage,HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> generateIt(Exception ex){
		ErrorMessage errorMessage=new ErrorMessage();
		errorMessage.setMessage(ex.getMessage());
		errorMessage.setCode("C500");
		return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	

}
