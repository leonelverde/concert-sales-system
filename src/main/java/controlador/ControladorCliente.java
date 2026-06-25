
package controlador;

import vista.PanelCliente;
import vista.PanelComprarEntradas;
import modelo.Cliente;
import modelo.Tarjeta;
import modelo.Zona;
import modelo.excepciones.CapacidadExcedidaException;
import modelo.excepciones.EntradaLimiteException;
import modelo.excepciones.EntradaNoDisponibleException;

import javax.swing.JOptionPane;
import java.awt.CardLayout;
import java.awt.Container;

public class ControladorCliente {
    
    private PanelCliente vistaCliente;
    private PanelComprarEntradas vistaCompra;
    private Cliente clienteSesion; // El usuario real que inició sesión
    private Container contenedorPrincipal;

    // El constructor ahora recibe ambas vistas
    public ControladorCliente(PanelCliente vistaCliente, PanelComprarEntradas vistaCompra, Container contenedorPrincipal) {
        this.vistaCliente = vistaCliente;
        this.vistaCompra = vistaCompra;
        this.contenedorPrincipal = contenedorPrincipal;
        
        this.inicializarEventos();
    }

    // MÉTODO NUEVO: Para recibir al cliente cuando inicie sesión
    public void setClienteSesion(Cliente clienteLogeado) {
        this.clienteSesion = clienteLogeado;
    }

    private void inicializarEventos() {
        this.vistaCliente.getButtonComprarEntradas().addActionListener(e -> irAPantallaCompra());
        this.vistaCliente.getButtonCerrarSesion().addActionListener(e -> cerrarSesion());
        
        this.vistaCompra.getBtnComprar().addActionListener(e -> procesarCompra());
        
        this.vistaCompra.getBtnVolver().addActionListener(e -> volverAlMenuCliente());
    }

    private void irAPantallaCompra() {
        CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
        cl.show(contenedorPrincipal, "comprar"); // Mostramos el nuevo panel
    }

    private void procesarCompra() {
        try {
            // 1. Extraemos los datos escritos por el usuario en los JTextFields
            String nombreZona = (String) vistaCompra.getCboZonas().getSelectedItem();
            Integer cantidadEntradas = (Integer) vistaCompra.getCboCantidadEntradas().getSelectedItem();
            int numeroTarjeta = Integer.parseInt(vistaCompra.getTxtTarjeta().getText());

            // 2. Instanciamos los objetos necesarios (Más adelante esto lo buscará el GestorConciertos)
            // Por ahora, creamos una zona temporal con capacidad de 50 y precio 100
            Zona zonaSeleccionada = new Zona(nombreZona, 50, 100); 
            
            // Creamos una tarjeta usando los datos ingresados
            Tarjeta tarjetaUsada = new Tarjeta(numeroTarjeta, clienteSesion.getNombres(), "12/25", 123);

            // 3. Preparamos al cliente con los datos obligatorios según tu lógica
            clienteSesion.setZonaSeleccionada(zonaSeleccionada);
            clienteSesion.setTarjetaSeleccionada(tarjetaUsada);

            // 4. ¡La compra real! (Esto activará tus excepciones si se rompen las reglas)
            boolean exito = clienteSesion.comprar(cantidadEntradas);

            if (exito) {
                JOptionPane.showMessageDialog(vistaCompra, "¡Compra realizada con éxito!\nSe generó la venta en tu historial.", "Transacción Aprobada", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormularioCompra();
                
                // Volvemos automáticamente al menú del cliente tras una compra exitosa
                CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
                cl.show(contenedorPrincipal, "cliente");
            }

        // --- MANEJO DE EXCEPCIONES ---
        } catch (NumberFormatException ex) {
            // Si el usuario escribe letras en lugar de números en la cantidad o tarjeta
            JOptionPane.showMessageDialog(vistaCompra, "La cantidad y la tarjeta deben ser valores numéricos.", "Error de formato", JOptionPane.ERROR_MESSAGE);
            
        } catch (EntradaLimiteException | CapacidadExcedidaException | EntradaNoDisponibleException ex) {
            // ¡Tus excepciones personalizadas en acción! Mostramos tu mensaje de error en la pantalla
            JOptionPane.showMessageDialog(vistaCompra, ex.getMessage(), "Operación denegada", JOptionPane.WARNING_MESSAGE);
            
        } catch (Exception ex) {
            // Cualquier otro error imprevisto
            JOptionPane.showMessageDialog(vistaCompra, "Ocurrió un error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarEstadisticas() {
        //JOptionPane.showMessageDialog(vistaCompra, "Módulo de estadísticas en construcción.");
    }

    private void limpiarFormularioCompra() {
        vistaCompra.getCboCantidadEntradas().setSelectedIndex(0);
        vistaCompra.getTxtTarjeta().setText("");
        vistaCompra.getCboZonas().setSelectedIndex(0);
    }

    private void cerrarSesion() {
        // Borramos la sesión de la memoria
        this.clienteSesion = null;
        
        CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
        cl.show(contenedorPrincipal, "login");
    }
    
    private void volverAlMenuCliente() {
    limpiarFormularioCompra();

    CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
    cl.show(contenedorPrincipal, "cliente");
}
}
