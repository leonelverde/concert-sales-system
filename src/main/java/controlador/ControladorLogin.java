package controlador;

import vista.PanelLogin;
import modelo.Persona;
import modelo.Cliente;
import modelo.Usuario;
import modelo.Sistema; // Nuestra nueva memoria RAM
import java.awt.CardLayout;
import java.awt.Container;
import javax.swing.JOptionPane;

public class ControladorLogin {
    
    private PanelLogin vistaLogin;
    private Container contenedorPrincipal; 
    private ControladorCliente controladorCliente;
    private ControladorAdmin controladorAdmin;
    
    public ControladorLogin(PanelLogin vistaLogin, Container contenedorPrincipal, ControladorCliente controladorCliente, ControladorAdmin controladorAdmin) {
        this.vistaLogin = vistaLogin;
        this.contenedorPrincipal = contenedorPrincipal;
        this.controladorCliente = controladorCliente;
        this.controladorAdmin = controladorAdmin;
        
        this.iniciar();
    }

    private void iniciar() {
        this.vistaLogin.getButtonLogin().addActionListener(e -> validarLogin());
        this.vistaLogin.getButtonRegistrarse().addActionListener(e -> irARegistro());
    }
    
    public Persona verificarCredenciales(String emailStr, String passStr) {
        // ¡MAGIA DEL POLIMORFISMO AQUÍ!
        // Recorremos una sola lista maestra que contiene a todos.
        for(Persona p : Sistema.personas){
            if(p.getEmail().equalsIgnoreCase(emailStr) && p.getContraseña().equals(passStr)){
                return p;
            }
        }
        return null; // Si termina el bucle y no encontró nada, credenciales inválidas
    }
       
    private void validarLogin() {
        String email = vistaLogin.getTxtEmail().getText().trim();
        String pass = new String(vistaLogin.getTxtPassword().getPassword());

        Persona personaLogeada = this.verificarCredenciales(email, pass);

        if (personaLogeada != null) {
            CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
            
            // Usamos instanceof para saber a qué menú enviarlo dependiendo de su clase hija
            if (personaLogeada instanceof Usuario) { 
                controladorAdmin.setAdminSesion((Usuario) personaLogeada);
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

    private void irARegistro() {
        CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
        cl.show(contenedorPrincipal, "registro");
        limpiarCamposLogin();
    }

    private void limpiarCamposLogin() {
        vistaLogin.getTxtEmail().setText("");
        vistaLogin.getTxtPassword().setText("");
    }
}