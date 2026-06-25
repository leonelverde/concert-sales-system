

package archivosInfo;

import serializador.Serializador;
import modelo.Concierto;
import java.io.File;
import java.io.Serializable;

public class ArchivoConcierto implements Serializable{
    
    private static final String path = "datos_concierto.dat";
    
    private void crearCarpeta(){
        File carpeta = new File("datos");
        if(!carpeta.exists()){
            carpeta.mkdir();
        }
    }
    
    public boolean guardarConcierto(Concierto[] concierto, int numConciertos){
        try{
            Concierto[] arregloAux = new Concierto[numConciertos];
            for(int i=0; i<numConciertos; i++){
                arregloAux[i] = concierto[i];
            }
            
            Serializador.serializar(path, arregloAux);
            return true;
        } catch(Exception e){
            System.out.println("Error al guardar Conciertos: " + e.getMessage());
            return false;
        }
    }
    
    public Concierto[] cargarConcierto(){
        try{
            File archivo = new File(path);
            
            if(archivo.exists()){
                Object obj = Serializador.deserializar(path);
            } else {return new Concierto[0];}
        } catch(Exception e){
            System.out.println("Error al cargar Conciertos: " + e.getMessage());
        }
        return new Concierto[0];
    }
    
}

