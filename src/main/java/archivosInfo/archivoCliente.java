
package archivosInfo;

import serializador.Serializador;
import modelo.Cliente;
import java.io.File;

public class archivoCliente {
    private String path;
    
    public archivoCliente(){
        this.path = "datos_cliente.dat";
    }
    
    private void crearCarpeta(){
        File carpeta = new File("datos");
        if(!carpeta.exists()){
            carpeta.mkdir();
        }
    }
    
}
