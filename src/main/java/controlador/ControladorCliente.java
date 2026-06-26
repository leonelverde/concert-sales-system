package controlador;

import vista.PanelCliente;
import modelo.Cliente;
import java.awt.CardLayout;
import java.awt.Container;
import javax.swing.JOptionPane;

public class ControladorCliente {
    
    private PanelCliente vistaCliente;
    private Container contenedorPrincipal;
    private Cliente clienteLogeado;
    
    // Inyectamos el controlador de la tabla para poder darle la orden de actualizarse
    private ControladorConciertosDisponibles controladorConciertos;
    
    private ControladorTarjetasRegistradas ctrlTarjetas;
    private ControladorAgregarTarjeta ctrlAgregarTarjeta;
    
    public ControladorCliente(PanelCliente vistaCliente, Container contenedorPrincipal, ControladorConciertosDisponibles controladorConciertos, ControladorTarjetasRegistradas ctrlTarjetas, ControladorAgregarTarjeta ctrlAgregar) {
        this.vistaCliente = vistaCliente;
        this.contenedorPrincipal = contenedorPrincipal;
        this.controladorConciertos = controladorConciertos;
        this.ctrlTarjetas = ctrlTarjetas;
        this.ctrlAgregarTarjeta = ctrlAgregar;
        
        this.iniciar();
    }

    private void iniciar() {
        this.vistaCliente.getButtonConciertosDisponibles().addActionListener(e -> irAConciertosDisponibles());
        //this.vistaCliente.getButtonComprarEntradas().addActionListener(e -> irAComprarEntradas());
        this.vistaCliente.getButtonTarjetasRegistradas().addActionListener(e -> irAMisTarjetas());
        this.vistaCliente.getButtonHistorialCompras().addActionListener(e -> irAHistorialCompras());
        this.vistaCliente.getButtonCerrarSesion().addActionListener(e -> cerrarSesion());
    }

    public void setClienteSesion(Cliente cliente) {
        this.clienteLogeado = cliente;
        
        ctrlTarjetas.setClienteSesion(cliente);
        ctrlAgregarTarjeta.setClienteActual(cliente);
    }

    private void irAConciertosDisponibles() {
        //Refrescar la lectura del archivo .dat 
        if (this.controladorConciertos != null) {
            
            this.controladorConciertos.setClienteSesion(this.clienteLogeado);
            this.controladorConciertos.refrescarCatalogo();
        }
        
        // Mostrar la pantalla ya actualizada
        CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
        cl.show(contenedorPrincipal, "conciertosDisponibles");
    }

    private void irAComprarEntradas() {
        CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
        cl.show(contenedorPrincipal, "comprarEntradas");
    }
    
    private void irAMisTarjetas(){
        CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
        cl.show(contenedorPrincipal, "tarjetasRegistradas");
        
    }
    private void irAHistorialCompras() {
        // Aquí eventualmente le pasarás this.clienteLogeado al controlador de historial 
        // para que filtre en Venta.dat solo las compras hechas por este DNI/Email.
        CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
        cl.show(contenedorPrincipal, "historialCompras");
    }

    private void cerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(
            vistaCliente, 
            "¿Está seguro que desea cerrar sesión?", 
            "Cerrar Sesión", 
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            this.clienteLogeado = null; // Destruimos la sesión por seguridad
            
            CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
            cl.show(contenedorPrincipal, "login");
        }
    }
}