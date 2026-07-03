
package modelo;

import java.util.ArrayList;
import java.util.List;
import modelo.excepciones.CapacidadExcedidaException;
import modelo.excepciones.EntradaNoDisponibleException;
import java.io.Serializable;

public class Zona implements Serializable{
    // Se fija el serialVersionUID al valor auto-generado original para no romper
    // la lectura de los archivos .dat existentes al agregar nuevos metodos.
    private static final long serialVersionUID = -1917868843209091102L;

    private String nombre;
    private int capacidad;
    private int precio;
    private List<Entrada> entradas;

    public Zona(String nombre, int capacidad, int precio){
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.precio = precio;
        this.entradas = new ArrayList<>();
        generarEntradasBase();
    }
    
    private void generarEntradasBase() {
        for (int i = 1; i <= capacidad; i++) {
            entradas.add(new Entrada(i, "Disponible"));
        }
    }
    
    public boolean generarEntradas(int cantidad) throws CapacidadExcedidaException {
        if (entradas.size() + cantidad > capacidad) {
            throw new CapacidadExcedidaException("No se pueden generar " + cantidad + " entradas. "
                    + "La capacidad máxima de la zona " + nombre + " es " + capacidad + ".");
    
        }
        for (int i = 0; i < cantidad; i++) {
            entradas.add(new Entrada(entradas.size() + 1, "Disponible"));
        }
        return true;
    }
    
    public int getCapacidadDisponible() {
        int disponibles = 0;
        for (Entrada e : entradas) {
            if ("Disponible".equalsIgnoreCase(e.getEstado())) {
                disponibles++;
            }
        }
        return disponibles;
    }
    
    public Entrada[] mostrarEntrada(){return new Entrada[0];}

    public List<Entrada> venderEntradas(int numero) throws CapacidadExcedidaException, EntradaNoDisponibleException {
        if (numero > getCapacidadDisponible()) {
            throw new CapacidadExcedidaException("Solo quedan " + getCapacidadDisponible() + " entradas disponibles en la zona " + nombre + ".");
        }

        List<Entrada> seleccionadas = new ArrayList<>();
        int cont = 0;
        for (Entrada e : entradas) {
            if ("Disponible".equalsIgnoreCase(e.getEstado()) && cont < numero) {
                e.vender();
                seleccionadas.add(e);
                cont++;
            }
        }
        return seleccionadas;
    }
    
    public int getPrecio() { return precio; }
    public String getNombre() { return nombre; }
    public List<Entrada> getEntradas() {return entradas;}

    public int getCapacidad() { return capacidad; }

    // Permite que el administrador modifique el nombre de la zona.
    public void setNombre(String nombre) { this.nombre = nombre; }

    // Permite que el administrador modifique el precio de la zona.
    public void setPrecio(int precio) { this.precio = precio; }
}
