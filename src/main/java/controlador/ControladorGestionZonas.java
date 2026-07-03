
package controlador;

import vista.PanelGestionZonas;
import coleccion.ColeccionConcierto;
import modelo.Concierto;
import modelo.EmisorTarjeta;
import modelo.Zona;

import java.awt.CardLayout;
import java.awt.Container;
import javax.swing.JOptionPane;

/**
 * Controlador que permite al administrador agregar y modificar las zonas de un
 * concierto (nombre y precio) asi como configurar los descuentos por emisor de
 * tarjeta de cada concierto.
 */
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

        // Al seleccionar una fila cargamos sus datos en los campos de edicion.
        vista.getTablaZonas().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarFilaSeleccionada();
            }
        });
    }

    // Recarga la lista de conciertos (se invoca al entrar al panel).
    public void refrescar() {
        conciertoActual = null;
        vista.poblarConciertos(ColeccionConcierto.obtenerConciertos());
        vista.poblarZonas(new java.util.ArrayList<>());
        vista.limpiarCamposZona();
    }

    private void cargarConcierto() {
        Object seleccion = vista.getCboConciertos().getSelectedItem();
        if (seleccion == null) {
            JOptionPane.showMessageDialog(vista, "No hay conciertos registrados.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nombre = seleccion.toString();
        conciertoActual = buscarConcierto(nombre);
        if (conciertoActual == null) {
            JOptionPane.showMessageDialog(vista, "No se pudo cargar el concierto seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        vista.poblarZonas(conciertoActual.getZonas());
        vista.cargarDescuentos(conciertoActual);
        vista.limpiarCamposZona();
    }

    private void cargarFilaSeleccionada() {
        int fila = vista.getTablaZonas().getSelectedRow();
        if (conciertoActual == null || fila < 0 || fila >= conciertoActual.getZonas().size()) {
            return;
        }
        Zona zona = conciertoActual.getZonas().get(fila);
        vista.getTxtNombreZona().setText(zona.getNombre());
        vista.getTxtCapacidadZona().setText(String.valueOf(zona.getCapacidad()));
        vista.getTxtPrecioZona().setText(String.valueOf(zona.getPrecio()));
    }

    private void agregarZona() {
        if (conciertoActual == null) {
            JOptionPane.showMessageDialog(vista, "Primero cargue un concierto.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nombre = vista.getTxtNombreZona().getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese el nombre de la nueva zona.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        for (Zona z : conciertoActual.getZonas()) {
            if (z.getNombre().equalsIgnoreCase(nombre)) {
                JOptionPane.showMessageDialog(vista, "Ya existe una zona con ese nombre en este concierto.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        int capacidad;
        int precio;
        try {
            capacidad = Integer.parseInt(vista.getTxtCapacidadZona().getText().trim());
            precio = Integer.parseInt(vista.getTxtPrecioZona().getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Capacidad y precio deben ser números enteros válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (capacidad <= 0 || precio < 0) {
            JOptionPane.showMessageDialog(vista, "La capacidad debe ser mayor a 0 y el precio no puede ser negativo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        conciertoActual.agregarZona(new Zona(nombre, capacidad, precio));
        persistirYRecargar("Zona '" + nombre + "' agregada correctamente.");
    }

    private void guardarCambiosZona() {
        if (conciertoActual == null) {
            JOptionPane.showMessageDialog(vista, "Primero cargue un concierto.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int fila = vista.getTablaZonas().getSelectedRow();
        if (fila < 0 || fila >= conciertoActual.getZonas().size()) {
            JOptionPane.showMessageDialog(vista, "Seleccione una zona de la tabla para modificarla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nombre = vista.getTxtNombreZona().getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El nombre de la zona no puede quedar vacío.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int precio;
        try {
            precio = Integer.parseInt(vista.getTxtPrecioZona().getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "El precio debe ser un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (precio < 0) {
            JOptionPane.showMessageDialog(vista, "El precio no puede ser negativo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Evitar duplicar el nombre con otra zona distinta.
        for (int i = 0; i < conciertoActual.getZonas().size(); i++) {
            if (i != fila && conciertoActual.getZonas().get(i).getNombre().equalsIgnoreCase(nombre)) {
                JOptionPane.showMessageDialog(vista, "Ya existe otra zona con ese nombre en este concierto.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        Zona zona = conciertoActual.getZonas().get(fila);
        zona.setNombre(nombre);
        zona.setPrecio(precio);
        persistirYRecargar("Zona actualizada correctamente.");
    }

    private void guardarDescuentos() {
        if (conciertoActual == null) {
            JOptionPane.showMessageDialog(vista, "Primero cargue un concierto.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            conciertoActual.setDescuento(EmisorTarjeta.VISA, parsearPorcentaje(vista.getTxtDescVisa().getText()));
            conciertoActual.setDescuento(EmisorTarjeta.MASTERCARD, parsearPorcentaje(vista.getTxtDescMastercard().getText()));
            conciertoActual.setDescuento(EmisorTarjeta.DINERS, parsearPorcentaje(vista.getTxtDescDiners().getText()));
            conciertoActual.setDescuento(EmisorTarjeta.AMEX, parsearPorcentaje(vista.getTxtDescAmex().getText()));
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(vista, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean ok = ColeccionConcierto.actualizarConcierto(conciertoActual);
        if (ok) {
            JOptionPane.showMessageDialog(vista, "Descuentos guardados para el concierto.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(vista, "No se pudieron guardar los descuentos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double parsearPorcentaje(String texto) {
        double valor;
        try {
            valor = Double.parseDouble(texto.trim());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Los descuentos deben ser números (ej: 5 o 10.5).");
        }
        if (valor < 0 || valor > 100) {
            throw new IllegalArgumentException("Los descuentos deben estar entre 0 y 100.");
        }
        return valor;
    }

    private void persistirYRecargar(String mensajeExito) {
        boolean ok = ColeccionConcierto.actualizarConcierto(conciertoActual);
        if (ok) {
            // Releer el concierto desde disco para reflejar el estado persistido.
            conciertoActual = buscarConcierto(conciertoActual.getNombre());
            vista.poblarZonas(conciertoActual.getZonas());
            vista.limpiarCamposZona();
            JOptionPane.showMessageDialog(vista, mensajeExito, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(vista, "No se pudieron guardar los cambios en disco.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Concierto buscarConcierto(String nombre) {
        for (Concierto c : ColeccionConcierto.obtenerConciertos()) {
            if (c.getNombre().equalsIgnoreCase(nombre)) {
                return c;
            }
        }
        return null;
    }

    private void volver() {
        CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
        cl.show(contenedorPrincipal, "admin");
    }
}
