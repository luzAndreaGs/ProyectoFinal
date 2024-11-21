package proyectoparcial2;
import java.util.Date;
import java.util.List;

public abstract class Servicio {
    protected int folioServicio;
    protected int idVehiculo;
    protected String tipoServicio;
    protected Date fecha;
    protected String estatus;
    protected String descripcion;
    protected Date fechaProximoServicio;
    protected String cliente;  // Nombre del cliente (dueño del vehículo)
    protected List<Refaccion> refacciones;  // Lista de refacciones asociadas


    // Constructor
    public Servicio(int folioServicio, int idVehiculo, String tipoServicio, Date fecha, String estatus, String descripcion, Date fechaProximoServicio) {
        this.folioServicio = folioServicio;
        this.idVehiculo = idVehiculo;
        this.tipoServicio = tipoServicio;
        this.fecha = fecha;
        this.estatus = estatus;
        this.descripcion = descripcion;
        this.fechaProximoServicio = fechaProximoServicio;
    }

    // Métodos abstractos para las subclases
    public abstract String getDescripcion();
    public abstract double calcularCosto();

    // Getters para los atributos
    public int getFolioServicio() {
        return folioServicio;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getEstatus() {
        return estatus;
    }

    public String getDescripcionServicio() {
        return descripcion;
    }

    public Date getFechaProximoServicio() {
        return fechaProximoServicio;
    }
    
    //Setters
    public void setFolioServicio(int folioServicio) {
        this.folioServicio = folioServicio;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaProximoServicio(Date fechaProximoServicio) {
        this.fechaProximoServicio = fechaProximoServicio;
    }
    
}
