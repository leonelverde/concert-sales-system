
package modelo;

/**
 * Representa el emisor (marca) de una tarjeta de credito/debito.
 *
 * La deteccion se realiza a partir de los primeros digitos del numero de la
 * tarjeta (IIN/BIN). Cada emisor tiene ademas un porcentaje de descuento por
 * defecto, que luego puede ser sobrescrito de forma configurable por cada
 * concierto (ver {@link Concierto#getDescuento(EmisorTarjeta)}).
 */
public enum EmisorTarjeta {

    VISA("Visa", 5.0),
    MASTERCARD("Mastercard", 10.0),
    DINERS("Diners Club", 15.0),
    AMEX("American Express", 7.0),
    DESCONOCIDO("Desconocido", 0.0);

    private final String etiqueta;
    private final double descuentoPorDefecto;

    EmisorTarjeta(String etiqueta, double descuentoPorDefecto) {
        this.etiqueta = etiqueta;
        this.descuentoPorDefecto = descuentoPorDefecto;
    }

    public String getEtiqueta() { return etiqueta; }

    public double getDescuentoPorDefecto() { return descuentoPorDefecto; }

    /**
     * Detecta el emisor a partir de los primeros digitos del numero de tarjeta.
     * Se apoya en los primeros 4 digitos (IIN/BIN) segun los rangos estandar de
     * la industria.
     *
     * @param numeroTarjeta numero de la tarjeta (se ignoran espacios).
     * @return el emisor detectado o {@link #DESCONOCIDO} si no coincide.
     */
    public static EmisorTarjeta detectar(String numeroTarjeta) {
        if (numeroTarjeta == null) {
            return DESCONOCIDO;
        }

        String numero = numeroTarjeta.replaceAll("\\s+", "");
        if (numero.length() < 4 || !numero.chars().allMatch(Character::isDigit)) {
            return DESCONOCIDO;
        }

        String prefijo = numero.substring(0, 4);
        int p4 = Integer.parseInt(prefijo);            // primeros 4 digitos
        int p2 = Integer.parseInt(prefijo.substring(0, 2)); // primeros 2 digitos
        char p1 = prefijo.charAt(0);                   // primer digito

        // American Express: 34, 37
        if (p2 == 34 || p2 == 37) {
            return AMEX;
        }
        // Diners Club: 300-305, 3095, 36, 38, 39
        if ((p4 >= 3000 && p4 <= 3059) || (p4 >= 3095 && p4 <= 3099)
                || p2 == 36 || p2 == 38 || p2 == 39) {
            return DINERS;
        }
        // Mastercard: 51-55, 2221-2720
        if ((p2 >= 51 && p2 <= 55) || (p4 >= 2221 && p4 <= 2720)) {
            return MASTERCARD;
        }
        // Visa: empieza con 4
        if (p1 == '4') {
            return VISA;
        }
        return DESCONOCIDO;
    }
}
