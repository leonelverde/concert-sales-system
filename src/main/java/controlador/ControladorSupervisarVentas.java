package controlador;

import vista.PanelSupervisarVentas;
import modelo.Sistema; // Memoria RAM
import modelo.GestorPersistencia; // Guardado
import modelo.Venta;
import java.awt.CardLayout;
import java.awt.Container;
import javax.swing.JOptionPane;

public class ControladorSupervisarVentas {

    private final PanelSupervisarVentas vista;
    private final Container contenedorPrincipal;

    public ControladorSupervisarVentas(PanelSupervisarVentas vista, Container contenedorPrincipal) {
        this.vista = vista;
        this.contenedorPrincipal = contenedorPrincipal;
        this.iniciar();
    }

    private void iniciar() {
        vista.getBtnRefrescar().addActionListener(e -> refrescar());
        vista.getBtnCancelar().addActionListener(e -> cancelarCompra());
        vista.getBtnVolver().addActionListener(e -> volver());
    }

    public void refrescar() {
        vista.poblarTabla(Sistema.ventas.toArray(new Venta[0]));
    }

    private void cancelarCompra() {
        int fila = vista.getTablaVentas().getSelectedRow();
        if (fila < 0) return;

        Venta venta = Sistema.ventas.get(fila);
        venta.anular(); // Marca como anulada en el modelo
        
        GestorPersistencia.guardarDatos(); // Guardado automático
        refrescar();
        JOptionPane.showMessageDialog(vista, "Compra cancelada.");
    }

    private void volver() {
        ((CardLayout) contenedorPrincipal.getLayout()).show(contenedorPrincipal, "admin");
    }
}