package proyectoFinal;
import java.sql.*;
//import java.util.Scanner;

public class GestionarRefacciones {

//    public static void mostrarMenuRefacciones(Connection conn) {
//        Scanner scanner = new Scanner(System.in);
//        boolean continuar = true;
//        
//        while (continuar) {
//            System.out.println("\nGestión de Refacciones:");
//            System.out.println("1. Registrar Refacción");
//            System.out.println("2. Modificar Refacción");
//            System.out.println("3. Eliminar Refacción");
//            System.out.println("4. Ver Refacciones");
//            System.out.println("5. Regresar al Menú Principal");
//            System.out.print("Seleccione una opción: ");
//            int opcion = scanner.nextInt();
//            scanner.nextLine();  // Limpiar buffer
//
//            switch (opcion) {
//                case 1:
//                    registrarRefaccion(conn);
//                    break;
//                case 2:
//                    modificarRefaccion(conn);
//                    break;
//                case 3:
//                    eliminarRefaccion(conn);
//                    break;
//                case 4:
//                    verRefacciones(conn);
//                    break;
//                case 5:
//                    continuar = false;
//                    break;
//                default:
//                    System.out.println("Opción no válida.");
//                    break;
//            }
//        }
//    }

    protected static boolean registrarRefaccion(Connection conn, String nombre, String descripcion, double costo) {
        String query = "INSERT INTO Refaccion (nombre, descripcion, costo) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombre);
            stmt.setString(2, descripcion);
            stmt.setDouble(3, costo);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0; // Devuelve true si se insertó correctamente
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Devuelve false si hubo un error
        }
    }

    protected static boolean modificarRefaccion(Connection conn, int idRefaccion, String nuevoNombre, String nuevaDescripcion, double nuevoCosto) {
        String query = "UPDATE Refaccion SET nombre = ?, descripcion = ?, costo = ? WHERE idRefaccion = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nuevoNombre);
            stmt.setString(2, nuevaDescripcion);
            stmt.setDouble(3, nuevoCosto);
            stmt.setInt(4, idRefaccion);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0; // Devuelve true si se actualizó correctamente
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Devuelve false si hubo un error
        }
    }

    protected static boolean eliminarRefaccion(Connection conn, int idRefaccion) {
        String query = "DELETE FROM Refaccion WHERE idRefaccion = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idRefaccion);

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0; // Devuelve true si se eliminó correctamente
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Devuelve false si hubo un error
        }
    }

//    private static void verRefacciones(Connection conn) {
//        String query = "SELECT * FROM Refaccion";
//
//        try (PreparedStatement stmt = conn.prepareStatement(query)) {
//            ResultSet rs = stmt.executeQuery();
//
//            System.out.println("\nLista de Refacciones:");
//            while (rs.next()) {
//                System.out.println("ID: " + rs.getInt("idRefaccion") +
//                                   ", Nombre: " + rs.getString("nombre") +
//                                   ", Descripción: " + rs.getString("descripcion") +
//                                   ", Costo: $" + rs.getDouble("costo"));
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    
    protected static boolean existeRefaccion(Connection conn, int idRefaccion) {
        String query = "SELECT idRefaccion FROM Refaccion WHERE idRefaccion = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idRefaccion);

            ResultSet rs = stmt.executeQuery();
            return rs.next();  // Si la refacción existe, retorna true

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
