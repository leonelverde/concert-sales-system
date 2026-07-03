package controlador;

import vista.PanelGestionZonas;
import modelo.Sistema; // Memoria RAM
import modelo.GestorPersistencia; // Guardado único
import modelo.Concierto;
import modelo.EmisorTarjeta;
import modelo.Zona;
import java.awt.CardLayout;
import java.awt.Container;
import javax.swing.JOptionPane;

public class ControladorGestionZonas {

    private final PanelGestionZonas vista;
    private final Container contenedorPrincipal;
    private Concierto conciertoActual;

    public ControladorGestionZonas(PanelGestionZonas vista, Container contenedorPrincipal) {
        this.vista = vista;
        this.contenedorPrincipal = contenedorPrincipal;
        this.iniciar();
    }

    private void iniciar() {
        vista.getBtnCargar().addActionListener(e -> cargarConcierto());
        vista.getBtnAgregarZona().addActionListener(e -> agregarZona());
        vista.getBtnGuardarCambios().addActionListener(e -> guardarCambiosZona());
        vista.getBtnGuardarDescuentos().addActionListener(e -> guardarDescuentos());
        vista.getBtnVolver().addActionListener(e -> volver());
    }

    public void refrescar() {
        conciertoActual = null;
        vista.poblarConciertos(Sistema.conciertos.toArray(new Concierto[0]));
        vista.poblarZonas(new java.util.ArrayList<>());
        vista.limpiarCamposZona();
    }

    private void cargarConcierto() {
        Object seleccion = vista.getCboConciertos().getSelectedItem();
        if (seleccion == null) return;
        
        conciertoActual = buscarConcierto(seleccion.toString());
        if (conciertoActual != null) {
            vista.poblarZonas(conciertoActual.getZonas());
            vista.cargarDescuentos(conciertoActual);
        }
    }

    private void agregarZona() {
        if (conciertoActual == null) return;
        String nombre = vista.getTxtNombreZona().getText().trim();
        try {
            int cap = Integer.parseInt(vista.getTxtCapacidadZona().getText().trim());
            int precio = Integer.parseInt(vista.getTxtPrecioZona().getText().trim());
            conciertoActual.agregarZona(new Zona(nombre, cap, precio));
            persistirYRecargar("Zona agregada.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Datos numéricos inválidos.");
        }
    }

    private void guardarCambiosZona() {
        int fila = vista.getTablaZonas().getSelectedRow();
        if (fila < 0) return;
        
        Zona zona = conciertoActual.getZonas().get(fila);
        zona.setNombre(vista.getTxtNombreZona().getText());
        zona.setPrecio(Integer.parseInt(vista.getTxtPrecioZona().getText()));
        persistirYRecargar("Cambios guardados.");
    }

    private void guardarDescuentos() {
        // Validación de descuentos usando el nuevo emisor
        conciertoActual.setDescuento(EmisorTarjeta.VISA, Double.parseDouble(vista.getTxtDescVisa().getText()));
        conciertoActual.setDescuento(EmisorTarjeta.MASTERCARD, Double.parseDouble(vista.getTxtDescMastercard().getText()));
        conciertoActual.setDescuento(EmisorTarjeta.DINERS, Double.parseDouble(vista.getTxtDescDiners().getText()));
        conciertoActual.setDescuento(EmisorTarjeta.AMEX, Double.parseDouble(vista.getTxtDescAmex().getText()));
        
        GestorPersistencia.guardarDatos();
        JOptionPane.showMessageDialog(vista, "Descuentos guardados.");
    }

    private void persistirYRecargar(String msg) {
        GestorPersistencia.guardarDatos();
        vista.poblarZonas(conciertoActual.getZonas());
        JOptionPane.showMessageDialog(vista, msg);
    }

    private Concierto buscarConcierto(String nombre) {
        for (Concierto c : Sistema.conciertos) {
            if (c.getNombre().equalsIgnoreCase(nombre)) return c;
        }
        return null;
    }

    private void volver() {
        CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
        cl.show(contenedorPrincipal, "admin");
    }
}