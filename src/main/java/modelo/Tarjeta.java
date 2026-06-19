
package modelo;

import java.io.Serializable;

public class Tarjeta implements Serializable {
    private int numero;
    private String nombre;
    private String fecha;
    private int cvv;

    public Tarjeta(int numero, String nombre, String fecha, int cvv){
        this.numero = numero;
        this.nombre = nombre;
        this.fecha = fecha;
        this.cvv = cvv;
    }
    
    public void method(){}
}
