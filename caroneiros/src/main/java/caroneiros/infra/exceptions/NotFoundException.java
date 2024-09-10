package caroneiros.infra.exceptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String mensage) {
        super(mensage);
    }
}
