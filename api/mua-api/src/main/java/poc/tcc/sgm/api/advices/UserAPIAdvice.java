package poc.tcc.sgm.api.advices;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import poc.tcc.sgm.api.UserAPI;
import poc.tcc.sgm.dtos.ApiErrorResponse;
import poc.tcc.sgm.exceptions.UserAlreadyExistsException;
import poc.tcc.sgm.exceptions.UserNotFoundException;

@Slf4j
@RestControllerAdvice(assignableTypes = { UserAPI.class })
@SuppressWarnings("rawtypes")
public class UserAPIAdvice {

	public static final List<String> BAD_REQUEST_PREFIXS_ERROR = Arrays.asList("Validation failed for argument", "Required request body is missing");
	
	protected ApiErrorResponse errorResponse(final String message, final int httpStatus) {
		return ApiErrorResponse.builder()
				.message(message)
				.status(httpStatus)
				.timestamp(LocalDateTime.now().toString())
			.build();
	}
	
	@ExceptionHandler(value = UserAlreadyExistsException.class)
	protected ResponseEntity userAlreadyExistsExceptionHandler(Exception e) {
		log.error("Error user already exists", e);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(this.errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
	}
	
	@ExceptionHandler(value = UserNotFoundException.class)
	protected ResponseEntity userNotFoundExceptionHandler(Exception e) {
		log.error("Error user not found", e);
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(this.errorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value()));
	}
	
	@ExceptionHandler(value = Exception.class)
	protected ResponseEntity exceptionHandler(Exception e) {
		log.error("Error when processing users", e);
		if (this.isBadRequestForMissingInputData(e)) {
			return this.missingInputData(e);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(this.errorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
	}
	
	protected boolean isBadRequestForMissingInputData(Exception e) {
		if (Objects.nonNull(e.getLocalizedMessage()) && this.containsBadRequestErrorMessage(e.getLocalizedMessage())) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	private boolean containsBadRequestErrorMessage(final String errorMessage) {
		return BAD_REQUEST_PREFIXS_ERROR.stream().anyMatch(prefix -> errorMessage.contains(prefix));
	}
	
	protected ResponseEntity missingInputData(Exception ex) {
		String message = ex.getMessage();
		if (ex instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException manve = (MethodArgumentNotValidException) ex;
			StringBuilder errorMessages = new StringBuilder();
			for (Object object : manve.getBindingResult().getAllErrors()) {
				if (object instanceof FieldError) {
					FieldError fieldError = (FieldError) object;
					errorMessages.append(fieldError.getDefaultMessage()).append(";");
				}
			}
			message = errorMessages.toString();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(this.errorResponse(message, HttpStatus.BAD_REQUEST.value()));
	}
	
}
