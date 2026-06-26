
package controlador;

import vista.PanelAgregarTarjeta;
import coleccion.ColeccionTarjeta;
import modelo.Tarjeta;
import java.awt.CardLayout;
import java.awt.Container;
import javax.swing.JOptionPane;
import modelo.Cliente;

public class ControladorAgregarTarjeta {
    
    private PanelAgregarTarjeta vistaAgregar;
    private Container contenedorPrincipal;
    private ControladorTarjetasRegistradas controladorTarjetas; 
    private Cliente clienteActual;

    public ControladorAgregarTarjeta(PanelAgregarTarjeta vistaAgregar, Container contenedorPrincipal, ControladorTarjetasRegistradas controladorTarjetas) {
        this.vistaAgregar = vistaAgregar;
        this.contenedorPrincipal = contenedorPrincipal;
        this.controladorTarjetas = controladorTarjetas;
        
        this.iniciar();
    }

    private void iniciar() {
        this.vistaAgregar.getButtonVolver().addActionListener(e -> volver());
        this.vistaAgregar.getButtonGuardarTarjeta().addActionListener(e -> guardar());
    }
    
    public void setClienteActual(Cliente cliente) {
        this.clienteActual = cliente;
    }
    
    private void guardar() {
        String num = vistaAgregar.getTxtNumeroTarjeta().getText().trim();
        String nom = vistaAgregar.getTxtNombreTitular().getText().trim();
        String fecha = vistaAgregar.getTxtFechaVencimiento().getText().trim();
        String cvv = vistaAgregar.getTxtCvv().getText().trim();

        if (num.isEmpty() || nom.isEmpty() || fecha.isEmpty() || cvv.isEmpty()) {
            JOptionPane.showMessageDialog(vistaAgregar, "Por favor, complete todos los campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Verificamos mediante una expresión regular básica que sean 16 números exactos
        if (!num.matches("\\d{16}")) {
            JOptionPane.showMessageDialog(vistaAgregar, "El número de tarjeta debe contener exactamente 16 dígitos numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Tarjeta nueva = new Tarjeta(clienteActual.getDni(), num, nom, fecha, cvv);
        
        boolean guardado = ColeccionTarjeta.agregarTarjeta(nueva);

        if (guardado) {
            JOptionPane.showMessageDialog(vistaAgregar, "Tarjeta guardada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiar();
            this.controladorTarjetas.refrescarCatalogo(); // Forzamos actualización de la tabla padre
            volver();
        } else {
            JOptionPane.showMessageDialog(vistaAgregar, "Error al intentar registrar la tarjeta en el disco.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void volver() {
        limpiar();
        CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
        cl.show(contenedorPrincipal, "tarjetasRegistradas");
    }

    private void limpiar() {
        vistaAgregar.getTxtNumeroTarjeta().setText("");
        vistaAgregar.getTxtNombreTitular().setText("");
        vistaAgregar.getTxtFechaVencimiento().setText("");
        vistaAgregar.getTxtCvv().setText("");
    }
}
