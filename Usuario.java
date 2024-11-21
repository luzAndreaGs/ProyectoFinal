package proyectoparcial2;

public class Usuario extends Persona {

    private int idUsuario;
    private String usuario;
    private String contraseña;

    // Constructor
    public Usuario(int idPersona, String nombre, String apellidoPaterno, String apellidoMaterno, int edad, String telefono, String email, int idUsuario, String usuario, String contraseña) {
        super(idPersona, nombre, apellidoPaterno, apellidoMaterno, edad, telefono, email);
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    // Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setidUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    @Override
    public String toString() {
        return super.toString() + ", ID Administrador: " + idUsuario + ", Usuario: " + usuario;
    }
}


