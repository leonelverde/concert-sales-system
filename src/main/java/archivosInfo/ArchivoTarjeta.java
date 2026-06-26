
package archivosInfo;

import serializador.Serializador;
import modelo.Tarjeta;
import java.io.File;
import java.io.Serializable;

public class ArchivoTarjeta implements Serializable {
    private static final String path = "datos_tarjeta.dat";

    private void crearCarpeta(){
        File carpeta = new File("datos");
        if(!carpeta.exists()){
            carpeta.mkdir();
        }
    }
    
    public static boolean guardarTarjetas(Tarjeta[] tarjetas, int numTarjetas) {
        try {
            Tarjeta[] arregloAux = new Tarjeta[numTarjetas];
            for (int i = 0; i < numTarjetas; i++) {
                arregloAux[i] = tarjetas[i];
            }
            
            Serializador.serializar(path, arregloAux);
            return true;
        } catch (Exception e) {
            System.out.println("Error al guardar Tarjetas: " + e.getMessage());
            return false;
        }
    }

    public static Tarjeta[] cargarTarjetas() {
        try {
            File archivo = new File(path);
            if (archivo.exists()) {
                Object obj = Serializador.deserializar(path);
                return (Tarjeta[]) obj;
            } else {
                return new Tarjeta[0];
            }
        } catch (Exception e) {
            System.out.println("Error al cargar Tarjetas: " + e.getMessage());
        }
        return new Tarjeta[0];
    }
}
