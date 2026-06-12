
package modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Concierto {
    private String nombre;
    private Date fecha;
    private List<Zona> zonas; 

    public Concierto(String nombre, Date fecha){
        this.nombre = nombre;
        this.fecha = fecha;
        this.zonas = new ArrayList<>();
    }

    public boolean agregarZona(Zona zona){return this.zonas.add(zona);}

    public boolean eliminarZona(Zona zona){return this.zonas.remove(zona);}
    
    public List<Zona> getZonas() {
        return zonas;
    }
    
    public double calcularRecaudacionTotal() {
        return 0.0; 
    }
}
