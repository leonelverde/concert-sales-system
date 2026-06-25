
package coleccion;

import modelo.Venta;
import archivosInfo.ArchivoVenta;

public class ColeccionVenta {
    
    public static boolean agregarVenta(Venta nuevaVenta) {
        Venta[] listaActual = ArchivoVenta.cargarVentas();
        Venta[] nuevaLista = new Venta[listaActual.length + 1];
        
        for (int i = 0; i < listaActual.length; i++) {
            nuevaLista[i] = listaActual[i];
        }
        nuevaLista[listaActual.length] = nuevaVenta;
        
        return ArchivoVenta.guardarVentas(nuevaLista, nuevaLista.length);
    }
    
    public static Venta[] obtenerVentas() {
        return ArchivoVenta.cargarVentas();
    }
}
