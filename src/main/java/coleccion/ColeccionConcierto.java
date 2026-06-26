
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
    
    public static boolean actualizarConcierto(Concierto conciertoModificado) {

        Concierto[] listaActual = ArchivoConcierto.cargarConciertos();
        boolean encontrado = false;

        for (int i = 0; i < listaActual.length; i++) {
            if (listaActual[i].getNombre().equalsIgnoreCase(conciertoModificado.getNombre())) {
                listaActual[i] = conciertoModificado; 
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("Error crítico: El concierto " + conciertoModificado.getNombre() + " no existe en el archivo.");
            return false;
        }

        return ArchivoConcierto.guardarConciertos(listaActual, listaActual.length);
    }
    
    // Recupera la lista completa de conciertos desde el archivo
    public static Concierto[] obtenerConciertos() {
        return ArchivoConcierto.cargarConciertos();
    }
}
