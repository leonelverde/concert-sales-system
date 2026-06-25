
package archivosInfo;

import serializador.Serializador;
import modelo.Usuario;
import java.io.File;
import java.io.Serializable;

public class ArchivoUsuario implements Serializable{
    
    private static final String path = "datos_usuario.dat";
    
    private void crearCarpeta(){
        File carpeta = new File("datos");
        if(!carpeta.exists()){
            carpeta.mkdir();
        }
    }
    
    public static boolean guardarUsuarios(Usuario[] usuarios, int numUsuarios){
        try{
            Usuario[] arregloAux = new Usuario[numUsuarios];
            for(int i=0; i<numUsuarios; i++){
                arregloAux[i] = usuarios[i];
            }
            
            Serializador.serializar(path, arregloAux);
            return true;
        } catch(Exception e){
            System.out.println("Error al guardar Usuario Admin: " + e.getMessage());
            return false;
        }
    }
    
    public static Usuario[] cargarUsuarios(){
        try{
            File archivo = new File(path);
            
            if(archivo.exists()){
                Object obj = Serializador.deserializar(path);
                return (Usuario[]) obj;
            } else {return new Usuario[0];}
        } catch(Exception e){
            System.out.println("Error al cargar Usuarios: " + e.getMessage());
        }
        return new Usuario[0];
    }
}
