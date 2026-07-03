package controlador;

import vista.PanelRegistro;
import modelo.Cliente;
import modelo.Usuario;
import modelo.Persona;
import modelo.Sistema; // Nuestra memoria RAM
import modelo.GestorPersistencia; // Nuestro guardado único
import java.awt.CardLayout;
import java.awt.Container;
import java.util.Random;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class ControladorRegistro {
    private PanelRegistro vistaRegistro;
    private Container contenedorPrincipal; 
    private ControladorCliente controladorCliente;

    public ControladorRegistro(PanelRegistro vistaRegistro, Container contenedorPrincipal, ControladorCliente controladorCliente) {
        this.vistaRegistro = vistaRegistro;
        this.contenedorPrincipal = contenedorPrincipal;
        this.controladorCliente = controladorCliente;
        
        this.iniciar();
    }

    private void iniciar() {
        this.vistaRegistro.getButtonCreateAccount().addActionListener(e -> registrarUsuario());
        this.vistaRegistro.getButtonVolver().addActionListener(e -> irALogin());
    }

    private void registrarUsuario() {
        String nombres = vistaRegistro.getTxtNombres().getText().trim();
        String apellidos = vistaRegistro.getTxtApellidos().getText().trim();
        String dni = vistaRegistro.getTxtDni().getText().trim(); 
        String email = vistaRegistro.getTxtEmail().getText().trim().toLowerCase();
        String pass = new String(vistaRegistro.getTxtPassword().getPassword());
        String edadTexto = vistaRegistro.getTxtEdad().getText().trim(); // <-- Leer edad

        if (nombres.isEmpty() || apellidos.isEmpty() || dni.isEmpty() || email.isEmpty() || pass.isEmpty() || edadTexto.isEmpty()) {
            JOptionPane.showMessageDialog(vistaRegistro, "Llene todos los campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!dni.matches("\\d{8}")) {
            JOptionPane.showMessageDialog(vistaRegistro, "El DNI debe tener exactamente 8 dígitos numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-z]+$";
        if (!Pattern.matches(emailRegex, email)) {
            JOptionPane.showMessageDialog(vistaRegistro, "El formato del correo es inválido. Intente con un correo real.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (existeCorreo(email)) {
            JOptionPane.showMessageDialog(vistaRegistro, "El correo ingresado ya se encuentra registrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // --- MANEJO DE EXCEPCIONES: Validación de Mayoría de Edad ---
        int edadParsed = 0;
        try {
            edadParsed = Integer.parseInt(edadTexto);
            if (edadParsed < 18) {
                // Lanzamos nuestra excepción personalizada
                throw new modelo.excepciones.EdadInvalidaException("Operación rechazada: Debe ser mayor de 18 años para registrarse.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vistaRegistro, "Por favor, ingrese una edad numérica válida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (modelo.excepciones.EdadInvalidaException ex) {
            // Capturamos el error y bloqueamos el flujo
            JOptionPane.showMessageDialog(vistaRegistro, ex.getMessage(), "Registro Denegado", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Si pasa la validación de edad, procedemos con el código de 4 dígitos
        String codigoGenerado = String.format("%04d", new java.util.Random().nextInt(10000));
        String inputCodigo = JOptionPane.showInputDialog(vistaRegistro, 
                "Simulación de Correo:\nSe ha enviado el código [" + codigoGenerado + "] a " + email + "\n\nIngrese el código de 4 dígitos para confirmar su cuenta:", 
                "Verificación en dos pasos", JOptionPane.INFORMATION_MESSAGE);

        if (inputCodigo == null || !inputCodigo.equals(codigoGenerado)) {
            JOptionPane.showMessageDialog(vistaRegistro, "Código incorrecto o registro cancelado.", "Registro Fallido", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Persona nuevaPersona;
        
        if (email.endsWith("@admin.com")) {
            nuevaPersona = new Usuario(nombres, apellidos, dni, email, pass, Boolean.TRUE, edadParsed); // <-- Pasa edad
        } else {
            nuevaPersona = new Cliente(nombres, apellidos, dni, email, pass, Integer.valueOf(0), edadParsed); // <-- Pasa edad
        }
        
        Sistema.personas.add(nuevaPersona);
        GestorPersistencia.guardarDatos();

        JOptionPane.showMessageDialog(vistaRegistro, "Cuenta creada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        
        // Limpiamos la caja de edad también
        vistaRegistro.getTxtEdad().setText("");
        limpiarCamposRegistro();
        
        CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
        if (nuevaPersona instanceof Usuario) {
            cl.show(contenedorPrincipal, "admin");
        } else {
            controladorCliente.setClienteSesion((Cliente) nuevaPersona); 
            cl.show(contenedorPrincipal, "cliente");
        }
    }

    private void irALogin() {
        CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
        cl.show(contenedorPrincipal, "login");
        limpiarCamposRegistro();
    }

    private void limpiarCamposRegistro() {
        vistaRegistro.getTxtNombres().setText("");
        vistaRegistro.getTxtApellidos().setText("");
        vistaRegistro.getTxtDni().setText(""); 
        vistaRegistro.getTxtPassword().setText("");
        vistaRegistro.getTxtEmail().setText(""); 
    }

    private boolean existeCorreo(String emailBuscado) {
        // Polimorfismo: Buscamos en la única lista de la memoria
        for (Persona p : Sistema.personas) {
            if (p.getEmail().equalsIgnoreCase(emailBuscado)) return true;
        }
        return false;
    }
}