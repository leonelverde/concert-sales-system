
package controlador;

import vista.PanelRegistrarConcierto;
import modelo.Concierto;
import modelo.Zona;
import java.awt.CardLayout;
import java.awt.Container;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import coleccion.ColeccionConcierto;

public class ControladorRegistrarConcierto {
    private PanelRegistrarConcierto vistaRegistroConcierto;
    private Container contenedorPrincipal;

    public ControladorRegistrarConcierto(PanelRegistrarConcierto vistaRegistroConcierto, Container contenedorPrincipal) {
        this.vistaRegistroConcierto = vistaRegistroConcierto;
        this.contenedorPrincipal = contenedorPrincipal;
        
        this.iniciar();
    }

    private void iniciar() {
        this.vistaRegistroConcierto.getButtonRegistrarConcierto().addActionListener(e -> registrarConcierto());
        this.vistaRegistroConcierto.getButtonVolver().addActionListener(e -> volverAlMenuAdmin());
    }

    private void registrarConcierto() {
        String nombre = vistaRegistroConcierto.getTxtNombreConcierto().getText().trim();
        String fechaTexto = vistaRegistroConcierto.getTxtFechaConcierto().getText().trim(); 

        if (nombre.isEmpty() || fechaTexto.isEmpty()) {
            JOptionPane.showMessageDialog(vistaRegistroConcierto, "Debe ingresar el nombre y la fecha del concierto.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Convertir la fecha
        Date fechaConcierto;
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            formatoFecha.setLenient(false); 
            fechaConcierto = formatoFecha.parse(fechaTexto);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(vistaRegistroConcierto, "Formato de fecha incorrecto. Use: dd/MM/yyyy", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Instancia del concierto vacio
        Concierto nuevoConcierto = new Concierto(nombre, fechaConcierto);

        // Extraer capacidad y precios
        try {
            int capPlat = Integer.parseInt(vistaRegistroConcierto.getTxtCapacidadPlatinum().getText().trim());
            int precioPlat = Integer.parseInt(vistaRegistroConcierto.getTxtPrecioPlatinum().getText().trim());
            nuevoConcierto.agregarZona(new Zona("Platinum", capPlat, precioPlat));

            int capVip = Integer.parseInt(vistaRegistroConcierto.getTxtCapacidadVip().getText().trim());
            int precioVip = Integer.parseInt(vistaRegistroConcierto.getTxtPrecioVip().getText().trim());
            nuevoConcierto.agregarZona(new Zona("VIP", capVip, precioVip));

            int capGen = Integer.parseInt(vistaRegistroConcierto.getTxtCapacidadGeneral().getText().trim());
            int precioGen = Integer.parseInt(vistaRegistroConcierto.getTxtPrecioGeneral().getText().trim());
            nuevoConcierto.agregarZona(new Zona("General", capGen, precioGen));

            int capTrib = Integer.parseInt(vistaRegistroConcierto.getTxtCapacidadTribuna().getText().trim());
            int precioTrib = Integer.parseInt(vistaRegistroConcierto.getTxtPrecioTribuna().getText().trim());
            nuevoConcierto.agregarZona(new Zona("Tribuna", capTrib, precioTrib));

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vistaRegistroConcierto, "Los precios y capacidades deben ser números enteros válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Guardar todo el bloque en el disco duro
        boolean guardado = ColeccionConcierto.agregarConcierto(nuevoConcierto);

        if (guardado) {
            JOptionPane.showMessageDialog(vistaRegistroConcierto, "Concierto y zonas registrados con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            volverAlMenuAdmin();
        } else {
            JOptionPane.showMessageDialog(vistaRegistroConcierto, "Error al guardar el concierto en el disco.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void volverAlMenuAdmin() {
        CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
        cl.show(contenedorPrincipal, "admin");
        limpiarCampos();
    }

    private void limpiarCampos() {
        vistaRegistroConcierto.getTxtNombreConcierto().setText("");
        vistaRegistroConcierto.getTxtFechaConcierto().setText("");
        
        vistaRegistroConcierto.getTxtCapacidadPlatinum().setText("");
        vistaRegistroConcierto.getTxtPrecioPlatinum().setText("");
        
        vistaRegistroConcierto.getTxtCapacidadVip().setText("");
        vistaRegistroConcierto.getTxtPrecioVip().setText("");
        
        vistaRegistroConcierto.getTxtCapacidadGeneral().setText("");
        vistaRegistroConcierto.getTxtPrecioGeneral().setText("");
        
        vistaRegistroConcierto.getTxtCapacidadTribuna().setText("");
        vistaRegistroConcierto.getTxtPrecioTribuna().setText("");
    }
}
