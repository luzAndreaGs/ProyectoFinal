package proyectoparcial2;

public class Vehiculo {
    private int idVehiculo;
    private String marca;
    private String modelo;
    private int año;
    private String propietario;

    public Vehiculo(int idVehiculo, String marca, String modelo, int año, String propietario) {
        this.idVehiculo = idVehiculo;
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.propietario = propietario;
    }

    // Getters y Setters
    public int getIdVehiculo() {
        return idVehiculo;
    }
    
    public void setIdVehiculo(int idVehiculo) {    
        this.idVehiculo = idVehiculo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }
    
}

