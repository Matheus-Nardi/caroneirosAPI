package caroneiros.infra.exception;

public class NotFoundVehicleException extends RuntimeException{

    public NotFoundVehicleException(String mensage) {
        super(mensage);
    }
}
