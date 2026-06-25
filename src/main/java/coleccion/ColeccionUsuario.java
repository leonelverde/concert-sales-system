
package coleccion;

import modelo.Usuario;
import archivosInfo.ArchivoUsuario;

public class ColeccionUsuario {
    
    public static boolean agregarUsuario(Usuario nuevoAdmin) {
        Usuario[] listaActual = ArchivoUsuario.cargarUsuarios();
        Usuario[] nuevaLista = new Usuario[listaActual.length + 1];
        
        for (int i = 0; i < listaActual.length; i++) {
            nuevaLista[i] = listaActual[i];
        }
        nuevaLista[listaActual.length] = nuevoAdmin;
        
        return ArchivoUsuario.guardarUsuarios(nuevaLista, nuevaLista.length);
    }
    
    public static Usuario[] obtenerUsuarios() {
        return ArchivoUsuario.cargarUsuarios();
    }
}
