package controlador;

import vista.PanelHistorialCompras;
import modelo.Sistema;
import modelo.Venta;
import modelo.Cliente;
import java.awt.CardLayout;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

public class ControladorHistorialCompras {
    private PanelHistorialCompras vista;
    private Container contenedor;
    private Cliente clienteActual;

    public ControladorHistorialCompras(PanelHistorialCompras vista, Container contenedor) {
        this.vista = vista;
        this.contenedor = contenedor;
        this.vista.getBtnVolver().addActionListener(e -> volver());
    }

    public void setCliente(Cliente c) {
        this.clienteActual = c;
        refrescar();
    }

    private void refrescar() {
        if (clienteActual == null) return;
        List<Venta> misCompras = new ArrayList<>();
        // Buscamos en la memoria RAM todas las ventas de este DNI
        for (Venta v : Sistema.ventas) {
            if (v.getCliente().getDni().equals(clienteActual.getDni())) {
                misCompras.add(v);
            }
        }
        vista.poblarTabla(misCompras);
    }

    private void volver() {
        ((CardLayout) contenedor.getLayout()).show(contenedor, "cliente");
    }
}