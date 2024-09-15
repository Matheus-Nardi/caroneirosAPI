package caroneiros.infra.exceptions;

public class NoVehiclesException extends RuntimeException {
    public NoVehiclesException(String mensage) {
        super(mensage);
    }
}
