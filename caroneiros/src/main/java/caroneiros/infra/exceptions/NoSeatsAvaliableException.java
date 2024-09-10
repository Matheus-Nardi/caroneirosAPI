package caroneiros.infra.exceptions;

public class NoSeatsAvaliableException extends RuntimeException {
    public NoSeatsAvaliableException(String mensage) {
        super(mensage);
    }
}
