package proyectoparcial2;
import java.sql.*;
import java.util.Scanner;
//import com.lowagie.text.Document;
//import com.lowagie.text.DocumentException;
//import com.lowagie.text.Paragraph;
//import com.lowagie.text.pdf.PdfPTable;
//import com.lowagie.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;


public class GestionarServicios {

    public static void mostrarMenuServicios(Connection conn) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        
        while (continuar) {
            System.out.println("\nGestión de Servicios:");
            System.out.println("1. Registrar Servicio");
            System.out.println("2. Modificar Servicio");
            System.out.println("3. Eliminar Servicio");
            System.out.println("4. Ver Servicios");
            System.out.println("5. Ver servicios por cliente");
            System.out.println("6. Generar Comprobante");
            System.out.println("7. Regresar al Menú Principal");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    registrarServicio(conn);
                    break;
                case 2:
                    modificarServicio(conn);
                    break;
                case 3:
                    eliminarServicio(conn);
                    break;
                case 4:
                    verServicios(conn);
                    break;
                case 5:
                    verServiciosPorCliente(conn);
                    break;
                case 6:
                    generarComprobante(conn);
                    break;
                case 7:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }

    private static void registrarServicio(Connection conn) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el folio del servicio: ");
        int folioServicio = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Ingrese el ID del vehículo asociado al servicio: ");
        int idVehiculo = scanner.nextInt();
        scanner.nextLine();

        // Validar si el vehículo existe
        if (!GestionarVehiculos.existeVehiculo(conn, idVehiculo)) { //Metodo tomado de GestionarVehiculos
            System.out.println("Vehículo no encontrado. Regrese a la opción de gestionar vehículos para agregarlo.");
            return;  // Cancelar operación si el vehículo no existe
        }

        System.out.print("Ingrese el tipo de servicio (Preventivo/Correctivo): ");
        String tipoServicio = scanner.nextLine();

        System.out.print("Ingrese la fecha del servicio (YYYY-MM-DD): ");
        String fecha = scanner.nextLine();

        System.out.print("Ingrese el estatus del servicio (En espera, En proceso, Finalizado): ");
        String estatus = scanner.nextLine();
        
        System.out.print("Ingrese una breve descripcion del servicio: ");
        String descripcion = scanner.nextLine();

        // Preguntar por la fecha del próximo servicio de forma opcional
        System.out.print("Ingrese la fecha del próximo servicio (YYYY-MM-DD) o deje en blanco si no aplica: ");
        String fechaProximoServicio = scanner.nextLine();

        // Insertar el nuevo servicio en la tabla Servicio
        String query = "INSERT INTO Servicio (folioServicio, idVehiculo, tipoServicio, fecha, estatus, descripcion, fechaProximoServicio) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, folioServicio);
            stmt.setInt(2, idVehiculo);
            stmt.setString(3, tipoServicio);
            stmt.setDate(4, Date.valueOf(fecha));
            stmt.setString(5, estatus);
            stmt.setString(6, descripcion);

            // Si el usuario deja en blanco la fecha, insertar NULL, de lo contrario, insertar la fecha
            if (fechaProximoServicio.isEmpty()) {
                stmt.setNull(7, java.sql.Types.DATE);  // Definir como NULL si el campo está vacío
            } else {
                stmt.setDate(7, Date.valueOf(fechaProximoServicio));  // Usar la fecha proporcionada
            }

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Servicio registrado exitosamente.");
                agregarRefaccionesAlServicio(conn, folioServicio);  // Llamar al método para agregar refacciones
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private static void agregarRefaccionesAlServicio(Connection conn, int folioServicio) {
        Scanner scanner = new Scanner(System.in);

        boolean agregarOtraRefaccion = true;
        while (agregarOtraRefaccion) {
            System.out.print("Ingrese el ID de la refacción: ");
            int idRefaccion = scanner.nextInt();
            System.out.print("Ingrese la cantidad de esta refacción: ");
            int cantidad = scanner.nextInt();
            scanner.nextLine();  // Limpiar buffer

            // Insertar la relación en la tabla Servicio_Refaccion
            String query = "INSERT INTO Servicio_Refaccion (idServicio, idRefaccion, cantidad) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, folioServicio);
                stmt.setInt(2, idRefaccion);
                stmt.setInt(3, cantidad);

                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Refacción añadida exitosamente al servicio.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            System.out.print("¿Desea agregar otra refacción? (Si/No): ");
            String respuesta = scanner.nextLine();
            agregarOtraRefaccion = respuesta.equalsIgnoreCase("Si");
        }
    }

    private static void modificarServicio(Connection conn) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el folio del servicio que desea modificar: ");
        int folioServicio = scanner.nextInt();
        scanner.nextLine();

        // Verificar si el servicio existe
        if (!existeServicio(conn, folioServicio)) {
            System.out.println("El servicio no existe.");
            return;
        }

        System.out.print("Ingrese el nuevo tipo de servicio: ");
        String nuevoTipo = scanner.nextLine();

        System.out.print("Ingrese la nueva fecha del servicio (YYYY-MM-DD): ");
        String nuevaFecha = scanner.nextLine();

        System.out.print("Ingrese el nuevo estatus del servicio: ");
        String nuevoEstatus = scanner.nextLine();

        System.out.print("Ingrese la nueva descripción: ");
        String nuevaDescripcion = scanner.nextLine();

        System.out.print("Ingrese la nueva fecha del próximo servicio (YYYY-MM-DD): (Si es que requiere)");
        String nuevaFechaProximo = scanner.nextLine();

        // Consulta SQL para actualizar los datos del servicio
        String query = "UPDATE Servicio SET tipoServicio = ?, fecha = ?, estatus = ?, descripcion = ?, fechaProximoServicio = ? WHERE folioServicio = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nuevoTipo);
            stmt.setDate(2, Date.valueOf(nuevaFecha));
            stmt.setString(3, nuevoEstatus);
            stmt.setString(4, nuevaDescripcion);
            // Si el usuario deja en blanco la fecha, insertar NULL, de lo contrario, insertar la fecha
            if (nuevaFechaProximo.isEmpty()) {
                stmt.setNull(5, java.sql.Types.DATE);
            } else {
                stmt.setDate(5, Date.valueOf(nuevaFechaProximo));  // Usar la fecha proporcionada
            }
            stmt.setInt(6, folioServicio);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Servicio modificado exitosamente.");
                modificarRefaccionesDelServicio(conn, folioServicio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void modificarRefaccionesDelServicio(Connection conn, int folioServicio) {
        Scanner scanner = new Scanner(System.in);

        // Mostrar las refacciones asociadas al servicio
        verRefaccionesPorServicio(conn, folioServicio);

        // Eliminar las refacciones existentes (opcional)
        System.out.print("¿Desea eliminar las refacciones actuales y agregar nuevas? (Si/No): ");
        String respuesta = scanner.nextLine();
        if (respuesta.equalsIgnoreCase("Si")) {
            eliminarRefaccionesPorServicio(conn, folioServicio);
            agregarRefaccionesAlServicio(conn, folioServicio);
        }
    }

    private static void eliminarServicio(Connection conn) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el folio del servicio que desea eliminar: ");
        int folioServicio = scanner.nextInt();
        scanner.nextLine();

        // Verificar si el servicio existe
        if (!existeServicio(conn, folioServicio)) {
            System.out.println("El servicio no existe.");
            return;
        }

        // Eliminar las refacciones asociadas al servicio
        eliminarRefaccionesPorServicio(conn, folioServicio);

        // Eliminar el servicio
        String query = "DELETE FROM Servicio WHERE folioServicio = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, folioServicio);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Servicio eliminado exitosamente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private static void eliminarRefaccionesPorServicio(Connection conn, int folioServicio) {
        String query = "DELETE FROM Servicio_Refaccion WHERE idServicio = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, folioServicio);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Refacciones asociadas al servicio eliminadas exitosamente.");
            } else {
                System.out.println("No se encontraron refacciones asociadas al servicio.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void verServicios(Connection conn) {
            String query = "SELECT * FROM Servicio";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();

                System.out.println("\nLista de Servicios:");
                while (rs.next()) {
                    System.out.println("Folio: " + rs.getInt("folioServicio") +
                                       ", Tipo: " + rs.getString("tipoServicio") +
                                       ", ID Vehiculo: "+ rs.getInt("idVehiculo")+
                                       ", Fecha: " + rs.getDate("fecha") +
                                       ", Estatus: " + rs.getString("estatus") +
                                       ", Descripción: " + rs.getString("descripcion") +
                                       ", Próximo Servicio: " + rs.getDate("fechaProximoServicio"));

                    // Mostrar las refacciones asociadas
                    verRefaccionesPorServicio(conn, rs.getInt("folioServicio"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    private static void verRefaccionesPorServicio(Connection conn, int folioServicio) {
    String query = "SELECT r.nombre, sr.cantidad FROM Servicio_Refaccion sr JOIN Refaccion r ON sr.idRefaccion = r.idRefaccion WHERE sr.idServicio = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, folioServicio);

            ResultSet rs = stmt.executeQuery();

            System.out.println("Refacciones del Servicio:");
            while (rs.next()) {
                System.out.println(" - Refacción: " + rs.getString("nombre") + ", Cantidad: " + rs.getInt("cantidad"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void verServiciosPorCliente(Connection conn) {
        Scanner s = new Scanner(System.in);
        System.out.print("Ingrese el ID del Cliente: ");
        int idCliente = s.nextInt();
        
        // Verificar si el servicio existe
        if (!GestionarClientes.existeCliente(conn, idCliente)) {
            System.out.println("El cliente no existe.");
            return;
        }
        
        String query = "SELECT s.folioServicio, s.tipoServicio, s.fecha, s.estatus, s.descripcion, s.fechaProximoServicio, c.nombreCliente " +
                       "FROM Servicio s " +
                       "JOIN Vehiculo v ON s.idVehiculo = v.idVehiculo " +
                       "JOIN Cliente c ON v.idCliente = c.idCliente " +
                       "WHERE c.idCliente = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\nServicios del cliente con ID: " + idCliente);
            while (rs.next()) {
                System.out.println("Nombre del cliente: " + rs.getString("nombreCliente"));
                System.out.println("Folio de Servicio: " + rs.getInt("folioServicio"));
                System.out.println("Tipo de Servicio: " + rs.getString("tipoServicio"));
                System.out.println("Fecha: " + rs.getDate("fecha"));
                System.out.println("Estatus: " + rs.getString("estatus"));
                System.out.println("Descripción: " + rs.getString("descripcion"));
                System.out.println("Fecha Próximo Servicio: " + rs.getDate("fechaProximoServicio"));
                System.out.println("-------------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private static boolean existeServicio(Connection conn, int folioServicio) {
        String query = "SELECT folioServicio FROM Servicio WHERE folioServicio = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, folioServicio);

            ResultSet rs = stmt.executeQuery();
            return rs.next();  // Si el servicio existe, retorna true

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private static void generarComprobante(Connection conn) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el folio del servicio: ");
        int folioServicio = scanner.nextInt();
        scanner.nextLine();

        try {
            // Consulta para obtener los detalles del servicio, vehículo y dueño (nombre completo)
            String query = "SELECT s.folioServicio, s.tipoServicio, s.fecha, s.estatus, " +
                           "v.marca, v.modelo, v.año, CONCAT(p.nombre, ' ', p.apellidoPaterno, ' ', p.apellidoMaterno) AS dueñoCompleto " +
                           "FROM Servicio s " +
                           "JOIN Vehiculo v ON s.idVehiculo = v.idVehiculo " +
                           "JOIN Cliente c ON v.idCliente = c.idCliente " +
                           "JOIN Persona p ON c.idPersona = p.idPersona " +
                           "WHERE s.folioServicio = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, folioServicio);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Obtener los detalles del servicio y del vehículo
                int folio = rs.getInt("folioServicio");
                String tipoServicio = rs.getString("tipoServicio");
                Date fechaServicio = rs.getDate("fecha");
                String estatus = rs.getString("estatus");
                String marca = rs.getString("marca");
                String modelo = rs.getString("modelo");
                int año = rs.getInt("año");
                String dueñoCompleto = rs.getString("dueñoCompleto");

                // Mostrar los datos del comprobante en consola
                System.out.println("\n===== Comprobante de Servicio =====");
                System.out.println("Folio del Servicio: " + folio);
                System.out.println("Servicio: " + tipoServicio);
                System.out.println("Dueño del Vehículo: " + dueñoCompleto);
                System.out.println("Vehículo: " + marca + " " + modelo + " " + año);
                System.out.println("Fecha del Servicio: " + fechaServicio);
                System.out.println("Estatus: " + estatus);
                System.out.println("------------------------------------");

                // Consulta para obtener las refacciones asociadas al servicio
                String refaccionQuery = "SELECT r.nombre, sr.cantidad, r.costo " +
                                        "FROM Servicio_Refaccion sr " +
                                        "JOIN Refaccion r ON sr.idRefaccion = r.idRefaccion " +
                                        "WHERE sr.idServicio = ?";
                PreparedStatement refaccionStmt = conn.prepareStatement(refaccionQuery);
                refaccionStmt.setInt(1, folioServicio);
                ResultSet rsRefaccion = refaccionStmt.executeQuery();

                double total = 0.0;
                System.out.println("Refacciones utilizadas:");
                System.out.println("Nombre   |   Cantidad   |   Costo Unitario   |   Subtotal");
                while (rsRefaccion.next()) {
                    String nombreRefaccion = rsRefaccion.getString("nombre");
                    int cantidad = rsRefaccion.getInt("cantidad");
                    double costoUnitario = rsRefaccion.getDouble("costo");
                    double subtotal = cantidad * costoUnitario;
                    total += subtotal;

                    System.out.printf("%s   |   %d   |   $%.2f   |   $%.2f\n", nombreRefaccion, cantidad, costoUnitario, subtotal);
                }
                System.out.println("------------------------------------");
                System.out.printf("Total: $%.2f\n", total);

//                // Preguntar si se desea generar un PDF
//                System.out.print("¿Desea generar un comprobante en PDF? (Si/No): ");
//                String respuesta = scanner.nextLine();
//
//                if (respuesta.equalsIgnoreCase("Si")) {
//                    generarPDF(folio, tipoServicio, dueñoCompleto, marca, modelo, año, fechaServicio, estatus, total, conn);
//                    System.out.println("Comprobante generado exitosamente en PDF.");
//                }
            } else {
                System.out.println("No se encontró ningún servicio con ese folio.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
//    private static void generarPDF(int folio, String tipoServicio, String dueñoCompleto, String marca, String modelo, int anio, Date fechaServicio, String estatus, double total, Connection conn) {
//        try {
//            // Crear documento PDF
//            Document document = new Document();
//
//            // Guardar en el directorio de trabajo actual
//            String rutaArchivo = "comprobante_servicio_" + folio + ".pdf";  // Guardar en el directorio actual
//            PdfWriter.getInstance(document, new FileOutputStream(rutaArchivo));
//
//            // Abrir el documento para empezar a agregar contenido
//            document.open();
//
//            // Agregar información al documento
//            document.add(new Paragraph("===== Comprobante de Servicio ====="));
//            document.add(new Paragraph("Folio del Servicio: " + folio));
//            document.add(new Paragraph("Servicio: " + tipoServicio));
//            document.add(new Paragraph("Dueño del Vehículo: " + dueñoCompleto));
//            document.add(new Paragraph("Vehículo: " + marca + " " + modelo + " " + anio));
//            document.add(new Paragraph("Fecha del Servicio: " + fechaServicio));
//            document.add(new Paragraph("Estatus: " + estatus));
//            document.add(new Paragraph(" "));  // Espacio
//
//            // Refacciones utilizadas
//            document.add(new Paragraph("Refacciones utilizadas:"));
//            document.add(new Paragraph(" "));  // Espacio
//            PdfPTable table = new PdfPTable(4);  // 4 columnas: Nombre, Cantidad, Costo Unitario, Subtotal
//            table.addCell("Nombre");
//            table.addCell("Cantidad");
//            table.addCell("Costo Unitario");
//            table.addCell("Subtotal");
//
//            // Consulta para obtener las refacciones relacionadas con el servicio
//            String refaccionQuery = "SELECT r.nombre, sr.cantidad, r.costo FROM Servicio_Refaccion sr JOIN Refaccion r ON sr.idRefaccion = r.idRefaccion WHERE sr.idServicio = ?";
//            PreparedStatement refaccionStmt = conn.prepareStatement(refaccionQuery);
//            refaccionStmt.setInt(1, folio);
//            ResultSet rsRefaccion = refaccionStmt.executeQuery();
//
//            // Agregar cada refacción al PDF
//            while (rsRefaccion.next()) {
//                table.addCell(rsRefaccion.getString("nombre"));  // Nombre de la refacción
//                table.addCell(String.valueOf(rsRefaccion.getInt("cantidad")));  // Cantidad de refacciones
//                table.addCell("$" + rsRefaccion.getDouble("costo"));  // Costo unitario de la refacción
//                double subtotal = rsRefaccion.getInt("cantidad") * rsRefaccion.getDouble("costo");
//                table.addCell("$" + subtotal);  // Subtotal (cantidad * costo unitario)
//            }
//
//            // Agregar tabla al documento
//            document.add(table);
//
//            // Agregar total
//            document.add(new Paragraph(" "));
//            document.add(new Paragraph("Total: $" + total));
//
//            // Cerrar el documento
//            document.close();
//
//            // Confirmación de que el PDF fue generado
//            System.out.println("Comprobante generado exitosamente en: " + rutaArchivo);
//
//        } catch (DocumentException | IOException | SQLException e) {
//            e.printStackTrace();  // Mostrar el error si ocurre alguno
//            System.out.println("Error al generar el PDF: " + e.getMessage());
//        }
//    }
}
