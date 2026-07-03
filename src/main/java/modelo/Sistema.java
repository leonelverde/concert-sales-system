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
    
    // --- CARGA DE FÁBRICA (Seed Data) ---
    public static void cargarDatosIniciales() {
        if (personas.isEmpty()) {
            // 1. Administrador técnico
            personas.add(new Usuario("System", "Admin", "99999999", "admin@admin.com", "admin123", true, 35));
            
            // 2. Cliente de prueba
            personas.add(new Cliente("Valeria", "Rojas", "77788899", "valeria.rojas@gmail.com", "pass123", 100, 22));
            
            // 3. Cliente adicional para probar historial
            personas.add(new Cliente("Bruno", "Linares", "12345678", "bruno.linares@outlook.com", "pass123", 50, 28));
        }

        if (conciertos.isEmpty()) {
            // Concierto 1: Rock Internacional
            Concierto c1 = new Concierto("Midnight Echo Tour", new java.util.Date());
            c1.agregarZona(new Zona("VIP", 50, 450));
            c1.agregarZona(new Zona("General", 300, 120));
            c1.agregarZona(new Zona("Tribuna", 200, 80));
            conciertos.add(c1);

            // Concierto 2: Festival Urbano
            Concierto c2 = new Concierto("Urban Flow Lima", new java.util.Date());
            c2.agregarZona(new Zona("Platinum", 80, 600));
            c2.agregarZona(new Zona("VIP", 150, 300));
            c2.agregarZona(new Zona("General", 500, 100));
            conciertos.add(c2);
            
            // Concierto 3: Jazz en el Parque
            Concierto c3 = new Concierto("Sunset Jazz Sessions", new java.util.Date());
            c3.agregarZona(new Zona("Preferencial", 40, 250));
            c3.agregarZona(new Zona("General", 200, 90));
            conciertos.add(c3);
        }
    }
}