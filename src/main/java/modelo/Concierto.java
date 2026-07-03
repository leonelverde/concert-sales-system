
package modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.Serializable;

public class Concierto implements Serializable{
    // Se fija el serialVersionUID al valor auto-generado original para no romper
    // la lectura de los archivos .dat existentes al agregar nuevos campos.
    private static final long serialVersionUID = -1926816076331256569L;

    private String nombre;
    private Date fecha;
    private List<Zona> zonas;
    // Descuentos configurables por concierto: clave = EmisorTarjeta.name(),
    // valor = porcentaje de descuento a aplicar para ese emisor.
    private Map<String, Double> descuentosPorEmisor;

    public Concierto(String nombre, Date fecha){
        this.nombre = nombre;
        this.fecha = fecha;
        this.zonas = new ArrayList<>();
        inicializarDescuentosPorDefecto();
    }

    // Carga el porcentaje por defecto de cada emisor.
    private void inicializarDescuentosPorDefecto() {
        this.descuentosPorEmisor = new HashMap<>();
        for (EmisorTarjeta emisor : EmisorTarjeta.values()) {
            this.descuentosPorEmisor.put(emisor.name(), emisor.getDescuentoPorDefecto());
        }
    }

    public boolean agregarZona(Zona zona){return this.zonas.add(zona);}

    public boolean eliminarZona(Zona zona){return this.zonas.remove(zona);}

    public List<Zona> getZonas() {
        return zonas;
    }

    public double calcularRecaudacionTotal() {
        return 0.0;
    }

    public String getNombre(){
        return this.nombre;
    }

    public Date getFecha(){
        return this.fecha;
    }

    // --- Descuentos configurables por concierto ---

    /**
     * Devuelve el porcentaje de descuento configurado para el emisor indicado.
     * Si el concierto proviene de un archivo antiguo (sin descuentos guardados),
     * se inicializan los valores por defecto de forma perezosa.
     */
    public double getDescuento(EmisorTarjeta emisor) {
        if (descuentosPorEmisor == null) {
            inicializarDescuentosPorDefecto();
        }
        Double porcentaje = descuentosPorEmisor.get(emisor.name());
        return porcentaje == null ? emisor.getDescuentoPorDefecto() : porcentaje;
    }

    /**
     * Configura el porcentaje de descuento para un emisor en este concierto.
     */
    public void setDescuento(EmisorTarjeta emisor, double porcentaje) {
        if (descuentosPorEmisor == null) {
            inicializarDescuentosPorDefecto();
        }
        this.descuentosPorEmisor.put(emisor.name(), porcentaje);
    }

    public Map<String, Double> getDescuentosPorEmisor() {
        if (descuentosPorEmisor == null) {
            inicializarDescuentosPorDefecto();
        }
        return descuentosPorEmisor;
    }
}
