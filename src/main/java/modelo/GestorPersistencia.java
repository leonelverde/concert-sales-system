
package modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class GestorPersistencia implements Serializable{
    
    private static final String RUTA_ARCHIVO = "datos_usuarios.dat";

    // Guarda una nueva persona (Cliente o Usuario) en el archivo
    public boolean guardarPersona(Persona nuevaPersona) {
        List<Persona> lista = leerTodasLasPersonas();
        
        // Verificar que el usuario no exista ya
        for (Persona p : lista) {
            if (p.getUsuario().equals(nuevaPersona.getUsuario())) {
                return false; // El nombre de usuario ya está tomado
            }
        }
        
        lista.add(nuevaPersona);
        
        // Escribir la lista actualizada en el disco
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_ARCHIVO))) {
            oos.writeObject(lista);
            return true;
        } catch (IOException e) {
            System.out.println("Error al guardar en disco: " + e.getMessage());
            return false;
        }
    }

    // Busca si las credenciales coinciden y devuelve el objeto original
    public Persona verificarCredenciales(String usuarioStr, String passStr) {
        List<Persona> lista = leerTodasLasPersonas();
        
        for (Persona p : lista) {
            if (p.getUsuario().equals(usuarioStr) && p.getContraseña().equals(passStr)) {
                return p; // Retorna el Cliente o Usuario encontrado
            }
        }
        return null; // Credenciales inválidas
    }

    // Método auxiliar para leer el archivo completo
    private List<Persona> leerTodasLasPersonas() {
        List<Persona> lista = new ArrayList<>();
        File archivo = new File(RUTA_ARCHIVO);
        
        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
                lista = (List<Persona>) ois.readObject();
            } catch (Exception e) {
                System.out.println("Error al leer el archivo: " + e.getMessage());
            }
        }
        return lista;
    }
    
    // Herramienta temporal para depurar en consola
    public void imprimirDatosEnConsola() {
        List<Persona> lista = leerTodasLasPersonas();
        System.out.println("\n=== DEPURACIÓN DE ARCHIVO .DAT ===");
        System.out.println("Usuarios registrados en disco: " + lista.size());
        
        for (Persona p : lista) {
            System.out.println("Clase instanciada: " + p.getClass().getSimpleName());
            // Usamos corchetes [ ] para detectar si se coló algún espacio en blanco al inicio o final
            System.out.println("Usuario guardado: [" + p.getUsuario() + "]");
            System.out.println("Contraseña guardada: [" + p.getContraseña() + "]");
            System.out.println("----------------------------------");
        }
    }
}
