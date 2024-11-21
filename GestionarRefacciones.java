package proyectoparcial2;
import java.sql.*;
import java.util.Scanner;

public class GestionarRefacciones {

    public static void mostrarMenuRefacciones(Connection conn) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        
        while (continuar) {
            System.out.println("\nGestión de Refacciones:");
            System.out.println("1. Registrar Refacción");
            System.out.println("2. Modificar Refacción");
            System.out.println("3. Eliminar Refacción");
            System.out.println("4. Ver Refacciones");
            System.out.println("5. Regresar al Menú Principal");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Limpiar buffer

            switch (opcion) {
                case 1:
                    registrarRefaccion(conn);
                    break;
                case 2:
                    modificarRefaccion(conn);
                    break;
                case 3:
                    eliminarRefaccion(conn);
                    break;
                case 4:
                    verRefacciones(conn);
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

    private static void registrarRefaccion(Connection conn) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el nombre de la refacción: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese la descripción de la refacción: ");
        String descripcion = scanner.nextLine();

        System.out.print("Ingrese el costo de la refacción: ");
        double costo = scanner.nextDouble();
        scanner.nextLine();

        // Consulta SQL para insertar una nueva refacción
        String query = "INSERT INTO Refaccion (nombre, descripcion, costo) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombre);
            stmt.setString(2, descripcion);
            stmt.setDouble(3, costo);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Refacción registrada exitosamente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void modificarRefaccion(Connection conn) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el ID de la refacción que desea modificar: ");
        int idRefaccion = scanner.nextInt();
        scanner.nextLine();

        // Verificar si la refacción existe
        if (!existeRefaccion(conn, idRefaccion)) {
            System.out.println("La refacción no existe.");
            return;
        }

        System.out.print("Ingrese el nuevo nombre de la refacción: ");
        String nuevoNombre = scanner.nextLine();

        System.out.print("Ingrese la nueva descripción de la refacción: ");
        String nuevaDescripcion = scanner.nextLine();

        System.out.print("Ingrese el nuevo costo de la refacción: ");
        double nuevoCosto = scanner.nextDouble();
        scanner.nextLine();

        // Consulta SQL para actualizar la refacción
        String query = "UPDATE Refaccion SET nombre = ?, descripcion = ?, costo = ? WHERE idRefaccion = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nuevoNombre);
            stmt.setString(2, nuevaDescripcion);
            stmt.setDouble(3, nuevoCosto);
            stmt.setInt(4, idRefaccion);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Refacción modificada exitosamente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void eliminarRefaccion(Connection conn) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el ID de la refacción que desea eliminar: ");
        int idRefaccion = scanner.nextInt();
        scanner.nextLine();

        // Verificar si la refacción existe
        if (!existeRefaccion(conn, idRefaccion)) {
            System.out.println("La refacción no existe.");
            return;
        }

        // Consulta SQL para eliminar la refacción
        String query = "DELETE FROM Refaccion WHERE idRefaccion = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idRefaccion);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Refacción eliminada exitosamente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void verRefacciones(Connection conn) {
        String query = "SELECT * FROM Refaccion";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            System.out.println("\nLista de Refacciones:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("idRefaccion") +
                                   ", Nombre: " + rs.getString("nombre") +
                                   ", Descripción: " + rs.getString("descripcion") +
                                   ", Costo: $" + rs.getDouble("costo"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private static boolean existeRefaccion(Connection conn, int idRefaccion) {
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
