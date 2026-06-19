
package modelo;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import modelo.excepciones.EntradaLimiteException;
import java.io.Serializable;

public class Venta implements Serializable {
    private Date fecha;
    private int monto;
    private Zona zona;
    private Tarjeta tarjeta;
    private List<Entrada> entradas;
    
    public Venta(Zona zona, Tarjeta tarjeta){
        this.fecha = new Date();
        this.zona = zona;
        this.tarjeta = tarjeta;
        this.entradas = new ArrayList<>();
        this.monto = 0;
    }
    
    public void agregarEntradas(List<Entrada> nuevasEntradas) throws EntradaLimiteException {
        if (this.entradas.size() + nuevasEntradas.size() > 4) {
            throw new EntradaLimiteException("Error: Una transacción no puede contener más de 4 entradas.");
        }
        this.entradas.addAll(nuevasEntradas);
        this.monto = this.entradas.size() * zona.getPrecio();
    }
    
    public boolean anular(){return true;}
    public Date getFecha() { return fecha; }
    public int getMonto() { return monto; }
}
