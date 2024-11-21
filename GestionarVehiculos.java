package proyectoparcial2;
import java.sql.*;
import java.util.Scanner;

public class GestionarVehiculos {

    public static void mostrarMenuVehiculos(Connection conn) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        
        while (continuar) {
            System.out.println("\nGestión de Vehículos:");
            System.out.println("1. Registrar Vehículo");
            System.out.println("2. Modificar Vehículo");
            System.out.println("3. Eliminar Vehículo");
            System.out.println("4. Ver Vehículos");
            System.out.println("5. Regresar al Menú Principal");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Limpiar buffer

            switch (opcion) {
                case 1:
                    registrarVehiculo(conn);
                    break;
                case 2:
                    modificarVehiculo(conn);
                    break;
                case 3:
                    eliminarVehiculo(conn);
                    break;
                case 4:
                    verVehiculos(conn);
                    break;
                case 5:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }

    private static void registrarVehiculo(Connection conn) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese la marca del vehículo: ");
        String marca = scanner.nextLine();

        System.out.print("Ingrese el modelo del vehículo: ");
        String modelo = scanner.nextLine();

        System.out.print("Ingrese el año del vehículo: ");
        int año = scanner.nextInt();
        scanner.nextLine();

        // Seleccionar el cliente (propietario)
        System.out.print("Ingrese el ID del cliente (propietario): ");
        int idCliente = scanner.nextInt();
        scanner.nextLine();

        try {
            // Insertar el vehículo en la tabla Vehiculo
            String sqlVehiculo = "INSERT INTO Vehiculo (marca, modelo, año, idCliente) VALUES (?, ?, ?, ?)";
            PreparedStatement vehiculoStmt = conn.prepareStatement(sqlVehiculo);
            vehiculoStmt.setString(1, marca);
            vehiculoStmt.setString(2, modelo);
            vehiculoStmt.setInt(3, año);
            vehiculoStmt.setInt(4, idCliente);
            vehiculoStmt.executeUpdate();

            System.out.println("Vehículo registrado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void modificarVehiculo(Connection conn) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el ID del vehículo que desea modificar: ");
        int idVehiculo = scanner.nextInt();
        scanner.nextLine();  // Limpiar buffer

        System.out.print("Ingrese la nueva marca: ");
        String nuevaMarca = scanner.nextLine();

        System.out.print("Ingrese el nuevo modelo: ");
        String nuevoModelo = scanner.nextLine();

        System.out.print("Ingrese el nuevo año: ");
        int nuevoAño = scanner.nextInt();
        scanner.nextLine();  // Limpiar buffer

        System.out.print("Ingrese el nuevo ID del cliente (propietario): ");
        int nuevoIdCliente = scanner.nextInt();
        scanner.nextLine();

        try {
            String sqlVehiculo = "UPDATE Vehiculo SET marca = ?, modelo = ?, año = ?, idCliente = ? WHERE idVehiculo = ?";
            PreparedStatement vehiculoStmt = conn.prepareStatement(sqlVehiculo);
            vehiculoStmt.setString(1, nuevaMarca);
            vehiculoStmt.setString(2, nuevoModelo);
            vehiculoStmt.setInt(3, nuevoAño);
            vehiculoStmt.setInt(4, nuevoIdCliente);
            vehiculoStmt.setInt(5, idVehiculo);
            vehiculoStmt.executeUpdate();

            System.out.println("Vehículo modificado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void eliminarVehiculo(Connection conn) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el ID del vehículo que desea eliminar: ");
        int idVehiculo = scanner.nextInt();
        scanner.nextLine();  // Limpiar buffer

        try {
            String sqlVehiculo = "DELETE FROM Vehiculo WHERE idVehiculo = ?";
            PreparedStatement vehiculoStmt = conn.prepareStatement(sqlVehiculo);
            vehiculoStmt.setInt(1, idVehiculo);
            vehiculoStmt.executeUpdate();

            System.out.println("Vehículo eliminado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void verVehiculos(Connection conn) {
        String query = "SELECT v.idVehiculo, v.marca, v.modelo, v.año, p.nombre AS propietario " +
                       "FROM Vehiculo v " +
                       "JOIN Cliente c ON v.idCliente = c.idCliente " +
                       "JOIN Persona p ON c.idPersona = p.idPersona";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            System.out.println("\nLista de Vehículos:");
            while (rs.next()) {
                System.out.println("ID Vehículo: " + rs.getInt("idVehiculo"));
                System.out.println("Marca: " + rs.getString("marca"));
                System.out.println("Modelo: " + rs.getString("modelo"));
                System.out.println("Año: " + rs.getInt("año"));
                System.out.println("Propietario: " + rs.getString("propietario"));
                System.out.println("-------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static boolean existeVehiculo(Connection conn, int idVehiculo) {
        String query = "SELECT idVehiculo FROM Vehiculo WHERE idVehiculo = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idVehiculo);

            ResultSet rs = stmt.executeQuery();
            return rs.next();  // Si el vehículo existe, retorna true

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
