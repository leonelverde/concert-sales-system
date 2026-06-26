
package controlador;

import vista.PanelComprarEntradas;
import coleccion.ColeccionConcierto;
import coleccion.ColeccionTarjeta;
import coleccion.ColeccionVenta;
import modelo.*;
import modelo.excepciones.EntradaLimiteException;
import modelo.excepciones.EntradaNoDisponibleException;

import java.awt.CardLayout;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ControladorComprarEntradas {
    
    private PanelComprarEntradas vistaCompra;
    private Container contenedorPrincipal;
    private Cliente clienteComprador;
    private Concierto conciertoSeleccionado;

    public ControladorComprarEntradas(PanelComprarEntradas vistaCompra, Container contenedorPrincipal) {
        this.vistaCompra = vistaCompra;
        this.contenedorPrincipal = contenedorPrincipal;
        this.iniciar();
    }

    private void iniciar() {
        this.vistaCompra.getBtnVolver().addActionListener(e -> volver());
        this.vistaCompra.getBtnComprar().addActionListener(e -> procesarCompra());
    }

    public void iniciarSesionCompra(Cliente cliente, Concierto concierto) {
        this.clienteComprador = cliente;
        this.conciertoSeleccionado = concierto;
        this.vistaCompra.prepararParaCompra(concierto);
    }

    private void procesarCompra() {
        if (conciertoSeleccionado == null || clienteComprador == null) {
            JOptionPane.showMessageDialog(vistaCompra, "Error de sesión. Vuelva a seleccionar el concierto.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nombreZona = vistaCompra.getCboZonas().getSelectedItem().toString();
        int cantidadPedida = Integer.parseInt(vistaCompra.getCboCantidadEntradas().getSelectedItem().toString());
        String numTarjetaInput = vistaCompra.getTxtTarjeta().getText().trim();

        if (cantidadPedida <= 0) {
            JOptionPane.showMessageDialog(vistaCompra, "Seleccione al menos 1 entrada.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Tarjeta tarjetaValida = null;
        for (Tarjeta t : ColeccionTarjeta.obtenerTarjetas()) {
            if (t.getNumero().equals(numTarjetaInput)) {
                tarjetaValida = t;
                break;
            }
        }

        if (tarjetaValida == null) {
            JOptionPane.showMessageDialog(vistaCompra, "La tarjeta ingresada no coincide con ninguna de sus tarjetas registradas.", "Pago Rechazado", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Zona zonaReal = null;
        for (Zona z : conciertoSeleccionado.getZonas()) {
            if (z.getNombre().equalsIgnoreCase(nombreZona)) {
                zonaReal = z;
                break;
            }
        }

        List<Entrada> boletosParaVender = new ArrayList<>();
        for (Entrada ent : zonaReal.getEntradas()) {
            if ("Disponible".equalsIgnoreCase(ent.getEstado())) {
                boletosParaVender.add(ent);
                if (boletosParaVender.size() == cantidadPedida) break;
            }
        }

        if (boletosParaVender.size() < cantidadPedida) {
            JOptionPane.showMessageDialog(vistaCompra, "Aforo agotado: Solo quedan " + boletosParaVender.size() + " entradas disponibles en " + nombreZona + ".", "Sold Out", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Venta nuevaVenta = new Venta(clienteComprador, zonaReal, tarjetaValida);
            
            // Cambiando estado de boletos a "Vendida"
            for (Entrada e : boletosParaVender) {
                e.vender(); 
            }

            nuevaVenta.agregarEntradas(boletosParaVender);

            // Guardamos en ambos archivos .dat simultáneamente
            ColeccionVenta.agregarVenta(nuevaVenta);
            ColeccionConcierto.actualizarConcierto(conciertoSeleccionado);

            JOptionPane.showMessageDialog(vistaCompra, "¡Compra Exitosa!\nSe cargaron S/ " + nuevaVenta.getMonto() + " a su tarjeta terminada en " + numTarjetaInput.substring(12), "Ticket Emitido", JOptionPane.INFORMATION_MESSAGE);
            
            vistaCompra.getTxtTarjeta().setText("");
            volver();

        } catch (EntradaNoDisponibleException | EntradaLimiteException ex) {
            JOptionPane.showMessageDialog(vistaCompra, ex.getMessage(), "Transacción abortada", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void volver() {
        CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
        cl.show(contenedorPrincipal, "cliente");
    }
}
