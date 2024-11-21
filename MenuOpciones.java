package proyectoparcial2;
import java.util.*;
import java.sql.*;

public class MenuOpciones {

    public static void mostrarMenu(Connection conn) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        
        while (continuar) {
            System.out.println("\nMenú de opciones:");
            System.out.println("1. Gestión de Usuarios");
            System.out.println("2. Gestión de Clientes");
            System.out.println("3. Gestión de Vehículos");
            System.out.println("4. Gestión de Servicios");
            System.out.println("5. Gestión de Refacciones");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Limpiar buffer

            switch (opcion) {
                case 1:
                    GestionarUsuarios.mostrarMenuUsuarios(conn);
                    break;
                case 2:
                    GestionarClientes.mostrarMenuClientes(conn);
                    break;
                case 3:
                    GestionarVehiculos.mostrarMenuVehiculos(conn);
                    break;
                case 4:
                    GestionarServicios.mostrarMenuServicios(conn);
                    break;
                case 5:
                    GestionarRefacciones.mostrarMenuRefacciones(conn);
                    break;
                case 6:
                    continuar = false;
                    Autenticar.cerrarSesion();
                    ConnectDB.closeConnection();  // Cierra la conexión a la base de datos
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    break;
            }
        }
    }
}


