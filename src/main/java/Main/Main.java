
package Main;

import vista.Vista;  

public class Main {

    public static void main(String[] args) {
     // 1. Cargar los datos desde el disco duro a la RAM (GestorPersistencia / Sistema)

        // 2. Arrancar la interfaz visual principal
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Instancia tu ventana principal y la hace visible
                new Vista().setVisible(true); 
            }
        });   
    }
}
