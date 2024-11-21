package proyectoparcial2;

public abstract class ServicioDecorator extends Servicio {
    protected Servicio servicioDecorado;

    public ServicioDecorator(Servicio servicioDecorado) {
        super(servicioDecorado.getFolioServicio(),
              servicioDecorado.getIdVehiculo(),
              servicioDecorado.getTipoServicio(),
              servicioDecorado.getFecha(),
              servicioDecorado.getEstatus(),
              servicioDecorado.getDescripcionServicio(),
              servicioDecorado.getFechaProximoServicio());
        this.servicioDecorado = servicioDecorado;
    }

    public abstract String getDescripcion();

    public abstract double calcularCosto();
}

