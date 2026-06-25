
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
    
    public ControladorAdmin(PanelAdmin vistaAdmin, Container contenedorPrincipal) {
        this.vistaAdmin = vistaAdmin;
        this.contenedorPrincipal = contenedorPrincipal;
        
        this.iniciar();
    }
    
    private void iniciar() {
        this.vistaAdmin.getButtonRegistrarConcierto().addActionListener(e -> irARegistrarConcierto());
        this.vistaAdmin.getButtonSupervisarVentas().addActionListener(e -> irASupervisarVentas());
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
        // Lógica para cargar el archivo Venta.dat y llenar la tabla de ventas
        CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
        cl.show(contenedorPrincipal, "supervisarVentas");
        System.out.println("Abriendo supervisión de ventas...");
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
