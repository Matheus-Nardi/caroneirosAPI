package caroneiros.infra.exception;

public class NotFoundAppUserException extends RuntimeException{

    public NotFoundAppUserException(String mensage) {
        super(mensage);
    }
}
