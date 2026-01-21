package cat.itacademy.s04.t02.n02.fruit.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}