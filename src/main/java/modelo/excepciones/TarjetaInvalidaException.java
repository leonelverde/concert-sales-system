package modelo.excepciones;

public class TarjetaInvalidaException extends Exception {
    public TarjetaInvalidaException(String mensaje) {
        super(mensaje);
    }
}