
package controlador;

import vista.PanelConciertosDisponibles;
import coleccion.ColeccionConcierto;
import modelo.Concierto;
import java.awt.CardLayout;
import java.awt.Container;

public class ControladorConciertosDisponibles {
    
    private PanelConciertosDisponibles vistaConciertos;
    private Container contenedorPrincipal;

    public ControladorConciertosDisponibles(PanelConciertosDisponibles vistaConciertos, Container contenedorPrincipal) {
        this.vistaConciertos = vistaConciertos;
        this.contenedorPrincipal = contenedorPrincipal;
        
        this.iniciar();
    }

    private void iniciar() {
        this.vistaConciertos.getButtonRefrescar().addActionListener(e -> refrescarCatalogo());
        this.vistaConciertos.getButtonVolver().addActionListener(e -> volverAlMenuCliente());
    }

    public void refrescarCatalogo() {
        Concierto[] conciertosDelDisco = ColeccionConcierto.obtenerConciertos();
        vistaConciertos.poblarTabla(conciertosDelDisco);
    }

    private void volverAlMenuCliente() {
        CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
        cl.show(contenedorPrincipal, "cliente");
    }
}
