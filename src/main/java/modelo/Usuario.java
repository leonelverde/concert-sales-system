
package modelo;

import modelo.excepciones.EntradaLimiteException;
import java.io.Serializable;

public class Usuario extends Persona implements Serializable{
    private Boolean estado;
    
    public Usuario(String nombres, String apellidos, String dni, String usuario, String contraseña, Boolean estado){
        super(nombres, apellidos, dni, usuario, contraseña);
        this.estado = estado;
    }
    
    public void registrarZonas(){}
    
    @Override
    public Boolean comprar(int cantidad) throws EntradaLimiteException {
        if (cantidad > 4) {
            throw new EntradaLimiteException("Error en Usuario: Límite excedido. El maximo permitido es 4 entradas.");
        }
        if (cantidad <= 0) {
            throw new EntradaLimiteException("Error en Usuario: Cantidad invalida.");
        }
        return true;
    }
}
