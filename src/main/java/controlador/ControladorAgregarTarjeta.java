package controlador;

import vista.PanelAgregarTarjeta;
import modelo.Sistema; // Memoria RAM
import modelo.GestorPersistencia; // Guardado rápido
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

        try {
            // --- VALIDACIÓN DE DÍGITOS (Exigencia del Profesor) ---
            // Permite entre 14 y 16 dígitos para soportar Diners (14) y Amex (15)
            if (!num.matches("\\d{14,16}")) {
                throw new modelo.excepciones.TarjetaInvalidaException("El número de tarjeta debe contener entre 14 y 16 dígitos.");
            }

            // Validaciones Estrictas de Formato
            if (!fecha.matches("(0[1-9]|1[0-2])/\\d{2}")) {
                throw new IllegalArgumentException("La fecha debe tener el formato MM/YY (ej: 12/28).");
            }
            if (!cvv.matches("\\d{3,4}")) {
                throw new IllegalArgumentException("El CVV debe tener 3 o 4 dígitos.");
            }
            
            modelo.EmisorTarjeta emisor = modelo.EmisorTarjeta.detectar(num);
            if (emisor == modelo.EmisorTarjeta.DESCONOCIDO) {
                throw new modelo.excepciones.TarjetaInvalidaException("Número inválido. Solo aceptamos Visa, Mastercard, Diners o Amex reales.");
            }

            Tarjeta nueva = new Tarjeta(clienteActual.getDni(), num, nom, fecha, cvv);
            Sistema.tarjetas.add(nueva);
            GestorPersistencia.guardarDatos();

            JOptionPane.showMessageDialog(vistaAgregar, "Tarjeta " + emisor.getEtiqueta() + " guardada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiar();
            this.controladorTarjetas.refrescarCatalogo(); 
            volver();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(vistaAgregar, ex.getMessage(), "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (modelo.excepciones.TarjetaInvalidaException ex) {
            JOptionPane.showMessageDialog(vistaAgregar, ex.getMessage(), "Tarjeta Rechazada", JOptionPane.ERROR_MESSAGE);
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