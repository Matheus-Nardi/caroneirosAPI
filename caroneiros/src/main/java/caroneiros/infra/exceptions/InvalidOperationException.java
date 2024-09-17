package caroneiros.infra.exceptions;

public class InvalidOperationException  extends RuntimeException{

    public InvalidOperationException(String mensage) {
        super(mensage);
    }
}
