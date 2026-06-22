package controlador;

import vista.PanelLogin;
import vista.PanelRegistro;
import modelo.Persona;
import modelo.Cliente;
import modelo.Usuario;
import modelo.GestorPersistencia;
import java.awt.CardLayout;
import java.awt.Container;
import javax.swing.JOptionPane;

public class ControladorLogin {
    
    private PanelLogin vistaLogin;
    private PanelRegistro vistaRegistro;
    private GestorPersistencia modeloPersistencia;
    private Container contenedorPrincipal; 

    private ControladorCliente controladorCliente;
    
    public ControladorLogin(PanelLogin vistaLogin, PanelRegistro vistaRegistro, GestorPersistencia modeloPersistencia, Container contenedorPrincipal, ControladorCliente controladorCliente) {
        this.vistaLogin = vistaLogin;
        this.vistaRegistro = vistaRegistro;
        this.modeloPersistencia = modeloPersistencia;
        this.contenedorPrincipal = contenedorPrincipal;
        this.controladorCliente = controladorCliente;
        
        this.iniciar();
    }

    private void iniciar() {
        this.vistaLogin.getButtonLogin().addActionListener(e -> validarLogin());
        this.vistaLogin.getButtonRegistrarse().addActionListener(e -> irARegistro());
        
        this.vistaRegistro.getButtonCreateAccount().addActionListener(e -> registrarUsuario());
        this.vistaRegistro.getButtonVolver().addActionListener(e -> irALogin());
    }

    private void validarLogin() {
        String user = vistaLogin.getTxtEmail().getText();
        String pass = new String(vistaLogin.getTxtPassword().getPassword());

        Persona personaLogeada = modeloPersistencia.verificarCredenciales(user, pass);

        if (personaLogeada != null) {
            CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
            
            if (personaLogeada instanceof Usuario) { 
                cl.show(contenedorPrincipal, "admin");
            } else if (personaLogeada instanceof Cliente) {
                
                controladorCliente.setClienteSesion((Cliente) personaLogeada);
                cl.show(contenedorPrincipal, "cliente");
            }
            limpiarCamposLogin();
        } else {
            JOptionPane.showMessageDialog(vistaLogin, "Credenciales incorrectas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }

    private void registrarUsuario() {
        String nombres = vistaRegistro.getTxtNombres().getText();
        String apellidos = vistaRegistro.getTxtApellidos().getText();
        String dni = vistaRegistro.getTxtDni().getText(); 
        String user = vistaRegistro.getTxtUsuario().getText();
        String pass = new String(vistaRegistro.getTxtPassword().getPassword());
        
        // Usamos el getter temporal que devuelve ""
        String codigoAdmin = vistaRegistro.getCodigoAdmin(); 

        if (nombres.isEmpty() || apellidos.isEmpty() || dni.isEmpty() || user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(vistaRegistro, "Llene todos los campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Persona nuevaPersona;
        
        if (codigoAdmin.equals("SISTEMAS-2026")) { 
            // Usamos Boolean.TRUE para respetar el tipo envoltorio de tu clase
            nuevaPersona = new Usuario(nombres, apellidos, dni, user, pass, Boolean.TRUE); 
        } else {
            // Usamos Integer.valueOf(0) para respetar el tipo envoltorio de tu clase
            nuevaPersona = new Cliente(nombres, apellidos, dni, user, pass, Integer.valueOf(0)); 
        }

        boolean registrado = modeloPersistencia.guardarPersona(nuevaPersona);

        if (registrado) {
            JOptionPane.showMessageDialog(vistaRegistro, "Cuenta creada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCamposRegistro();
            
            CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
            if (nuevaPersona instanceof Usuario) {
                cl.show(contenedorPrincipal, "admin");
            } else {
                cl.show(contenedorPrincipal, "cliente");
            }
        } else {
            JOptionPane.showMessageDialog(vistaRegistro, "El usuario ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void irARegistro() {
        CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
        cl.show(contenedorPrincipal, "registro");
        limpiarCamposLogin();
    }
    
    private void irALogin() {
        CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
        cl.show(contenedorPrincipal, "login");
        limpiarCamposRegistro();
    }

    private void limpiarCamposLogin() {
        vistaLogin.getTxtEmail().setText("");
        vistaLogin.getTxtPassword().setText("");
    }

    private void limpiarCamposRegistro() {
        vistaRegistro.getTxtNombres().setText("");
        vistaRegistro.getTxtApellidos().setText("");
        vistaRegistro.getTxtDni().setText(""); 
        vistaRegistro.getTxtUsuario().setText("");
        vistaRegistro.getTxtPassword().setText("");
    }
}