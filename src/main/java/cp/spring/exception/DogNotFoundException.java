package cp.spring.exception;

public class DogNotFoundException extends RuntimeException {
	public DogNotFoundException(String message) {
		super(message);
	}
}
