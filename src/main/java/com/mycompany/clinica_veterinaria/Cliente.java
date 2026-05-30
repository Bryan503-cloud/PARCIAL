package com.mycompany.clinica_veterinaria;

import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class Cliente extends javax.swing.JInternalFrame {

    public Cliente() {
        initComponents();
        aplicarEstilo();
        aplicarFiltros();
        cargarDatos();
    }

    private void aplicarFiltros() {
        Utilidades.soloLetras(txtNombre);
        Utilidades.soloLetras(txtApellido);
    }

    private void aplicarEstilo() {
        EstiloUI.aplicarVentana(getContentPane(), pnlHeader,
            new javax.swing.JTable[]{tblClientes},
            new javax.swing.JButton[]{btnGuardar, btnEditar, btnEliminar, btnLimpiar, btnBuscar});
    }

    private void cargarDatos() {
        DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Apellido", "DUI", "Teléfono", "Correo", "Dirección"}, 0
        ) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblClientes.setModel(modelo);
        try {
            Connection con = Conexion.getConnection();
            if (con == null) return;
            PreparedStatement ps = con.prepareStatement(
                "SELECT id_cliente, nombre, apellido, dui, telefono, correo, direccion FROM CLIENTE ORDER BY apellido, nombre");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id_cliente"), rs.getString("nombre"), rs.getString("apellido"),
                    rs.getString("dui"), rs.getString("telefono"), rs.getString("correo"), rs.getString("direccion")
                });
            }
            con.close();
        } catch (Exception e) {
            Utilidades.mostrarError(this, "Error cargando clientes: " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        txtNombre.setText(""); txtApellido.setText(""); txtDui.setText("");
        txtTelefono.setText(""); txtCorreo.setText(""); txtDireccion.setText("");
        txtBuscar.setText(""); tblClientes.clearSelection();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblApellido = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        lblDui = new javax.swing.JLabel();
        txtDui = new javax.swing.JTextField();
        lblTelefono = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        lblCorreo = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        lblDireccion = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        lblBuscar = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();

        setClosable(true); setMaximizable(true); setResizable(true);
        setTitle("Gestión de Clientes");

        pnlHeader.setBackground(new java.awt.Color(30, 100, 160));
        lblTitulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        lblTitulo.setForeground(java.awt.Color.WHITE);
        lblTitulo.setText("  Gestión de Clientes");
        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        pnlHeaderLayout.setVerticalGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup().addGap(12).addComponent(lblTitulo).addGap(12)));

        lblNombre.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); lblNombre.setText("Nombre:");
        txtNombre.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        lblApellido.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); lblApellido.setText("Apellido:");
        txtApellido.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        lblDui.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); lblDui.setText("DUI:");
        txtDui.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        lblTelefono.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); lblTelefono.setText("Teléfono:");
        txtTelefono.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        lblCorreo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); lblCorreo.setText("Correo:");
        txtCorreo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        lblDireccion.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); lblDireccion.setText("Dirección:");
        txtDireccion.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        lblBuscar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); lblBuscar.setText("Buscar:");
        txtBuscar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));

        btnBuscar.setBackground(new java.awt.Color(30, 100, 160)); btnBuscar.setForeground(java.awt.Color.WHITE);
        btnBuscar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { btnBuscarActionPerformed(evt); }
        });
        btnGuardar.setBackground(new java.awt.Color(30, 100, 160)); btnGuardar.setForeground(java.awt.Color.WHITE);
        btnGuardar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { btnGuardarActionPerformed(evt); }
        });
        btnEditar.setBackground(new java.awt.Color(50, 150, 50)); btnEditar.setForeground(java.awt.Color.WHITE);
        btnEditar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { btnEditarActionPerformed(evt); }
        });
        btnEliminar.setBackground(new java.awt.Color(180, 40, 40)); btnEliminar.setForeground(java.awt.Color.WHITE);
        btnEliminar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { btnEliminarActionPerformed(evt); }
        });
        btnLimpiar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { btnLimpiarActionPerformed(evt); }
        });

        tblClientes.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        tblClientes.setAutoCreateRowSorter(true);
        tblClientes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) { tblClientesMouseClicked(evt); }
        });
        jScrollPane1.setViewportView(tblClientes);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombre).addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblApellido).addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDui).addComponent(txtDui, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTelefono).addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCorreo).addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDireccion).addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblBuscar).addGap(5)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8).addComponent(btnBuscar).addGap(25)
                        .addComponent(btnGuardar).addGap(8)
                        .addComponent(btnEditar).addGap(8)
                        .addComponent(btnEliminar).addGap(8)
                        .addComponent(btnLimpiar)))
                .addGap(20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre).addComponent(lblApellido).addComponent(lblDui)
                    .addComponent(lblTelefono).addComponent(lblCorreo).addComponent(lblDireccion))
                .addGap(4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDui, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblBuscar)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String dui = txtDui.getText().trim();
        String correo = txtCorreo.getText().trim();
        if (Utilidades.campoVacio(nombre) || Utilidades.campoVacio(apellido)) {
            Utilidades.mostrarError(this, "Nombre y Apellido son obligatorios."); return;
        }
        if (!Utilidades.validarSoloLetras(nombre)) {
            Utilidades.mostrarError(this, "El nombre solo puede contener letras."); return;
        }
        if (!Utilidades.validarSoloLetras(apellido)) {
            Utilidades.mostrarError(this, "El apellido solo puede contener letras."); return;
        }
        String tel = txtTelefono.getText().trim();
        if (!dui.isEmpty() && !Utilidades.validarDUI(dui)) {
            Utilidades.mostrarError(this, "DUI inválido. Formato: 00000000-0"); return;
        }
        if (!tel.isEmpty() && !Utilidades.validarTelefono(tel)) {
            Utilidades.mostrarError(this, "Teléfono inválido (7-15 dígitos)."); return;
        }
        if (!correo.isEmpty() && !Utilidades.validarEmail(correo)) {
            Utilidades.mostrarError(this, "Correo electrónico inválido."); return;
        }
        try {
            Connection con = Conexion.getConnection();
            if (con == null) return;
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO CLIENTE (nombre, apellido, dui, telefono, correo, direccion) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, nombre); ps.setString(2, apellido);
            ps.setString(3, dui.isEmpty() ? null : dui);
            ps.setString(4, txtTelefono.getText().trim().isEmpty() ? null : txtTelefono.getText().trim());
            ps.setString(5, correo.isEmpty() ? null : correo);
            ps.setString(6, txtDireccion.getText().trim().isEmpty() ? null : txtDireccion.getText().trim());
            ps.executeUpdate(); con.close();
            Utilidades.mostrarExito(this, "Cliente guardado exitosamente.");
            limpiarCampos(); cargarDatos();
        } catch (Exception e) {
            Utilidades.mostrarError(this, "Error al guardar: " + e.getMessage());
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        int fila = tblClientes.getSelectedRow();
        if (fila == -1) { Utilidades.mostrarError(this, "Seleccione un cliente de la tabla."); return; }
        int id = (int) tblClientes.getValueAt(fila, 0);
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String duiE = txtDui.getText().trim();
        String correoE = txtCorreo.getText().trim();
        String telE = txtTelefono.getText().trim();
        if (Utilidades.campoVacio(nombre) || Utilidades.campoVacio(apellido)) {
            Utilidades.mostrarError(this, "Nombre y Apellido son obligatorios."); return;
        }
        if (!Utilidades.validarSoloLetras(nombre)) {
            Utilidades.mostrarError(this, "El nombre solo puede contener letras."); return;
        }
        if (!Utilidades.validarSoloLetras(apellido)) {
            Utilidades.mostrarError(this, "El apellido solo puede contener letras."); return;
        }
        if (!duiE.isEmpty() && !Utilidades.validarDUI(duiE)) {
            Utilidades.mostrarError(this, "DUI inválido. Formato: 00000000-0"); return;
        }
        if (!telE.isEmpty() && !Utilidades.validarTelefono(telE)) {
            Utilidades.mostrarError(this, "Teléfono inválido (7-15 dígitos)."); return;
        }
        if (!correoE.isEmpty() && !Utilidades.validarEmail(correoE)) {
            Utilidades.mostrarError(this, "Correo electrónico inválido."); return;
        }
        try {
            Connection con = Conexion.getConnection();
            if (con == null) return;
            PreparedStatement ps = con.prepareStatement(
                "UPDATE CLIENTE SET nombre=?, apellido=?, dui=?, telefono=?, correo=?, direccion=? WHERE id_cliente=?");
            ps.setString(1, nombre); ps.setString(2, apellido);
            ps.setString(3, txtDui.getText().trim()); ps.setString(4, txtTelefono.getText().trim());
            ps.setString(5, txtCorreo.getText().trim()); ps.setString(6, txtDireccion.getText().trim());
            ps.setInt(7, id); ps.executeUpdate(); con.close();
            Utilidades.mostrarExito(this, "Cliente actualizado.");
            limpiarCampos(); cargarDatos();
        } catch (Exception e) {
            Utilidades.mostrarError(this, "Error al actualizar: " + e.getMessage());
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int fila = tblClientes.getSelectedRow();
        if (fila == -1) { Utilidades.mostrarError(this, "Seleccione un cliente de la tabla."); return; }
        int id = (int) tblClientes.getValueAt(fila, 0);
        if (!Utilidades.confirmar(this, "¿Eliminar al cliente seleccionado?")) return;
        try {
            Connection con = Conexion.getConnection();
            if (con == null) return;
            PreparedStatement ps = con.prepareStatement("DELETE FROM CLIENTE WHERE id_cliente=?");
            ps.setInt(1, id); ps.executeUpdate(); con.close();
            Utilidades.mostrarExito(this, "Cliente eliminado.");
            limpiarCampos(); cargarDatos();
        } catch (Exception e) {
            Utilidades.mostrarError(this, "Error (puede tener mascotas asociadas): " + e.getMessage());
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String busqueda = txtBuscar.getText().trim();
        if (busqueda.isEmpty()) { cargarDatos(); return; }
        DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Apellido", "DUI", "Teléfono", "Correo", "Dirección"}, 0
        ) { public boolean isCellEditable(int r, int c) { return false; } };
        tblClientes.setModel(modelo);
        try {
            Connection con = Conexion.getConnection();
            if (con == null) return;
            String like = "%" + busqueda + "%";
            PreparedStatement ps = con.prepareStatement(
                "SELECT id_cliente, nombre, apellido, dui, telefono, correo, direccion FROM CLIENTE WHERE nombre LIKE ? OR apellido LIKE ? OR dui LIKE ? ORDER BY apellido");
            ps.setString(1, like); ps.setString(2, like); ps.setString(3, like);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{rs.getInt("id_cliente"), rs.getString("nombre"), rs.getString("apellido"),
                    rs.getString("dui"), rs.getString("telefono"), rs.getString("correo"), rs.getString("direccion")});
            }
            con.close();
        } catch (Exception e) {
            Utilidades.mostrarError(this, "Error en búsqueda: " + e.getMessage());
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        int fila = tblClientes.getSelectedRow();
        if (fila >= 0) {
            txtNombre.setText(obj2str(tblClientes.getValueAt(fila, 1)));
            txtApellido.setText(obj2str(tblClientes.getValueAt(fila, 2)));
            txtDui.setText(obj2str(tblClientes.getValueAt(fila, 3)));
            txtTelefono.setText(obj2str(tblClientes.getValueAt(fila, 4)));
            txtCorreo.setText(obj2str(tblClientes.getValueAt(fila, 5)));
            txtDireccion.setText(obj2str(tblClientes.getValueAt(fila, 6)));
        }
    }//GEN-LAST:event_tblClientesMouseClicked

    private String obj2str(Object o) { return o != null ? o.toString() : ""; }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblApellido;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblDui;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtDui;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
