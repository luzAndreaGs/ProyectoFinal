package proyectoFinal;
import java.sql.*;
//import java.util.Scanner;

public class GestionarVehiculos {

//    public static void mostrarMenuVehiculos(Connection conn) {
//        Scanner scanner = new Scanner(System.in);
//        boolean continuar = true;
//        
//        while (continuar) {
//            System.out.println("\nGestión de Vehículos:");
//            System.out.println("1. Registrar Vehículo");
//            System.out.println("2. Modificar Vehículo");
//            System.out.println("3. Eliminar Vehículo");
//            System.out.println("4. Ver Vehículos");
//            System.out.println("5. Regresar al Menú Principal");
//            System.out.print("Seleccione una opción: ");
//            int opcion = scanner.nextInt();
//            scanner.nextLine();  // Limpiar buffer
//
//            switch (opcion) {
//                case 1:
//                    registrarVehiculo(conn);
//                    break;
//                case 2:
//                    modificarVehiculo(conn);
//                    break;
//                case 3:
//                    eliminarVehiculo(conn);
//                    break;
//                case 4:
//                    verVehiculos(conn);
//                    break;
//                case 5:
//                    continuar = false;
//                    break;
//                default:
// 
//                   System.out.println("Opción no válida.");
//                    break;
//            }
//        }
//    }

    protected static boolean registrarVehiculo(Connection conn, String marca, String modelo, int año, int idCliente) {
        String query = "INSERT INTO Vehiculo (marca, modelo, año, idCliente) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, marca);
            stmt.setString(2, modelo);
            stmt.setInt(3, año);
            stmt.setInt(4, idCliente);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0; // Retorna true si se insertó al menos una fila
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Retorna false si ocurre algún error
        }
    }

    protected static boolean modificarVehiculo(Connection conn, int idVehiculo, String nuevaMarca, String nuevoModelo, int nuevoAño, int nuevoIdCliente) {
        String query = "UPDATE Vehiculo SET marca = ?, modelo = ?, año = ?, idCliente = ? WHERE idVehiculo = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nuevaMarca);
            stmt.setString(2, nuevoModelo);
            stmt.setInt(3, nuevoAño);
            stmt.setInt(4, nuevoIdCliente);
            stmt.setInt(5, idVehiculo);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0; // Retorna true si se actualizó al menos una fila
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Retorna false si ocurre algún error
        }
    }

    protected static boolean eliminarVehiculo(Connection conn, int idVehiculo) {
        String query = "DELETE FROM Vehiculo WHERE idVehiculo = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idVehiculo);

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0; // Retorna true si se eliminó al menos una fila
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Retorna false si ocurre algún error
        }
    }

//    private static void verVehiculos(Connection conn) {
//        String query = "SELECT v.idVehiculo, v.marca, v.modelo, v.año, p.nombre AS propietario " +
//                       "FROM Vehiculo v " +
//                       "JOIN Cliente c ON v.idCliente = c.idCliente " +
//                       "JOIN Persona p ON c.idPersona = p.idPersona";
//
//        try (PreparedStatement stmt = conn.prepareStatement(query)) {
//            ResultSet rs = stmt.executeQuery();
//
//            System.out.println("\nLista de Vehículos:");
//            while (rs.next()) {
//                System.out.println("ID Vehículo: " + rs.getInt("idVehiculo"));
//                System.out.println("Marca: " + rs.getString("marca"));
//                System.out.println("Modelo: " + rs.getString("modelo"));
//                System.out.println("Año: " + rs.getInt("año"));
//                System.out.println("Propietario: " + rs.getString("propietario"));
//                System.out.println("-------------------------------");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    
    protected static boolean existeVehiculo(Connection conn, int idVehiculo) {
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
