package modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorPersistencia {

    private static final String ARCHIVO_PERSONAS = "datos_personas.dat";
    private static final String ARCHIVO_CONCIERTOS = "datos_conciertos.dat";
    private static final String ARCHIVO_VENTAS = "datos_ventas.dat";
    private static final String ARCHIVO_TARJETAS = "datos_tarjetas.dat";

    // --- GUARDAR TODO AL SALIR ---
    public static void guardarDatos() {
        serializar(ARCHIVO_PERSONAS, Sistema.personas);
        serializar(ARCHIVO_CONCIERTOS, Sistema.conciertos);
        serializar(ARCHIVO_VENTAS, Sistema.ventas);
        serializar(ARCHIVO_TARJETAS, Sistema.tarjetas);
    }

    // --- CARGAR TODO AL INICIAR ---
    @SuppressWarnings("unchecked")
    public static void cargarDatos() {
        Sistema.personas = (List<Persona>) deserializar(ARCHIVO_PERSONAS, new ArrayList<Persona>());
        Sistema.conciertos = (List<Concierto>) deserializar(ARCHIVO_CONCIERTOS, new ArrayList<Concierto>());
        Sistema.ventas = (List<Venta>) deserializar(ARCHIVO_VENTAS, new ArrayList<Venta>());
        Sistema.tarjetas = (List<Tarjeta>) deserializar(ARCHIVO_TARJETAS, new ArrayList<Tarjeta>());
    }

    // Métodos genéricos ocultos para no repetir código
    private static void serializar(String ruta, Object objeto) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta))) {
            oos.writeObject(objeto);
        } catch (IOException e) {
            System.out.println("Error al guardar " + ruta + ": " + e.getMessage());
        }
    }

    private static Object deserializar(String ruta, Object valorPorDefecto) {
        File archivo = new File(ruta);
        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
                return ois.readObject();
            } catch (Exception e) {
                System.out.println("Error al leer " + ruta + ": " + e.getMessage());
            }
        }
        return valorPorDefecto; // Evita que la pantalla se ponga en blanco si no hay archivo
    }
}