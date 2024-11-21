package proyectoparcial2;
import java.sql.*;
import java.util.Scanner;

public class GestionarUsuarios {

    public static void mostrarMenuUsuarios(Connection conn) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        
        while (continuar) {
            System.out.println("\nGestión de Usuarios:");
            System.out.println("1. Registrar Usuario");
            System.out.println("2. Modificar Usuario");
            System.out.println("3. Eliminar Usuario");
            System.out.println("4. Ver Usuarios");
            System.out.println("5. Regresar al Menú Principal");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    registrarUsuario(conn);
                    break;
                case 2:
                    modificarUsuario(conn);
                    break;
                case 3:
                    eliminarUsuario(conn);
                    break;
                case 4:
                    verUsuarios(conn);
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

    private static void registrarUsuario(Connection conn) {
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

        // Ingresar los datos de Usuario
        System.out.print("Ingrese el ussername: ");
        String ussername = scanner.nextLine();

        System.out.print("Ingrese la contraseña: ");
        String contraseña = scanner.nextLine();

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

            // Insertar en Usuario
            String sqlUsuario = "INSERT INTO Usuario (idPersona, nombreUsuario, contraseña) VALUES (?, ?, ?)";
            PreparedStatement usuarioStmt = conn.prepareStatement(sqlUsuario);
            usuarioStmt.setInt(1, idPersona);
            usuarioStmt.setString(2, ussername);
            usuarioStmt.setString(3, contraseña);
            usuarioStmt.executeUpdate();

            System.out.println("Usuario registrado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void modificarUsuario(Connection conn) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el id del usuario que desea modificar: ");
        int idUsuario = scanner.nextInt();
        scanner.nextLine();

        if (!existeUsuario(conn, idUsuario)) {
            System.out.println("El usuario no existe.");
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

        // Modificar datos de Usuario
        System.out.print("Ingrese el nuevo ussername: ");
        String nuevoUssername = scanner.nextLine();
        
        System.out.print("Ingrese la nueva contraseña: ");
        String nuevaContraseña = scanner.nextLine();

        try {
            // Obtener el idPersona relacionado con el usuario
            String obtenerIdPersona = "SELECT idPersona FROM Usuario WHERE idUsuario = ?";
            PreparedStatement obtenerStmt = conn.prepareStatement(obtenerIdPersona);
            obtenerStmt.setInt(1, idUsuario);
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

            // Actualizar los datos de Usuario
            String sqlUsuario = "UPDATE Usuario SET nombreUsuario = ?, contraseña = ? WHERE idUsuario = ?";
            PreparedStatement usuarioStmt = conn.prepareStatement(sqlUsuario);
            usuarioStmt.setString(1, nuevoUssername);
            usuarioStmt.setString(2, nuevaContraseña);
            usuarioStmt.setInt(3, idUsuario);
            usuarioStmt.executeUpdate();

            System.out.println("Usuario modificado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void eliminarUsuario(Connection conn) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el id del usuario que desea eliminar: ");
        int idUsuario = scanner.nextInt();

        if (!existeUsuario(conn, idUsuario)) {
            System.out.println("El usuario no existe.");
            return;
        }

        try {
            // Obtener el idPersona relacionado con el usuario
            String obtenerIdPersona = "SELECT idPersona FROM Usuario WHERE idUsuario = ?";
            PreparedStatement obtenerStmt = conn.prepareStatement(obtenerIdPersona);
            obtenerStmt.setInt(1, idUsuario);
            ResultSet rs = obtenerStmt.executeQuery();

            int idPersona = 0;
            if (rs.next()) {
                idPersona = rs.getInt("idPersona");
            }

            // Eliminar de Usuario
            String sqlUsuario = "DELETE FROM Usuario WHERE idUsuario = ?";
            PreparedStatement usuarioStmt = conn.prepareStatement(sqlUsuario);
            usuarioStmt.setInt(1, idUsuario);
            usuarioStmt.executeUpdate();

            // Eliminar de Persona
            String sqlPersona = "DELETE FROM Persona WHERE idPersona = ?";
            PreparedStatement personaStmt = conn.prepareStatement(sqlPersona);
            personaStmt.setInt(1, idPersona);
            personaStmt.executeUpdate();

            System.out.println("Usuario eliminado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void verUsuarios(Connection conn) {
        String query = "SELECT u.idUsuario, u.nombreUsuario, p.nombre, p.apellidoPaterno, p.apellidoMaterno, p.edad, p.telefono, p.email FROM Usuario u JOIN Persona p ON u.idPersona = p.idPersona";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            System.out.println("\nLista de Usuarios:");
            while (rs.next()) {
                System.out.println("ID Usuario: " + rs.getString("idUsuario"));
                System.out.println("Usuario: " + rs.getString("nombreUsuario"));
                System.out.println("Nombre: " + rs.getString("nombre") + " " + rs.getString("apellidoPaterno") + " " + rs.getString("apellidoMaterno"));
                System.out.println("Edad: " + rs.getInt("edad"));
                System.out.println("Teléfono: " + rs.getString("telefono"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("-------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean existeUsuario(Connection conn, int idUsuario) {
        String query = "SELECT idUsuario FROM Usuario WHERE idUsuario = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idUsuario);
            
            ResultSet rs = stmt.executeQuery();
            return rs.next();  // Si el usuario existe, retorna true
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
