
package controlador;

import vista.PanelTarjetasRegistradas;
import coleccion.ColeccionTarjeta;
import modelo.Tarjeta;
import java.awt.CardLayout;
import java.awt.Container;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import modelo.Cliente;

public class ControladorTarjetasRegistradas {
    
    private PanelTarjetasRegistradas vistaTarjetas;
    private Container contenedorPrincipal;

    public ControladorTarjetasRegistradas(PanelTarjetasRegistradas vistaTarjetas, Container contenedorPrincipal) {
        this.vistaTarjetas = vistaTarjetas;
        this.contenedorPrincipal = contenedorPrincipal;
        this.iniciar();
    }

    private void iniciar() {
        this.vistaTarjetas.getButtonVolver().addActionListener(e -> volver());
        this.vistaTarjetas.getButtonAgregarTarjeta().addActionListener(e -> irAAgregarTarjeta());
        this.vistaTarjetas.getButtonEliminarTarjeta().addActionListener(e -> eliminarSeleccionada());
    }
    
    private Cliente clienteActual;
    
    public void setClienteSesion(Cliente cliente) {
        this.clienteActual = cliente;
        this.refrescarCatalogo();
    }
    
    public void refrescarCatalogo() {
        Tarjeta[] lista = ColeccionTarjeta.obtenerTarjetasPorCliente(clienteActual.getDni());
        this.vistaTarjetas.poblarTabla(lista);
    }
    
    private void eliminarSeleccionada() {
        JTable tabla = vistaTarjetas.getTablaTarjetas();
        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vistaTarjetas, "Debe hacer clic sobre una tarjeta de la tabla para seleccionarla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Tomamos el número de tarjeta visualizado en la columna 0 de esa fila
        String numTarjeta = tabla.getValueAt(fila, 0).toString(); 

        int confirm = JOptionPane.showConfirmDialog(
            vistaTarjetas, 
            "¿Seguro que desea eliminar la tarjeta terminada en " + numTarjeta.substring(numTarjeta.length() - 4) + "?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            boolean borrado = ColeccionTarjeta.eliminarTarjeta(numTarjeta);
            
            if (borrado) {
                refrescarCatalogo(); // Redibujamos la tabla limpia
                JOptionPane.showMessageDialog(vistaTarjetas, "Tarjeta eliminada correctamente.");
            } else {
                JOptionPane.showMessageDialog(vistaTarjetas, "No se pudo eliminar la tarjeta del archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void irAAgregarTarjeta() {
        CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
        cl.show(contenedorPrincipal, "agregarTarjeta");
    }

    private void volver() {
        CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
        cl.show(contenedorPrincipal, "cliente");
    }
}
