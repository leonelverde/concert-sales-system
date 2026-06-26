
package coleccion;

import modelo.Tarjeta;
import archivosInfo.ArchivoTarjeta;

public class ColeccionTarjeta {
    
    public static boolean agregarTarjeta(Tarjeta nueva) {
        Tarjeta[] listaActual = ArchivoTarjeta.cargarTarjetas();
        Tarjeta[] nuevaLista = new Tarjeta[listaActual.length + 1];
        
        for (int i = 0; i < listaActual.length; i++) {
            nuevaLista[i] = listaActual[i];
        }
        nuevaLista[listaActual.length] = nueva;
        
        return ArchivoTarjeta.guardarTarjetas(nuevaLista, nuevaLista.length);
    }
    
    public static boolean eliminarTarjeta(String numeroBuscado) {
        Tarjeta[] listaActual = ArchivoTarjeta.cargarTarjetas();
        int indiceEncontrado = -1;

        // Buscar la posicion del arreglo en l que esta la tarjeta
        for (int i = 0; i < listaActual.length; i++) {
            if (listaActual[i].getNumero().equals(numeroBuscado)) {
                indiceEncontrado = i;
                break;
            }
        }

        // Si no se encontró, retorna falso
        if (indiceEncontrado == -1) {
            return false; 
        }

        // Crear el arreglo reducido saltandose ese indice
        Tarjeta[] nuevaLista = new Tarjeta[listaActual.length - 1];
        int j = 0;

        for (int i = 0; i < listaActual.length; i++) {
            if (i == indiceEncontrado) continue;
            
            nuevaLista[j] = listaActual[i];
            j++;
        }

        return ArchivoTarjeta.guardarTarjetas(nuevaLista, nuevaLista.length);
    }
    
    public static Tarjeta[] obtenerTarjetas() {
        return ArchivoTarjeta.cargarTarjetas();
    }
    
    public static Tarjeta[] obtenerTarjetasPorCliente(String dniCliente) {
        Tarjeta[] todas = ArchivoTarjeta.cargarTarjetas();
    
        // Contamos cuántas le pertenecen para dimensionar el arreglo
        int contador = 0;
        for (Tarjeta t : todas) {
            if (t.getDniCliente().equals(dniCliente)) contador++;
        }
    
        // Creamos el arreglo filtrado
        Tarjeta[] filtradas = new Tarjeta[contador];
        int j = 0;
        for (Tarjeta t : todas) {
            if (t.getDniCliente().equals(dniCliente)) {
                filtradas[j] = t;
                j++;
            }
        }
        return filtradas;
    }
}
