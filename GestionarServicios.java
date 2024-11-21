package proyectoFinal;
import java.sql.*;
import java.util.Scanner;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.data.category.DefaultCategoryDataset;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GestionarServicios {

//    public static void mostrarMenuServicios(Connection conn) {
//        Scanner scanner = new Scanner(System.in);
//        boolean continuar = true;
//        
//        while (continuar) {
//            System.out.println("\nGestión de Servicios:");
//            System.out.println("1. Registrar Servicio");
//            System.out.println("2. Modificar Servicio");
//            System.out.println("3. Eliminar Servicio");
//            System.out.println("4. Ver Servicios");
//            System.out.println("5. Ver servicios por cliente");
//            System.out.println("6. Generar Comprobante");
//            System.out.println("7. Regresar al Menú Principal");
//            System.out.print("Seleccione una opción: ");
//            int opcion = scanner.nextInt();
//            scanner.nextLine();
//
//            switch (opcion) {
//                case 1:
//                    registrarServicio(conn,folioServicio,idVehiculo,tipoServicio,fecha,estatus,descripcion,fechaProximoServicio);
//                    break;
//                case 2:
//                    modificarServicio(conn);
//                    break;
//                case 3:
//                    eliminarServicio(conn);
//                    break;
//                case 4:
//                    verServicios(conn);
//                    break;
//                case 5:
//                    verServiciosPorCliente(conn);
//                    break;
//                case 6:
//                    generarComprobante(conn);
//                    break;
//                case 7:
//                    continuar = false;
//                    break;
//                default:
//                    System.out.println("Opción no válida.");
//                    break;
//            }
//        }
//    }

    protected static void registrarServicio(Connection conn, int folioServicio, int idVehiculo, String tipoServicio, String fecha, String estatus, String descripcion, String fechaProximoServicio) {
        String query = "INSERT INTO Servicio (folioServicio, idVehiculo, tipoServicio, fecha, estatus, descripcion, fechaProximoServicio) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, folioServicio);
            stmt.setInt(2, idVehiculo);
            stmt.setString(3, tipoServicio);
            stmt.setDate(4, java.sql.Date.valueOf(fecha)); // Convertir la fecha de String a Date
            stmt.setString(5, estatus);
            stmt.setString(6, descripcion);

            if (fechaProximoServicio == null || fechaProximoServicio.isEmpty()) {
                stmt.setNull(7, java.sql.Types.DATE); // Manejar fecha nula
            } else {
                stmt.setDate(7, java.sql.Date.valueOf(fechaProximoServicio)); // Convertir la fecha
            }

            stmt.executeUpdate();
//            System.out.println("Servicio registrado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    protected static void agregarRefaccionesAlServicio(Connection conn, int folioServicio, int idRefaccion, int cantidad) {
        try {
            // Verificar si la refacción ya está asociada al servicio
            String checkQuery = "SELECT cantidad FROM Servicio_Refaccion WHERE idServicio = ? AND idRefaccion = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setInt(1, folioServicio);
            checkStmt.setInt(2, idRefaccion);

            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // Si existe, actualizar la cantidad
                int cantidadActual = rs.getInt("cantidad");
                int nuevaCantidad = cantidadActual + cantidad;

                String updateQuery = "UPDATE Servicio_Refaccion SET cantidad = ? WHERE idServicio = ? AND idRefaccion = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setInt(1, nuevaCantidad);
                updateStmt.setInt(2, folioServicio);
                updateStmt.setInt(3, idRefaccion);

                int rowsUpdated = updateStmt.executeUpdate();
                if (rowsUpdated > 0) {
//                    System.out.println("Cantidad de la refacción actualizada exitosamente.");
                }
            } else {
                // Si no existe, insertar la nueva refacción
                String insertQuery = "INSERT INTO Servicio_Refaccion (idServicio, idRefaccion, cantidad) VALUES (?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                insertStmt.setInt(1, folioServicio);
                insertStmt.setInt(2, idRefaccion);
                insertStmt.setInt(3, cantidad);

                int rowsInserted = insertStmt.executeUpdate();
                if (rowsInserted > 0) {
//                    System.out.println("Refacción añadida exitosamente al servicio.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    protected static boolean modificarServicio(Connection conn, int folioServicio, String tipoServicio, String fecha, String estatus, String descripcion, String fechaProximoServicio) {
        String query = "UPDATE Servicio SET tipoServicio = ?, fecha = ?, estatus = ?, descripcion = ?, fechaProximoServicio = ? WHERE folioServicio = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, tipoServicio);
            stmt.setDate(2, Date.valueOf(fecha));
            stmt.setString(3, estatus);
            stmt.setString(4, descripcion);

            // Manejar el valor de fechaProximoServicio, permitiendo que sea NULL si está vacío
            if (fechaProximoServicio.isEmpty()) {
                stmt.setNull(5, java.sql.Types.DATE);
            } else {
                stmt.setDate(5, Date.valueOf(fechaProximoServicio));
            }

            stmt.setInt(6, folioServicio);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0; // Retorna true si se actualizó al menos un registro
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    protected static void eliminarServicio(Connection conn, int folioServicio) {
        // Verificar si el servicio existe
        if (!existeServicio(conn, folioServicio)) {
            System.out.println("El servicio no existe.");
            return; // Finaliza el método si no existe el servicio
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
            } else {
                System.out.println("No se pudo eliminar el servicio.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    protected static boolean eliminarRefaccion(Connection conn, int folioServicio, int idRefaccion) {
        String query = "DELETE FROM Servicio_Refaccion WHERE idServicio = ? AND idRefaccion = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, folioServicio);
            stmt.setInt(2, idRefaccion);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
//                System.out.println("Refacción con ID " + idRefaccion + " eliminada del servicio con folio " + folioServicio);
                return true;
            } else {
//                System.out.println("No se encontró la refacción con ID " + idRefaccion + " asociada al servicio con folio " + folioServicio);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    protected static boolean eliminarCantidadRefaccion(Connection conn, int folioServicio, int idRefaccion, int cantidadEliminar) {
        String selectQuery = "SELECT cantidad FROM Servicio_Refaccion WHERE idServicio = ? AND idRefaccion = ?";
        String updateQuery = "UPDATE Servicio_Refaccion SET cantidad = ? WHERE idServicio = ? AND idRefaccion = ?";
        String deleteQuery = "DELETE FROM Servicio_Refaccion WHERE idServicio = ? AND idRefaccion = ?";

        try {
            // Obtener la cantidad actual de la refacción
            try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
                selectStmt.setInt(1, folioServicio);
                selectStmt.setInt(2, idRefaccion);

                ResultSet rs = selectStmt.executeQuery();
                if (rs.next()) {
                    int cantidadActual = rs.getInt("cantidad");

                    // Verificar si la cantidad a eliminar es válida
                    if (cantidadEliminar > cantidadActual) {
                        System.out.println("Cantidad a eliminar supera la cantidad actual.");
                        return false;
                    }

                    // Si la cantidad a eliminar es igual a la cantidad actual, eliminar la fila
                    if (cantidadEliminar == cantidadActual) {
                        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
                            deleteStmt.setInt(1, folioServicio);
                            deleteStmt.setInt(2, idRefaccion);

                            int rowsDeleted = deleteStmt.executeUpdate();
                            return rowsDeleted > 0;
                        }
                    }

                    // Actualizar la cantidad restante en caso contrario
                    int nuevaCantidad = cantidadActual - cantidadEliminar;
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                        updateStmt.setInt(1, nuevaCantidad);
                        updateStmt.setInt(2, folioServicio);
                        updateStmt.setInt(3, idRefaccion);

                        int rowsUpdated = updateStmt.executeUpdate();
                        return rowsUpdated > 0;
                    }
                } else {
                    System.out.println("No se encontró la refacción con ID " + idRefaccion + " asociada al servicio " + folioServicio);
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    protected static void eliminarRefaccionesPorServicio(Connection conn, int folioServicio) {
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
    
    protected static void verServiciosPorCliente(Connection conn) {
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
    
    protected static boolean existeServicio(Connection conn, int folioServicio) {
        String query = "SELECT 1 FROM Servicio WHERE folioServicio = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, folioServicio);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Retorna true si encuentra un registro
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Retorna false si ocurre un error o no encuentra el servicio
    }
    
    protected static void generarPDF(Connection conn, int folioServicio, String rutaArchivo) {
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
                // Obtener los detalles del servicio y vehículo
                int folio = rs.getInt("folioServicio");
                String tipoServicio = rs.getString("tipoServicio");
                java.sql.Date sqlDate = rs.getDate("fecha");
                java.util.Date fechaServicio = new java.util.Date(sqlDate.getTime()); // Conversión de java.sql.Date a java.util.Date
                String estatus = rs.getString("estatus");
                String marca = rs.getString("marca");
                String modelo = rs.getString("modelo");
                int anio = rs.getInt("año");
                String dueñoCompleto = rs.getString("dueñoCompleto");

                // Consulta para calcular el total del servicio basado en las refacciones
                String refaccionQuery = "SELECT r.nombre, sr.cantidad, r.costo " +
                                        "FROM Servicio_Refaccion sr " +
                                        "JOIN Refaccion r ON sr.idRefaccion = r.idRefaccion " +
                                        "WHERE sr.idServicio = ?";
                PreparedStatement refaccionStmt = conn.prepareStatement(refaccionQuery);
                refaccionStmt.setInt(1, folioServicio);
                ResultSet rsRefaccion = refaccionStmt.executeQuery();

                double total = 0.0;

                // Crear el documento PDF
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(rutaArchivo));
                document.open();

                // Agregar contenido al PDF
                document.add(new Paragraph("===== Comprobante de Servicio ====="));
                document.add(new Paragraph("Folio del Servicio: " + folio));
                document.add(new Paragraph("Servicio: " + tipoServicio));
                document.add(new Paragraph("Dueño del Vehículo: " + dueñoCompleto));
                document.add(new Paragraph("Vehículo: " + marca + " " + modelo + " " + anio));
                document.add(new Paragraph("Fecha del Servicio: " + fechaServicio));
                document.add(new Paragraph("Estatus: " + estatus));
                document.add(new Paragraph(" ")); // Espacio

                // Agregar la tabla de refacciones
                PdfPTable table = new PdfPTable(4); // Nombre, Cantidad, Costo Unitario, Subtotal
                table.addCell("Nombre");
                table.addCell("Cantidad");
                table.addCell("Costo Unitario");
                table.addCell("Subtotal");

                while (rsRefaccion.next()) {
                    String nombre = rsRefaccion.getString("nombre");
                    int cantidad = rsRefaccion.getInt("cantidad");
                    double costoUnitario = rsRefaccion.getDouble("costo");
                    double subtotal = cantidad * costoUnitario;
                    total += subtotal;

                    table.addCell(nombre);
                    table.addCell(String.valueOf(cantidad));
                    table.addCell("$" + String.format("%.2f", costoUnitario));
                    table.addCell("$" + String.format("%.2f", subtotal));
                }

                document.add(table);
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Total: $" + String.format("%.2f", total)));

                document.close();

                // Confirmación de que el PDF fue generado
//                System.out.println("Comprobante generado exitosamente en: " + rutaArchivo);
            } else {
//                System.out.println("No se encontró ningún servicio con el folio proporcionado.");
            }
        } catch (SQLException | DocumentException | IOException e) {
            e.printStackTrace();
//            System.out.println("Error al generar el PDF: " + e.getMessage());
        }
    }

    protected static Map<String, Integer> obtenerTotalesPorEstatus(Connection conn) {
        String query = "SELECT estatus, COUNT(*) AS total FROM Servicio GROUP BY estatus";
        Map<String, Integer> totales = new HashMap<>();

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                totales.put(rs.getString("estatus"), rs.getInt("total"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totales;
    }
    
//    public static void mostrarGraficaEstatus(Connection conn) {
//        // Obtener los datos
//        Map<String, Integer> totales = obtenerTotalesPorEstatus(conn);
//
//        // Crear el conjunto de datos para la gráfica
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        dataset.addValue(totales.getOrDefault("En espera", 0), "Estatus", "En espera");
//        dataset.addValue(totales.getOrDefault("En proceso", 0), "Estatus", "En proceso");
//        dataset.addValue(totales.getOrDefault("Finalizado", 0), "Estatus", "Finalizado");
//
//        // Crear la gráfica
//        JFreeChart chart = ChartFactory.createBarChart(
//                "Totales por Estatus de Servicio", // Título
//                "Estatus", // Etiqueta del eje X
//                "Total", // Etiqueta del eje Y
//                dataset // Datos
//        );
//
//        // Crear un panel para mostrar la gráfica
//        ChartPanel chartPanel = new ChartPanel(chart);
//        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
//
//        // Crear un JFrame para mostrar la gráfica
//        JFrame frame = new JFrame("Dashboard - Totales por Estatus");
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        frame.getContentPane().add(chartPanel);
//        frame.pack();
//        frame.setVisible(true);
//    }
}
