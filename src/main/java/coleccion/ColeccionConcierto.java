
package coleccion;

import modelo.Concierto;
import archivosInfo.ArchivoConcierto;

public class ColeccionConcierto {
    // Agrega un nuevo concierto al arreglo existente y lo manda a guardar automaticamente
    public static boolean agregarConcierto(Concierto nuevoConcierto) {
        Concierto[] listaActual = ArchivoConcierto.cargarConciertos();
        Concierto[] nuevaLista = new Concierto[listaActual.length + 1];
        
        for (int i = 0; i < listaActual.length; i++) {
            nuevaLista[i] = listaActual[i];
        }
        nuevaLista[listaActual.length] = nuevoConcierto;
        
        return ArchivoConcierto.guardarConciertos(nuevaLista, nuevaLista.length);
    }
    
    // Recupera la lista completa de conciertos desde el archivo
    public static Concierto[] obtenerConciertos() {
        return ArchivoConcierto.cargarConciertos();
    }
}
