package modelo;

import modelo.excepciones.EntradaLimiteException;
import modelo.excepciones.CapacidadExcedidaException;
import modelo.excepciones.EntradaNoDisponibleException;

public abstract class Persona {
    private String nombres;
    private String apellidos;
    private String dni;
    protected String usuario;
    private String contraseña;
    
    public Persona(String nombres, String apellidos, String dni, String usuario, String contraseña){ 
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.usuario = usuario;
        this.contraseña = contraseña;
    }
    
    public Boolean registrarTarjeta(){return true;}
    public Boolean eliminarTarjeta(){return true;}
    public Boolean anularVenta(){return true;}
    
    public String getNombres(){return nombres;}
    public String getApellidos(){return apellidos;}
    public String getDni(){return dni;}
    
    public abstract Boolean comprar(int cantidad) throws EntradaLimiteException, CapacidadExcedidaException, EntradaNoDisponibleException;
    
    public String getUsuario() { return usuario; } 
    public String getContraseña() { return contraseña; }
}
