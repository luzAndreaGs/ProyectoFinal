package proyectoparcial2;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Autenticar {
    
    private static String usuarioSesion = null; // Variable global para manejar la sesión

    public static boolean iniciarSesion(Connection conn) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese su nombre de usuario: ");
        String nombreUsuario = scanner.nextLine();
        System.out.print("Ingrese su contraseña: ");
        String contraseña = scanner.nextLine();

        // Consulta SQL para validar al usuario respetando mayúsculas y caracteres especiales
        String query = "SELECT nombreUsuario FROM Usuario WHERE BINARY nombreUsuario = ? AND BINARY contraseña = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombreUsuario);
            stmt.setString(2, contraseña);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                usuarioSesion = nombreUsuario;  // Guarda el nombre de usuario en la variable de sesión
                System.out.println("Inicio de sesión exitoso. Bienvenido " + nombreUsuario + "!");
                return true;
            } else {
                System.out.println("Error: Nombre de usuario o contraseña incorrectos.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static void cerrarSesion() {
        usuarioSesion = null; // Destruye la variable global de sesión
        System.out.println("Sesión cerrada.");
    }

    public static String getUsuarioSesion() {
        return usuarioSesion;
    }
}
