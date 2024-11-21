package proyectoparcial2;
import java.sql.*;
import java.util.Scanner;

public class GestionarClientes {

    public static void mostrarMenuClientes(Connection conn) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        
        while (continuar) {
            System.out.println("\nGestión de Clientes:");
            System.out.println("1. Registrar Cliente");
            System.out.println("2. Modificar Cliente");
            System.out.println("3. Eliminar Cliente");
            System.out.println("4. Ver Clientes");
            System.out.println("5. Regresar al Menú Principal");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    registrarCliente(conn);
                    break;
                case 2:
                    modificarCliente(conn);
                    break;
                case 3:
                    eliminarCliente(conn);
                    break;
                case 4:
                    verClientes(conn);
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
    
    private static void registrarCliente(Connection conn) {
        Scanner scanner = new Scanner(System.in);

        // Ingresar los datos de Persona
        System.out.print("Ingrese el nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese el apellido paterno: ");
        String apellidoPaterno = scanner.nextLine();

        System.out.print("Ingrese el apellido materno: ");
        String apellidoMaterno = scanner.nextLine();

        System.out.print("Ingrese la edad: ");
        int edad = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Ingrese el teléfono: ");
        String telefono = scanner.nextLine();

        System.out.print("Ingrese el email: ");
        String email = scanner.nextLine();

        // Concatenar el nombre completo del cliente
        String nombreCliente = nombre + " " + apellidoPaterno + " " + apellidoMaterno;

        try {
            // Insertar en Persona
            String sqlPersona = "INSERT INTO Persona (nombre, apellidoPaterno, apellidoMaterno, edad, telefono, email) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement personaStmt = conn.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS);
            personaStmt.setString(1, nombre);
            personaStmt.setString(2, apellidoPaterno);
            personaStmt.setString(3, apellidoMaterno);
            personaStmt.setInt(4, edad);
            personaStmt.setString(5, telefono);
            personaStmt.setString(6, email);
            personaStmt.executeUpdate();

            // Obtener el ID generado de Persona
            ResultSet generatedKeys = personaStmt.getGeneratedKeys();
            int idPersona = 0;
            if (generatedKeys.next()) {
                idPersona = generatedKeys.getInt(1);
            }

            // Insertar en Cliente, usando el nombre completo concatenado
            String sqlCliente = "INSERT INTO Cliente (idPersona, nombreCliente) VALUES (?, ?)";
            PreparedStatement clienteStmt = conn.prepareStatement(sqlCliente);
            clienteStmt.setInt(1, idPersona);
            clienteStmt.setString(2, nombreCliente);  // Usar el nombre completo concatenado
            clienteStmt.executeUpdate();

            System.out.println("Cliente registrado exitosamente");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private static void modificarCliente(Connection conn) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el ID del cliente que desea modificar: ");
        int idCliente = scanner.nextInt();
        scanner.nextLine();

        if (!existeCliente(conn, idCliente)) {
            System.out.println("El cliente no existe.");
            return;
        }

        // Modificar datos de Persona
        System.out.print("Ingrese el nuevo nombre: ");
        String nuevoNombre = scanner.nextLine();

        System.out.print("Ingrese el nuevo apellido paterno: ");
        String nuevoApellidoPaterno = scanner.nextLine();

        System.out.print("Ingrese el nuevo apellido materno: ");
        String nuevoApellidoMaterno = scanner.nextLine();

        System.out.print("Ingrese la nueva edad: ");
        int nuevaEdad = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Ingrese el nuevo teléfono: ");
        String nuevoTelefono = scanner.nextLine();

        System.out.print("Ingrese el nuevo email: ");
        String nuevoEmail = scanner.nextLine();

        // Concatenar el nuevo nombre completo del cliente
        String nuevoNombreCliente = nuevoNombre + " " + nuevoApellidoPaterno + " " + nuevoApellidoMaterno;

        try {
            // Obtener el idPersona relacionado con el cliente
            String obtenerIdPersona = "SELECT idPersona FROM Cliente WHERE idCliente = ?";
            PreparedStatement obtenerStmt = conn.prepareStatement(obtenerIdPersona);
            obtenerStmt.setInt(1, idCliente);
            ResultSet rs = obtenerStmt.executeQuery();

            int idPersona = 0;
            if (rs.next()) {
                idPersona = rs.getInt("idPersona");
            }

            // Actualizar los datos de Persona
            String sqlPersona = "UPDATE Persona SET nombre = ?, apellidoPaterno = ?, apellidoMaterno = ?, edad = ?, telefono = ?, email = ? WHERE idPersona = ?";
            PreparedStatement personaStmt = conn.prepareStatement(sqlPersona);
            personaStmt.setString(1, nuevoNombre);
            personaStmt.setString(2, nuevoApellidoPaterno);
            personaStmt.setString(3, nuevoApellidoMaterno);
            personaStmt.setInt(4, nuevaEdad);
            personaStmt.setString(5, nuevoTelefono);
            personaStmt.setString(6, nuevoEmail);
            personaStmt.setInt(7, idPersona);
            personaStmt.executeUpdate();

            // Actualizar el nombre completo del cliente en la tabla Cliente
            String sqlCliente = "UPDATE Cliente SET nombreCliente = ? WHERE idCliente = ?";
            PreparedStatement clienteStmt = conn.prepareStatement(sqlCliente);
            clienteStmt.setString(1, nuevoNombreCliente);
            clienteStmt.setInt(2, idCliente);
            clienteStmt.executeUpdate();

            System.out.println("Cliente modificado exitosamente");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void eliminarCliente(Connection conn) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el ID del cliente que desea eliminar: ");
        int idCliente = scanner.nextInt();
        scanner.nextLine();

        if (!existeCliente(conn, idCliente)) {
            System.out.println("El cliente no existe.");
            return;
        }

        try {
            // Obtener el idPersona relacionado con el cliente
            String obtenerIdPersona = "SELECT idPersona FROM Cliente WHERE idCliente = ?";
            PreparedStatement obtenerStmt = conn.prepareStatement(obtenerIdPersona);
            obtenerStmt.setInt(1, idCliente);
            ResultSet rs = obtenerStmt.executeQuery();

            int idPersona = 0;
            if (rs.next()) {
                idPersona = rs.getInt("idPersona");
            }

            // Eliminar de Cliente
            String sqlCliente = "DELETE FROM Cliente WHERE idCliente = ?";
            PreparedStatement clienteStmt = conn.prepareStatement(sqlCliente);
            clienteStmt.setInt(1, idCliente);
            clienteStmt.executeUpdate();

            // Eliminar de Persona
            String sqlPersona = "DELETE FROM Persona WHERE idPersona = ?";
            PreparedStatement personaStmt = conn.prepareStatement(sqlPersona);
            personaStmt.setInt(1, idPersona);
            personaStmt.executeUpdate();

            System.out.println("Cliente eliminado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void verClientes(Connection conn) {
        String query = "SELECT c.idCliente, c.nombreCliente, p.nombre, p.apellidoPaterno, p.apellidoMaterno, p.edad, p.telefono, p.email FROM Cliente c JOIN Persona p ON c.idPersona = p.idPersona";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            System.out.println("\nLista de Clientes:");
            while (rs.next()) {
                System.out.println("ID Cliente: " + rs.getInt("idCliente"));
                System.out.println("Cliente: " + rs.getString("nombreCliente"));
                System.out.println("Edad: " + rs.getInt("edad"));
                System.out.println("Teléfono: " + rs.getString("telefono"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("-------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean existeCliente(Connection conn, int idCliente) {
        String query = "SELECT idCliente FROM Cliente WHERE idCliente = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idCliente);

            ResultSet rs = stmt.executeQuery();
            return rs.next();  // Si el cliente existe, retorna true

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}