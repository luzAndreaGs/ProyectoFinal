package proyectoparcial2;
import java.util.Date;

//Clase Concreta
public class ServicioBasico extends Servicio {
    private double costo;

    public ServicioBasico(int folioServicio, int idVehiculo, String tipoServicio, Date fecha, String estatus, String descripcion, Date fechaProximoServicio, double costo) {
        super(folioServicio, idVehiculo, tipoServicio, fecha, estatus, descripcion, fechaProximoServicio);
        this.costo = costo;
    }

    @Override
    public String getDescripcion() {
        return "Servicio: " + descripcion + ", Tipo: " + tipoServicio + ", Estatus: " + estatus;
    }

    @Override
    public double calcularCosto() {
        return costo;
    }
}

