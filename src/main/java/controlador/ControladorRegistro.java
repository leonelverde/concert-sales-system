
package controlador;

import vista.PanelRegistro;
import modelo.Cliente;
import modelo.Usuario;
import modelo.Persona;
import java.awt.CardLayout;
import java.awt.Container;
import javax.swing.JOptionPane;
import coleccion.ColeccionCliente;
import coleccion.ColeccionUsuario;

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

        if (nombres.isEmpty() || apellidos.isEmpty() || dni.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(vistaRegistro, "Llene todos los campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (existeCorreo(email)) {
            JOptionPane.showMessageDialog(vistaRegistro, "El correo ingresado ya se encuentra registrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Persona nuevaPersona;
        
        if (email.endsWith("@admin.com")) {
            // Si el correo termina en @admin.com, se instancia como Usuario (Administrador)
            nuevaPersona = new Usuario(nombres, apellidos, dni, email, pass, Boolean.TRUE); 
        } else {
            // Para cualquier otro correo (gmail, outlook, etc.), se instancia como Cliente
            nuevaPersona = new Cliente(nombres, apellidos, dni, email, pass, Integer.valueOf(0)); 
        }
        
        boolean registrado = guardarNuevaPersona(nuevaPersona);

        if (registrado) {
            JOptionPane.showMessageDialog(vistaRegistro, "Cuenta creada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCamposRegistro();
            
            CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
            
            if (nuevaPersona instanceof Usuario) {
                cl.show(contenedorPrincipal, "admin");
            } else {
                controladorCliente.setClienteSesion((Cliente) nuevaPersona); 
                cl.show(contenedorPrincipal, "cliente");
            }
        } else {
            JOptionPane.showMessageDialog(vistaRegistro, "Error al intentar registrar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
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

    private boolean guardarNuevaPersona(Persona p) {
        
        if (p instanceof Usuario) {
            return ColeccionUsuario.agregarUsuario((Usuario) p);
        } else if (p instanceof Cliente) {
            return ColeccionCliente.agregarCliente((Cliente) p);
        }
        return false;
    }

    private boolean existeCorreo(String emailBuscado) {
        for (Cliente c : ColeccionCliente.obtenerClientes()) {
            if (c.getEmail().equalsIgnoreCase(emailBuscado)) return true;
        }
        for (Usuario u : ColeccionUsuario.obtenerUsuarios()) {
            if (u.getEmail().equalsIgnoreCase(emailBuscado)) return true;
        }
        return false;
    }
}
