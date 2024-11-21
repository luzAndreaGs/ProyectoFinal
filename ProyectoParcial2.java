package proyectoparcial2;
import java.sql.*;
import java.util.*;

public class ProyectoParcial2 {

    public static void main(String[] args) {
            // Intentar obtener la conexión
            Connection conn = ConnectDB.getConnection();
            System.out.println("Iniciando prueba de conexión a la base de datos...");

        try {

            if (conn != null && !conn.isClosed()) {
                System.out.println("Conexión exitosa a la base de datos.");
            } else {
                System.out.println("Error: No se pudo establecer la conexión.");
            }

        } catch (SQLException e) {
            System.out.println("Error durante la conexión a la base de datos: " + e.getMessage());
            e.printStackTrace();
        }

        boolean continuar = true;
        Scanner scanner = new Scanner(System.in);

        while (continuar) {
            // Menú inicial: Iniciar sesión o salir
            System.out.println("\nBienvenido al Sistema de Mantenimiento de Automóviles");
            System.out.println("1. Iniciar Sesión");
            System.out.println("2. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Limpiar buffer
            
            switch (opcion) {
                case 1:
                    // Intentar iniciar sesión
                    if (Autenticar.iniciarSesion(conn)) {
                        // Si la autenticación es exitosa, mostrar el menú principal
                        MenuOpciones.mostrarMenu(conn);
                    }
                    break;
                case 2:
                    // Salir del sistema
                    continuar = false;
                    System.out.println("Saliendo del sistema...");
                    ConnectDB.closeConnection();  // Cierra la conexión a la base de datos
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }

        scanner.close();  // Cerrar el scanner cuando el programa termina
    }
    
}
