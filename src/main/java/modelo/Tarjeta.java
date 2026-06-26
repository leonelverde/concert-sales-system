
package modelo;

import java.io.Serializable;
import java.util.Objects;

public class Tarjeta implements Serializable {
    private String dniCliente;
    private String numero;
    private String nombre;
    private String fecha;
    private String cvv;

    public Tarjeta(String dniCliente, String numero, String nombre, String fecha, String cvv){
        this.dniCliente = dniCliente;
        this.numero = numero;
        this.nombre = nombre;
        this.fecha = fecha;
        this.cvv = cvv;
    }
    
    public void method(){}
    
    public String getDniCliente() { return dniCliente; }
    public String getNumero() { return numero; }
    public String getNombre() { return nombre; }
    public String getFecha() { return fecha; }
    public String getCvv() { return cvv; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tarjeta tarjeta = (Tarjeta) o;
        return Objects.equals(numero, tarjeta.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }
      
}
