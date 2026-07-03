package modelo;

import modelo.excepciones.EntradaLimiteException;
import modelo.excepciones.CapacidadExcedidaException;
import modelo.excepciones.EntradaNoDisponibleException;
import java.io.Serializable;

public abstract class Persona implements Serializable{
    private String nombres;
    private String apellidos;
    private String dni;
    protected String email;
    private String contraseña;
    private int edad; // <-- Nuevo campo
    
    public Persona(String nombres, String apellidos, String dni, String email, String contraseña, int edad){ 
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.email = email;
        this.contraseña = contraseña;
        this.edad = edad; // <-- Asignación
    }
    
    public String getNombres(){return nombres;}
    public String getApellidos(){return apellidos;}
    public String getDni(){return dni;}
    public String getEmail() { return email; } 
    public String getContraseña() { return contraseña; }
    public int getEdad() { return edad; } // <-- Nuevo Getter
    
    public Boolean registrarTarjeta(){return true;}
    public Boolean eliminarTarjeta(){return true;}
    public Boolean anularVenta(){return true;}
    
    public abstract Boolean comprar(int cantidad) throws EntradaLimiteException, CapacidadExcedidaException, EntradaNoDisponibleException;
}