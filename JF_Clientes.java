package proyectoFinal;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.*;
public class JF_Clientes extends javax.swing.JFrame {
    private Connection conn;
    
    /** Creates new form JF_Clientes */
    public JF_Clientes(Connection conn) {
        this.conn = conn;
        initComponents();
        
        // Cambiar el comportamiento al cerrar la ventana
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        // Añadir un WindowListener para regresar al menú al cerrar
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                regresarAlMenu();
            }
        });
    }
    
    // Método para regresar al menú principal
    private void regresarAlMenu() {
        new JF_Menu(conn).setVisible(true); // Abre el menú principal
        this.dispose(); 
    }
    
    private void limpiarCampos() {
        TextFieldNombre.setText("");
        TextFieldApellidoPaterno.setText("");
        TextFieldApellidoMaterno.setText("");
        TextFieldEdad.setText("");
        TextFieldTelefono.setText("");
        TextFieldEmail.setText("");
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        TextFieldNombre = new javax.swing.JTextField();
        TextFieldApellidoPaterno = new javax.swing.JTextField();
        TextFieldApellidoMaterno = new javax.swing.JTextField();
        TextFieldEdad = new javax.swing.JTextField();
        TextFieldTelefono = new javax.swing.JTextField();
        TextFieldEmail = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        VerClientes = new javax.swing.JTextArea();
        RegistrarCliente = new javax.swing.JButton();
        ModificarCliente = new javax.swing.JButton();
        MostrarCliente = new javax.swing.JButton();
        EliminarCliente = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Gestión de Clientes");

        jLabel2.setText("Nombre:");

        jLabel3.setText("Apellido Paterno:");

        jLabel4.setText("Apellido Materno:");

        jLabel5.setText("Edad:");

        jLabel6.setText("Telefono:");

        jLabel7.setText("E-mail:");

        TextFieldNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextFieldNombreActionPerformed(evt);
            }
        });

        TextFieldApellidoPaterno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextFieldApellidoPaternoActionPerformed(evt);
            }
        });

        TextFieldApellidoMaterno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextFieldApellidoMaternoActionPerformed(evt);
            }
        });

        TextFieldEdad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextFieldEdadActionPerformed(evt);
            }
        });

        TextFieldTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextFieldTelefonoActionPerformed(evt);
            }
        });

        TextFieldEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextFieldEmailActionPerformed(evt);
            }
        });

        VerClientes.setColumns(20);
        VerClientes.setRows(5);
        jScrollPane1.setViewportView(VerClientes);

        RegistrarCliente.setText("Registrar");
        RegistrarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegistrarClienteActionPerformed(evt);
            }
        });

        ModificarCliente.setText("Modificar");
        ModificarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarClienteActionPerformed(evt);
            }
        });

        MostrarCliente.setText("Mostrar");
        MostrarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MostrarClienteActionPerformed(evt);
            }
        });

        EliminarCliente.setText("Eliminar");
        EliminarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel3))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(TextFieldApellidoPaterno, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                                            .addComponent(TextFieldEdad))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(TextFieldEmail))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(TextFieldApellidoMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(TextFieldTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RegistrarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MostrarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(EliminarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ModificarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(TextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextFieldApellidoPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(TextFieldApellidoMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextFieldEdad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(TextFieldTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RegistrarCliente)
                    .addComponent(ModificarCliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MostrarCliente)
                    .addComponent(EliminarCliente))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TextFieldNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextFieldNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextFieldNombreActionPerformed

    private void TextFieldApellidoPaternoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextFieldApellidoPaternoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextFieldApellidoPaternoActionPerformed

    private void TextFieldApellidoMaternoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextFieldApellidoMaternoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextFieldApellidoMaternoActionPerformed

    private void TextFieldEdadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextFieldEdadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextFieldEdadActionPerformed

    private void TextFieldTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextFieldTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextFieldTelefonoActionPerformed

    private void TextFieldEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextFieldEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextFieldEmailActionPerformed

    private void RegistrarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegistrarClienteActionPerformed
        try {
            // Validar conexión a la base de datos
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Error: No hay conexión a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener los datos de los campos de texto
            String nombre = TextFieldNombre.getText().trim();
            String apellidoPaterno = TextFieldApellidoPaterno.getText().trim();
            String apellidoMaterno = TextFieldApellidoMaterno.getText().trim();
            String edadText = TextFieldEdad.getText().trim();
            String telefono = TextFieldTelefono.getText().trim();
            String email = TextFieldEmail.getText().trim();

            // Validar que no haya campos vacíos
            if (nombre.isEmpty() || apellidoPaterno.isEmpty() || apellidoMaterno.isEmpty() || 
                edadText.isEmpty() || telefono.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar que la edad sea un número entero válido
            int edad;
            try {
                edad = Integer.parseInt(edadText);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "La edad debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Concatenar el nombre completo del cliente
            String nombreCliente = nombre + " " + apellidoPaterno + " " + apellidoMaterno;

            // Llamar al método para registrar el cliente
            GestionarClientes.registrarCliente(conn, nombre, apellidoPaterno, apellidoMaterno, edad, telefono, email, nombreCliente);

            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(this, "Cliente registrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // Limpiar los campos después del registro
            limpiarCampos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al registrar el cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_RegistrarClienteActionPerformed

    private void ModificarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarClienteActionPerformed
        try {
            // Validar conexión a la base de datos
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Error: No hay conexión a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Solicitar el ID del cliente a modificar
            String inputId = JOptionPane.showInputDialog(this, "Ingrese el ID del cliente que desea modificar:");
            if (inputId == null || inputId.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un ID válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int idCliente;
            try {
                idCliente = Integer.parseInt(inputId.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El ID debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificar si el cliente existe
            if (!GestionarClientes.existeCliente(conn, idCliente)) {
                JOptionPane.showMessageDialog(this, "El cliente con ID " + idCliente + " no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener los datos del formulario
            String nuevoNombre = TextFieldNombre.getText().trim();
            String nuevoApellidoPaterno = TextFieldApellidoPaterno.getText().trim();
            String nuevoApellidoMaterno = TextFieldApellidoMaterno.getText().trim();
            String nuevaEdadText = TextFieldEdad.getText().trim();
            String nuevoTelefono = TextFieldTelefono.getText().trim();
            String nuevoEmail = TextFieldEmail.getText().trim();

            // Validar campos vacíos
            if (nuevoNombre.isEmpty() || nuevoApellidoPaterno.isEmpty() || nuevoApellidoMaterno.isEmpty() ||
                nuevaEdadText.isEmpty() || nuevoTelefono.isEmpty() || nuevoEmail.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar que la edad sea un número válido
            int nuevaEdad;
            try {
                nuevaEdad = Integer.parseInt(nuevaEdadText);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "La edad debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Concatenar el nombre completo del cliente
            String nuevoNombreCliente = nuevoNombre + " " + nuevoApellidoPaterno + " " + nuevoApellidoMaterno;

            // Llamar al método para modificar el cliente
            GestionarClientes.modificarCliente(conn, idCliente, nuevoNombre, nuevoApellidoPaterno, nuevoApellidoMaterno,
                                               nuevaEdad, nuevoTelefono, nuevoEmail, nuevoNombreCliente);

            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(this, "Cliente modificado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // Limpiar los campos después de modificar
            limpiarCampos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al modificar el cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_ModificarClienteActionPerformed

    private void MostrarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MostrarClienteActionPerformed
        try {
            // Validar conexión a la base de datos
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Error: No hay conexión a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Consulta SQL para obtener todos los clientes
            String query = "SELECT c.idCliente, p.nombre, p.apellidoPaterno, p.apellidoMaterno, p.telefono, p.email " +
                           "FROM Cliente c " +
                           "JOIN Persona p ON c.idPersona = p.idPersona";

            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            // Construir el texto para mostrar los clientes
            StringBuilder clientes = new StringBuilder();
            clientes.append("===== Lista de Clientes =====\n\n");
            
            while (rs.next()) {
                clientes.append("ID Cliente: ").append(rs.getInt("idCliente")).append("\n")
                        .append("Nombre: ").append(rs.getString("nombre")).append(" ")
                        .append(rs.getString("apellidoPaterno")).append(" ")
                        .append(rs.getString("apellidoMaterno")).append("\n")
                        .append("Teléfono: ").append(rs.getString("telefono")).append("\n")
                        .append("Email: ").append(rs.getString("email")).append("\n")
                        .append("------------------------------\n");
            }

            // Mostrar en el JTextArea o un mensaje si no hay clientes
            if (clientes.length() > 0) {
                VerClientes.setText(clientes.toString());
            } else {
                VerClientes.setText("No hay clientes registrados.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al mostrar los clientes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_MostrarClienteActionPerformed

    private void EliminarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarClienteActionPerformed
        try {
            // Validar conexión a la base de datos
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Error: No hay conexión a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Solicitar el ID del cliente a eliminar
            String inputId = JOptionPane.showInputDialog(this, "Ingrese el ID del cliente que desea eliminar:");
            if (inputId == null || inputId.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un ID válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int idCliente;
            try {
                idCliente = Integer.parseInt(inputId.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El ID debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificar si el cliente existe
            if (!GestionarClientes.existeCliente(conn, idCliente)) {
                JOptionPane.showMessageDialog(this, "El cliente con ID " + idCliente + " no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Confirmar la eliminación
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar al cliente con ID " + idCliente + "?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (confirmacion != JOptionPane.YES_OPTION) {
                return; // Cancelar eliminación si el usuario no confirma
            }

            // Llamar al método para eliminar el cliente
            GestionarClientes.eliminarCliente(conn, idCliente);

            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(this, "Cliente eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar el cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_EliminarClienteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        Connection conn = ConnectDB.getConnection(); // Obtenemos la conexión a la base de datos

        if (conn == null) {
            System.out.println("Error: No se pudo establecer conexión con la base de datos.");
            return;
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JF_Clientes(conn).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton EliminarCliente;
    private javax.swing.JButton ModificarCliente;
    private javax.swing.JButton MostrarCliente;
    private javax.swing.JButton RegistrarCliente;
    private javax.swing.JTextField TextFieldApellidoMaterno;
    private javax.swing.JTextField TextFieldApellidoPaterno;
    private javax.swing.JTextField TextFieldEdad;
    private javax.swing.JTextField TextFieldEmail;
    private javax.swing.JTextField TextFieldNombre;
    private javax.swing.JTextField TextFieldTelefono;
    private javax.swing.JTextArea VerClientes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}
