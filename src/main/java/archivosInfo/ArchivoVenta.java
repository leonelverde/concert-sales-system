
package archivosInfo;

import serializador.Serializador;
import modelo.Venta;
import java.io.File;
import java.io.Serializable;

public class ArchivoVenta implements Serializable{
    private String path;
    
    public ArchivoVenta(){
        this.path = "datos_venta.dat";
    }
    
    private void crearCarpeta(){
        File carpeta = new File("datos");
        if(!carpeta.exists()){
            carpeta.mkdir();
        }
    }
    
    public boolean guardarVentas(Venta[] ventas, int numVentas){
        try{
            Venta[] arregloAux = new Venta[numVentas];
            for(int i=0; i<numVentas; i++){
                arregloAux[i] = ventas[i];
            }
            
            Serializador.serializar(path, arregloAux);
            return true;
        } catch(Exception e){
            System.out.println("Error al guardar Ventas: " + e.getMessage());
            return false;
        }
    }
    
    public Venta[] cargarVenta(){
        try{
            File archivo = new File(path);
            
            if(archivo.exists()){
                Object obj = Serializador.deserializar(path);
            } else {return new Venta[0];}
        } catch(Exception e){
            System.out.println("Error al cargar Ventas: " + e.getMessage());
        }
        return new Venta[0];
    }
    
}

