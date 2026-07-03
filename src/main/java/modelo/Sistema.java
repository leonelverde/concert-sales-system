package modelo;

import java.util.ArrayList;
import java.util.List;

public class Sistema {
    // ¡AQUÍ ESTÁ EL POLIMORFISMO RECUPERADO!
    // Una sola lista que acepta tanto Clientes como Usuarios (Admins)
    public static List<Persona> personas = new ArrayList<>();
    
    // Las demás listas maestras
    public static List<Concierto> conciertos = new ArrayList<>();
    public static List<Venta> ventas = new ArrayList<>();
    public static List<Tarjeta> tarjetas = new ArrayList<>();
    
    // Método rápido para filtrar tarjetas
    public static List<Tarjeta> getTarjetasPorCliente(String dniCliente) {
        List<Tarjeta> filtradas = new ArrayList<>();
        for (Tarjeta t : tarjetas) {
            if (t.getDniCliente().equals(dniCliente)) {
                filtradas.add(t);
            }
        }
        return filtradas;
    }
}