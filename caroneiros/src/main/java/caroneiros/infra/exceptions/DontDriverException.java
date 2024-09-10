package caroneiros.infra.exceptions;

public class DontDriverException extends RuntimeException {
    public DontDriverException(String mensage) {
        super(mensage);
    }
}
