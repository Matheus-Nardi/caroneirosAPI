package caroneiros.infra.exception;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String mensage) {
        super(mensage);
    }
}
