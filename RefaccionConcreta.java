package proyectoparcial2;

public class RefaccionConcreta extends ServicioDecorator {
    private String nombreRefaccion;
    private double costoRefaccion;
    private int cantidad;

    public RefaccionConcreta(Servicio servicioDecorado, String nombreRefaccion, double costoRefaccion, int cantidad) {
        super(servicioDecorado);
        this.nombreRefaccion = nombreRefaccion;
        this.costoRefaccion = costoRefaccion;
        this.cantidad = cantidad;
    }

    @Override
    public String getDescripcion() {
        return servicioDecorado.getDescripcion() + ", Refacción: " + nombreRefaccion + " (x " + cantidad + ")";
    }

    @Override
    public double calcularCosto() {
        return servicioDecorado.calcularCosto() + (costoRefaccion * cantidad);
    }
}

