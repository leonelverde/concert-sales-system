
package modelo;

import java.util.ArrayList;
import java.util.List;

import modelo.excepciones.EntradaLimiteException;
import modelo.excepciones.CapacidadExcedidaException;
import modelo.excepciones.EntradaNoDisponibleException;

public class Cliente extends Persona{
    private Integer puntos;
    private List<Venta> historialCompras;
    private List<Tarjeta> tarjetasRegistradas;
    
    private Zona zonaSeleccionada;
    private Tarjeta tarjetaSeleccionada;
    
    public Cliente(String nombres, String apellidos, String dni, String contraseña, String usuario, Integer puntos){
        super(nombres, apellidos, dni, usuario, contraseña);
        this.puntos = puntos;
        this.historialCompras = new ArrayList<>();
        this.tarjetasRegistradas = new ArrayList<>();
        
    }
    
    private void ingresar (String usuario, String clave){}
    
    public void registrarTarjeta(Tarjeta tarjeta) {
        this.tarjetasRegistradas.add(tarjeta);
    }
    
    public void setZonaSeleccionada(Zona zona) {
        this.zonaSeleccionada = zona;
    }

    public void setTarjetaSeleccionada(Tarjeta tarjeta) {
        this.tarjetaSeleccionada = tarjeta;
    }
    
    @Override
    public Boolean comprar(int cantidad) throws EntradaLimiteException, CapacidadExcedidaException, EntradaNoDisponibleException     {
        if (cantidad > 4) {
            throw new EntradaLimiteException("Error en Cliente: No se pueden comprar mas de 4 entradas por transaccion.");
        }
        if (cantidad <= 0) {
            throw new EntradaLimiteException("Error en Cliente: La cantidad debe ser mayor a 0.");
        }
        if (zonaSeleccionada == null || tarjetaSeleccionada == null) {
            throw new IllegalStateException("Operación denegada: Debe seleccionar una zona y una tarjeta válidas antes de comprar.");
        }
        
        Venta nuevaVenta = new Venta(zonaSeleccionada, tarjetaSeleccionada);
        List<Entrada> entradasDespachadas = zonaSeleccionada.venderEntradas(cantidad);
        nuevaVenta.agregarEntradas(entradasDespachadas);

        historialCompras.add(nuevaVenta);
        return true;
    }
    
    public List<Venta> getHistorialCompras() {
        return historialCompras;
    }
         
}
