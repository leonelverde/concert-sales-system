
package controlador;

import vista.PanelSupervisarVentas;
import coleccion.ColeccionConcierto;
import coleccion.ColeccionVenta;
import modelo.Concierto;
import modelo.Entrada;
import modelo.Venta;
import modelo.Zona;

import java.awt.CardLayout;
import java.awt.Container;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Controlador que permite al administrador supervisar las ventas y cancelar
 * (anular) compras de los clientes, liberando las entradas correspondientes.
 */
public class ControladorSupervisarVentas {

    private final PanelSupervisarVentas vista;
    private final Container contenedorPrincipal;
    private Venta[] ventasCargadas = new Venta[0];

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

    // Recarga la tabla de ventas desde disco (se invoca al entrar al panel).
    public void refrescar() {
        ventasCargadas = ColeccionVenta.obtenerVentas();
        vista.poblarTabla(ventasCargadas);
    }

    private void cancelarCompra() {
        int fila = vista.getTablaVentas().getSelectedRow();
        if (fila < 0 || fila >= ventasCargadas.length) {
            JOptionPane.showMessageDialog(vista, "Seleccione una venta de la tabla para cancelarla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Venta venta = ventasCargadas[fila];
        if (venta.isAnulada()) {
            JOptionPane.showMessageDialog(vista, "Esta compra ya se encuentra anulada.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(vista,
                "¿Confirma cancelar esta compra? Se liberarán las entradas vendidas.",
                "Cancelar Compra", JOptionPane.YES_NO_OPTION);
        if (confirmar != JOptionPane.YES_OPTION) {
            return;
        }

        // Liberamos las entradas en el concierto real (archivo de conciertos).
        boolean entradasLiberadas = liberarEntradasEnConcierto(venta);

        // Marcamos la venta como anulada y persistimos el archivo de ventas.
        venta.anular();
        boolean ventasGuardadas = ColeccionVenta.guardarTodas(ventasCargadas);

        if (ventasGuardadas) {
            String aviso = entradasLiberadas
                    ? "Compra cancelada. Las entradas fueron liberadas."
                    : "Compra cancelada. (No se pudieron ubicar las entradas originales para liberarlas.)";
            JOptionPane.showMessageDialog(vista, aviso, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            refrescar();
        } else {
            JOptionPane.showMessageDialog(vista, "No se pudo guardar la cancelación en disco.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Ubica el concierto y la zona reales (en el archivo de conciertos) que
     * corresponden a la venta y libera las entradas vendidas. Devuelve true si
     * se logró liberar al menos las entradas de esa zona.
     */
    private boolean liberarEntradasEnConcierto(Venta venta) {
        if (venta.getZona() == null || venta.getEntradas() == null) {
            return false;
        }

        Concierto[] conciertos = ColeccionConcierto.obtenerConciertos();
        String nombreZonaVenta = venta.getZona().getNombre();
        String nombreConcierto = venta.getNombreConcierto();

        Zona zonaReal = null;
        Concierto conciertoReal = null;

        for (Concierto c : conciertos) {
            // Si conocemos el concierto de la venta, respetamos ese nombre.
            if (nombreConcierto != null && !c.getNombre().equalsIgnoreCase(nombreConcierto)) {
                continue;
            }
            for (Zona z : c.getZonas()) {
                if (z.getNombre().equalsIgnoreCase(nombreZonaVenta)) {
                    zonaReal = z;
                    conciertoReal = c;
                    break;
                }
            }
            if (zonaReal != null) {
                break;
            }
        }

        if (zonaReal == null || conciertoReal == null) {
            return false;
        }

        List<Entrada> entradasZona = zonaReal.getEntradas();
        for (Entrada vendida : venta.getEntradas()) {
            for (Entrada real : entradasZona) {
                if (real.getNumero() == vendida.getNumero()) {
                    real.liberar();
                    break;
                }
            }
        }

        return ColeccionConcierto.actualizarConcierto(conciertoReal);
    }

    private void volver() {
        CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
        cl.show(contenedorPrincipal, "admin");
    }
}
