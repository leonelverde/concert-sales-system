
package controlador;

import vista.PanelRegistro;
import modelo.Cliente;
import modelo.Usuario;
import modelo.Persona;
import java.awt.CardLayout;
import java.awt.Container;
import javax.swing.JOptionPane;
import archivosInfo.ArchivoCliente;
import archivosInfo.ArchivoUsuario;

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
        // Si la Persona resulta ser un Administrador, se guarda en datos_usuario.dat
        if (p instanceof Usuario) {
            Usuario nuevoAdmin = (Usuario) p;
            Usuario[] listaActual = ArchivoUsuario.cargarUsuarios();
            Usuario[] nuevaLista = new Usuario[listaActual.length + 1];
            
            for (int i = 0; i < listaActual.length; i++) {
                nuevaLista[i] = listaActual[i];
            }
            nuevaLista[listaActual.length] = nuevoAdmin;
            
            return ArchivoUsuario.guardarUsuarios(nuevaLista, nuevaLista.length);
            
        // Si la Persona resulta ser un Cliente, se guarda en datos_cliente.dat
        } else if (p instanceof Cliente) {
            Cliente nuevoCliente = (Cliente) p;
            Cliente[] listaActual = ArchivoCliente.cargarCliente();
            Cliente[] nuevaLista = new Cliente[listaActual.length + 1];
            
            for (int i = 0; i < listaActual.length; i++) {
                nuevaLista[i] = listaActual[i];
            }
            nuevaLista[listaActual.length] = nuevoCliente;
            
            return ArchivoCliente.guardarCliente(nuevaLista, nuevaLista.length);
        }
        
        return false;
    }

    private boolean existeCorreo(String emailBuscado) {
        for (Cliente c : ArchivoCliente.cargarCliente()) {
            if (c.getEmail().equalsIgnoreCase(emailBuscado)) return true;
        }
        for (Usuario u : ArchivoUsuario.cargarUsuarios()) {
            if (u.getEmail().equalsIgnoreCase(emailBuscado)) return true;
        }
        return false;
    }
}
