package caroneiros.infra.exceptions;

public class VehicleNotOwnedException extends RuntimeException {

    public VehicleNotOwnedException(String mensage) {
        super(mensage);
    }
}
