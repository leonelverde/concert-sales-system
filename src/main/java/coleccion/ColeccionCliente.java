
package coleccion;

import modelo.Cliente;
import archivosInfo.ArchivoCliente;

public class ColeccionCliente {
    
    public static boolean agregarCliente(Cliente nuevoCliente) {
        Cliente[] listaActual = ArchivoCliente.cargarClientes();
        Cliente[] nuevaLista = new Cliente[listaActual.length + 1];
        
        for (int i = 0; i < listaActual.length; i++) {
            nuevaLista[i] = listaActual[i];
        }
        nuevaLista[listaActual.length] = nuevoCliente;
        
        return ArchivoCliente.guardarClientes(nuevaLista, nuevaLista.length);
    }
    
    public static Cliente[] obtenerClientes() {
        return ArchivoCliente.cargarClientes();
    }
}
