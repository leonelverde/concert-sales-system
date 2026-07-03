
package modelo;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import modelo.excepciones.EntradaLimiteException;
import java.io.Serializable;

public class Venta implements Serializable {
    // Se fija el serialVersionUID al valor auto-generado original para no romper
    // la lectura de los archivos .dat existentes al agregar nuevos campos.
    private static final long serialVersionUID = 5189858463623393900L;

    private Date fecha;
    private int monto;          // monto final ya con descuento aplicado
    private int subtotal;       // monto antes del descuento
    private double porcentajeDescuento;
    private String emisor;      // etiqueta del emisor de la tarjeta usada
    private String nombreConcierto;
    private boolean anulada;
    private Cliente cliente;
    private Zona zona;
    private Tarjeta tarjeta;
    private List<Entrada> entradas;

    public Venta(Cliente cliente, Zona zona, Tarjeta tarjeta){
        this.fecha = new Date();
        this.cliente = cliente;
        this.zona = zona;
        this.tarjeta = tarjeta;
        this.entradas = new ArrayList<>();
        this.monto = 0;
        this.subtotal = 0;
        this.porcentajeDescuento = 0.0;
        this.anulada = false;
    }

    public void agregarEntradas(List<Entrada> nuevasEntradas) throws EntradaLimiteException {
        if (this.entradas.size() + nuevasEntradas.size() > 4) {
            throw new EntradaLimiteException("Error: Una transacción no puede contener más de 4 entradas.");
        }
        this.entradas.addAll(nuevasEntradas);
        this.subtotal = this.entradas.size() * zona.getPrecio();
        this.monto = calcularMontoFinal();
    }

    // Aplica el descuento del emisor sobre el subtotal y recalcula el monto.
    public void aplicarDescuento(String emisor, double porcentajeDescuento) {
        this.emisor = emisor;
        this.porcentajeDescuento = porcentajeDescuento;
        this.monto = calcularMontoFinal();
    }

    private int calcularMontoFinal() {
        double factor = 1.0 - (porcentajeDescuento / 100.0);
        return (int) Math.round(subtotal * factor);
    }

    // Marca la venta como anulada (usado por el administrador al cancelarla).
    public boolean anular(){
        this.anulada = true;
        return true;
    }

    public Date getFecha() { return fecha; }
    public int getMonto() { return monto; }
    public int getSubtotal() { return subtotal; }
    public double getPorcentajeDescuento() { return porcentajeDescuento; }
    public String getEmisor() { return emisor; }
    public Cliente getCliente() { return cliente; }
    public Zona getZona() { return zona; }
    public Tarjeta getTarjeta() { return tarjeta; }
    public List<Entrada> getEntradas() { return entradas; }
    public boolean isAnulada() { return anulada; }

    public String getNombreConcierto() { return nombreConcierto; }
    public void setNombreConcierto(String nombreConcierto) { this.nombreConcierto = nombreConcierto; }
}
