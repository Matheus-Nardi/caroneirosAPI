package caroneiros.infra.exception;

public class DontDriverException extends RuntimeException {
    public DontDriverException(String mensage) {
        super(mensage);
    }
}
