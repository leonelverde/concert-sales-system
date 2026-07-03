package controlador;

import vista.PanelTarjetasRegistradas;
import modelo.Sistema; // Memoria RAM
import modelo.GestorPersistencia; // Guardado rápido
import modelo.Tarjeta;
import java.awt.CardLayout;
import java.awt.Container;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import modelo.Cliente;
import java.util.List;

public class ControladorTarjetasRegistradas {
    
    private PanelTarjetasRegistradas vistaTarjetas;
    private Container contenedorPrincipal;
    private Cliente clienteActual;

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
    
    public void setClienteSesion(Cliente cliente) {
        this.clienteActual = cliente;
        this.refrescarCatalogo();
    }
    
    public void refrescarCatalogo() {
        if (clienteActual == null) return;
        List<Tarjeta> lista = Sistema.getTarjetasPorCliente(clienteActual.getDni());
        this.vistaTarjetas.poblarTabla(lista.toArray(new Tarjeta[0]));
    }
    
    private void eliminarSeleccionada() {
        JTable tabla = vistaTarjetas.getTablaTarjetas();
        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vistaTarjetas, "Debe hacer clic sobre una tarjeta de la tabla para seleccionarla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String numOculto = tabla.getValueAt(fila, 0).toString(); 

        int confirm = JOptionPane.showConfirmDialog(
            vistaTarjetas, 
            "¿Seguro que desea eliminar la tarjeta seleccionada (" + numOculto + ")?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            List<Tarjeta> delCliente = Sistema.getTarjetasPorCliente(clienteActual.getDni());
            if(fila < delCliente.size()){
                Tarjeta aEliminar = delCliente.get(fila);
                
                // Borramos de la RAM y guardamos en Disco
                Sistema.tarjetas.remove(aEliminar);
                GestorPersistencia.guardarDatos();
                
                refrescarCatalogo(); 
                JOptionPane.showMessageDialog(vistaTarjetas, "Tarjeta eliminada correctamente.");
            } else {
                JOptionPane.showMessageDialog(vistaTarjetas, "No se pudo eliminar la tarjeta.", "Error", JOptionPane.ERROR_MESSAGE);
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