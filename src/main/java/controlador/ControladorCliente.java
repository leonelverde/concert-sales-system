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
    
    private ControladorConciertosDisponibles controladorConciertos;
    private ControladorTarjetasRegistradas ctrlTarjetas;
    private ControladorAgregarTarjeta ctrlAgregarTarjeta;
    private ControladorHistorialCompras ctrlHistorial; // <-- Variable del historial agregada

    public ControladorCliente(PanelCliente vistaCliente, Container contenedorPrincipal, 
                              ControladorConciertosDisponibles controladorConciertos, 
                              ControladorTarjetasRegistradas ctrlTarjetas, 
                              ControladorAgregarTarjeta ctrlAgregar,
                              ControladorHistorialCompras ctrlHistorial) { // <-- Recibe el historial
        this.vistaCliente = vistaCliente;
        this.contenedorPrincipal = contenedorPrincipal;
        this.controladorConciertos = controladorConciertos;
        this.ctrlTarjetas = ctrlTarjetas;
        this.ctrlAgregarTarjeta = ctrlAgregar;
        this.ctrlHistorial = ctrlHistorial; // <-- Asignación
        
        this.iniciar();
    }

    private void iniciar() {
        this.vistaCliente.getButtonConciertosDisponibles().addActionListener(e -> irAConciertosDisponibles());
        this.vistaCliente.getButtonTarjetasRegistradas().addActionListener(e -> irAMisTarjetas());
        this.vistaCliente.getButtonHistorialCompras().addActionListener(e -> irAHistorialCompras());
        this.vistaCliente.getButtonCerrarSesion().addActionListener(e -> cerrarSesion());
    }

    public void setClienteSesion(Cliente cliente) {
        this.clienteLogeado = cliente;
        
        ctrlTarjetas.setClienteSesion(cliente);
        ctrlAgregarTarjeta.setClienteActual(cliente);
        ctrlHistorial.setCliente(cliente); // <-- Le avisa al historial quién inició sesión
    }

    private void irAConciertosDisponibles() {
        if (this.controladorConciertos != null) {
            this.controladorConciertos.setClienteSesion(this.clienteLogeado);
            this.controladorConciertos.refrescarCatalogo();
        }
        CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
        cl.show(contenedorPrincipal, "conciertosDisponibles");
    }
    
    private void irAMisTarjetas(){
        CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
        cl.show(contenedorPrincipal, "tarjetasRegistradas");
    }
    
    private void irAHistorialCompras() {
        this.ctrlHistorial.setCliente(this.clienteLogeado); // <-- Refresca los datos antes de mostrar la tabla
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
            this.clienteLogeado = null;
            CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
            cl.show(contenedorPrincipal, "login");
        }
    }
}