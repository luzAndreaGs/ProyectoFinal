package proyectoFinal;
import java.sql.*;
//import java.util.Scanner;

public class GestionarUsuarios {

//    public static void mostrarMenuUsuarios(Connection conn) {
//        Scanner scanner = new Scanner(System.in);
//        boolean continuar = true;
//        
//        while (continuar) {
//            System.out.println("\nGestión de Usuarios:");
//            System.out.println("1. Registrar Usuario");
//            System.out.println("2. Modificar Usuario");
//            System.out.println("3. Eliminar Usuario");
//            System.out.println("4. Ver Usuarios");
//            System.out.println("5. Regresar al Menú Principal");
//            System.out.print("Seleccione una opción: ");
//            int opcion = scanner.nextInt();
//            scanner.nextLine(); 
//
//            switch (opcion) {
//                case 1:
//                    registrarUsuario(conn);
//                    break;
//                case 2:
//                    modificarUsuario(conn);
//                    break;
//                case 3:
//                    eliminarUsuario(conn);
//                    break;
//                case 4:
//                    verUsuarios(conn);
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

    protected static void registrarUsuario(Connection conn, String nombre, String apellidoPaterno, String apellidoMaterno, int edad, String telefono, String email, String ussername, String contraseña) {
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected static void modificarUsuario(Connection conn, int idUsuario, String nuevoNombre, String nuevoApellidoPaterno, String nuevoApellidoMaterno, int nuevaEdad, String nuevoTelefono, String nuevoEmail, String nuevoUssername, String nuevaContraseña) {
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected static void eliminarUsuario(Connection conn, int idUsuario) {
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

//    private static void verUsuarios(Connection conn) {
//        String query = "SELECT u.idUsuario, u.nombreUsuario, p.nombre, p.apellidoPaterno, p.apellidoMaterno, p.edad, p.telefono, p.email FROM Usuario u JOIN Persona p ON u.idPersona = p.idPersona";
//
//        try (PreparedStatement stmt = conn.prepareStatement(query)) {
//            ResultSet rs = stmt.executeQuery();
//
//            System.out.println("\nLista de Usuarios:");
//            while (rs.next()) {
//                System.out.println("ID Usuario: " + rs.getString("idUsuario"));
//                System.out.println("Usuario: " + rs.getString("nombreUsuario"));
//                System.out.println("Nombre: " + rs.getString("nombre") + " " + rs.getString("apellidoPaterno") + " " + rs.getString("apellidoMaterno"));
//                System.out.println("Edad: " + rs.getInt("edad"));
//                System.out.println("Teléfono: " + rs.getString("telefono"));
//                System.out.println("Email: " + rs.getString("email"));
//                System.out.println("-------------------------------");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    protected static boolean existeUsuario(Connection conn, int idUsuario) {
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
