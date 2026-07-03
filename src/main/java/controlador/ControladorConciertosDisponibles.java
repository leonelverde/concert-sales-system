package controlador;

import vista.PanelConciertosDisponibles;
import modelo.Sistema; // Memoria RAM
import modelo.Concierto;
import java.awt.CardLayout;
import java.awt.Container;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import modelo.Cliente;

public class ControladorConciertosDisponibles {
    
    private PanelConciertosDisponibles vistaConciertos;
    private Container contenedorPrincipal;
    private Cliente clienteSesion;
    private ControladorComprarEntradas ctrlCompra;

    public ControladorConciertosDisponibles(PanelConciertosDisponibles vistaConciertos, Container contenedorPrincipal, ControladorComprarEntradas ctrlCompra) {
        this.vistaConciertos = vistaConciertos;
        this.contenedorPrincipal = contenedorPrincipal;
        this.ctrlCompra = ctrlCompra;
        this.iniciar();
    }

    private void iniciar() {
        this.vistaConciertos.getButtonRefrescar().addActionListener(e -> refrescarCatalogo());
        this.vistaConciertos.getButtonVolver().addActionListener(e -> volverAlMenuCliente());
        this.vistaConciertos.getButtonComprarEntrada().addActionListener(e -> irAComprarEntrada());
    }
    
    public void setClienteSesion(Cliente cliente) {
        this.clienteSesion = cliente;
    }
    
    public void refrescarCatalogo() {
        // Le pasamos la lista de la RAM convertida a arreglo para que la tabla la entienda
        Concierto[] conciertosDelDisco = Sistema.conciertos.toArray(new Concierto[0]);
        vistaConciertos.poblarTabla(conciertosDelDisco);
    }
    
    private void irAComprarEntrada() {
        JTable tabla = vistaConciertos.getTablaConciertos();
        int filaSeleccionada = tabla.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vistaConciertos, "Por favor, haga clic sobre un concierto de la tabla para seleccionarlo.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nombreBanda = tabla.getValueAt(filaSeleccionada, 0).toString();
        Concierto conciertoElegido = null;

        // Buscamos en la RAM
        for (Concierto c : Sistema.conciertos) {
            if (c.getNombre().equalsIgnoreCase(nombreBanda)) {
                conciertoElegido = c;
                break;
            }
        }

        this.ctrlCompra.iniciarSesionCompra(this.clienteSesion, conciertoElegido);

        CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
        cl.show(contenedorPrincipal, "comprarEntradas");
    }
    
    private void volverAlMenuCliente() {
        CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
        cl.show(contenedorPrincipal, "cliente");
    }
}