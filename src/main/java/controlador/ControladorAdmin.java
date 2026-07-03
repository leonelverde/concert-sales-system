
package controlador;

import java.awt.CardLayout;
import java.awt.Container;
import javax.swing.JOptionPane;
import modelo.Usuario;
import vista.PanelAdmin;

public class ControladorAdmin {
    private PanelAdmin vistaAdmin;
    private Container contenedorPrincipal;
    private Usuario adminLogeado;

    private ControladorSupervisarVentas ctrlSupervisarVentas;
    private ControladorGestionZonas ctrlGestionZonas;

    public ControladorAdmin(PanelAdmin vistaAdmin, Container contenedorPrincipal,
            ControladorSupervisarVentas ctrlSupervisarVentas,
            ControladorGestionZonas ctrlGestionZonas) {
        this.vistaAdmin = vistaAdmin;
        this.contenedorPrincipal = contenedorPrincipal;
        this.ctrlSupervisarVentas = ctrlSupervisarVentas;
        this.ctrlGestionZonas = ctrlGestionZonas;

        this.iniciar();
    }

    private void iniciar() {
        this.vistaAdmin.getButtonRegistrarConcierto().addActionListener(e -> irARegistrarConcierto());
        this.vistaAdmin.getButtonSupervisarVentas().addActionListener(e -> irASupervisarVentas());
        this.vistaAdmin.getButtonGestionarZonas().addActionListener(e -> irAGestionarZonas());
        this.vistaAdmin.getButtonCerrarSesion().addActionListener(e -> cerrarSesion());
    }

    public void setAdminSesion(Usuario admin) {
        this.adminLogeado = admin;
    }

    private void irARegistrarConcierto() {
        CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
        cl.show(contenedorPrincipal, "registrarConcierto");
        System.out.println("Abriendo...");
    }

    private void irASupervisarVentas() {
        // Cargamos las ventas desde el archivo y mostramos el panel de supervisión.
        if (ctrlSupervisarVentas != null) {
            ctrlSupervisarVentas.refrescar();
        }
        CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
        cl.show(contenedorPrincipal, "supervisarVentas");
        System.out.println("Abriendo supervisión de ventas...");
    }

    private void irAGestionarZonas() {
        // Cargamos los conciertos disponibles y mostramos el panel de gestión.
        if (ctrlGestionZonas != null) {
            ctrlGestionZonas.refrescar();
        }
        CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
        cl.show(contenedorPrincipal, "gestionZonas");
        System.out.println("Abriendo gestión de zonas...");
    }

    private void cerrarSesion() {
        int confirmacion = JOptionPane.showConfirmDialog(vistaAdmin, "¿Seguro que desea cerrar sesión?", "Cerrar Sesión", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            this.adminLogeado = null;

            CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
            cl.show(contenedorPrincipal, "login");
        }
    }
}
