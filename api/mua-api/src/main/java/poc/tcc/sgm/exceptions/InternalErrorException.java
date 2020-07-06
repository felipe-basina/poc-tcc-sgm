package poc.tcc.sgm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalErrorException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -878492227771597678L;

	public InternalErrorException(String message) {
		super(message);
	}
	
}
