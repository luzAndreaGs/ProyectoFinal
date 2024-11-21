package proyectoparcial2;

public class Cliente extends Persona {

    private int idCliente;  // Atributo específico de Propietario

    // Constructor
    public Cliente(int idPersona, String nombre, String apellidoPaterno, String apellidoMaterno, int edad, String telefono, String email, int idCliente) {
        super(idPersona, nombre, apellidoPaterno, apellidoMaterno, edad, telefono, email);
        this.idCliente = idCliente;
    }

    // Getters y Setters
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public String toString() {
        return super.toString() + ", ID Propietario: " + idCliente;
    }
}
