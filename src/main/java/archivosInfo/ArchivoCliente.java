
package archivosInfo;

import serializador.Serializador;
import modelo.Cliente;
import java.io.File;
import java.io.Serializable;

public class ArchivoCliente implements Serializable{
    private static String path;
    
    public ArchivoCliente(){
        this.path = "datos_cliente.dat";
    }
    
    private void crearCarpeta(){
        File carpeta = new File("datos");
        if(!carpeta.exists()){
            carpeta.mkdir();
        }
    }
    
    public static boolean guardarCliente(Cliente[] clientes, int numClientes){
        try{
            Cliente[] arregloAux = new Cliente[numClientes];
            for(int i=0; i<numClientes; i++){
                arregloAux[i] = clientes[i];
            }
            
            Serializador.serializar(path, arregloAux);
            return true;
        } catch(Exception e){
            System.out.println("Error al guardar Clientes: " + e.getMessage());
            return false;
        }
    }
    
    public static Cliente[] cargarCliente(){
        try{
            File archivo = new File(path);
            
            if(archivo.exists()){
                Object obj = Serializador.deserializar(path);
                return (Cliente[]) obj;
            } else {return new Cliente[0];}
        } catch(Exception e){
            System.out.println("Error al cargar Clientes: " + e.getMessage());
        }
        return new Cliente[0];
    }
    
}
