
package modelo;

import modelo.excepciones.EntradaNoDisponibleException;

public class Entrada {
    private int numero;
    private String estado; 

    public Entrada(int numero, String estado){
        this.numero = numero;
        this.estado = estado;
    }

    public Boolean vender() throws EntradaNoDisponibleException{
        if ("Vendida".equalsIgnoreCase(this.estado)) {
            throw new EntradaNoDisponibleException("Operación denegada: La entrada numero " + numero + " ya esta vendida.");
        }
        this.estado = "Vendida";
        return true;
    }
    
    public Boolean liberar(){
        this.estado = "Disponible";
        return true;
    }
    
    public String getEstado() { return estado; }
    public int getNumero() { return numero; }
}
