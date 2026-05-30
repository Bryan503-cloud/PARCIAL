package com.mycompany.clinica_veterinaria;

import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class Servicio extends javax.swing.JInternalFrame {

    public Servicio() {
        initComponents();
        aplicarEstilo();
        aplicarFiltros();
        cargarDatos();
    }

    private void aplicarFiltros() {
        Utilidades.soloNumerosDecimales(txtPrecio);
    }

    private void aplicarEstilo() {
        EstiloUI.aplicarVentana(getContentPane(), pnlHeader,
            new javax.swing.JTable[]{tblServicios},
            new javax.swing.JButton[]{btnGuardar, btnEditar, btnEliminar, btnLimpiar});
    }

    private void cargarDatos() {
        DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Descripción", "Costo Base", "Activo"}, 0
        ) { public boolean isCellEditable(int r, int c) { return false; } };
        tblServicios.setModel(modelo);
        try {
            Connection con = Conexion.getConnection();
            if (con == null) return;
            PreparedStatement ps = con.prepareStatement(
                "SELECT id_servicio, nombre_servicio, descripcion, costo_base, estado FROM SERVICIO ORDER BY nombre_servicio");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id_servicio"), rs.getString("nombre_servicio"), rs.getString("descripcion"),
                    rs.getBigDecimal("costo_base"), rs.getBoolean("estado") ? "Sí" : "No"
                });
            }
            con.close();
        } catch (Exception e) {
            Utilidades.mostrarError(this, "Error cargando servicios: " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        txtNombre.setText(""); txaDescripcion.setText(""); txtPrecio.setText("");
        chkActivo.setSelected(true); tblServicios.clearSelection();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblPrecio = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        chkActivo = new javax.swing.JCheckBox();
        lblDescripcion = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaDescripcion = new javax.swing.JTextArea();
        btnGuardar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblServicios = new javax.swing.JTable();

        setClosable(true); setMaximizable(true); setResizable(true);
        setTitle("Gestión de Servicios");

        pnlHeader.setBackground(new java.awt.Color(30, 100, 160));
        lblTitulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        lblTitulo.setForeground(java.awt.Color.WHITE);
        lblTitulo.setText("  Gestión de Servicios");
        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        pnlHeaderLayout.setVerticalGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup().addGap(12).addComponent(lblTitulo).addGap(12)));

        lblNombre.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); lblNombre.setText("Nombre:");
        txtNombre.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        lblPrecio.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); lblPrecio.setText("Precio ($):");
        txtPrecio.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        chkActivo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        chkActivo.setSelected(true); chkActivo.setText("Activo");
        lblDescripcion.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); lblDescripcion.setText("Descripción:");
        txaDescripcion.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        txaDescripcion.setRows(3); txaDescripcion.setLineWrap(true);
        jScrollPane2.setViewportView(txaDescripcion);

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

        tblServicios.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        tblServicios.setAutoCreateRowSorter(true);
        tblServicios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblServicios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) { tblServiciosMouseClicked(evt); }
        });
        jScrollPane1.setViewportView(tblServicios);

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
                            .addComponent(lblNombre)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPrecio)
                            .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20)
                        .addComponent(chkActivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDescripcion)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnGuardar).addGap(10)
                        .addComponent(btnEditar).addGap(10)
                        .addComponent(btnEliminar).addGap(10)
                        .addComponent(btnLimpiar)))
                .addGap(20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre).addComponent(lblPrecio).addComponent(lblDescripcion))
                .addGap(4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkActivo)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
        String precioStr = txtPrecio.getText().trim();
        if (Utilidades.campoVacio(nombre)) { Utilidades.mostrarError(this, "El nombre es obligatorio."); return; }
        if (!Utilidades.validarLongitud(nombre, 3, 100)) { Utilidades.mostrarError(this, "El nombre del servicio debe tener al menos 3 caracteres."); return; }
        if (Utilidades.campoVacio(precioStr)) { Utilidades.mostrarError(this, "El precio es obligatorio."); return; }
        try {
            double precio = Double.parseDouble(precioStr);
            if (precio < 0) { Utilidades.mostrarError(this, "El precio no puede ser negativo."); return; }
            Connection con = Conexion.getConnection();
            if (con == null) return;
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO SERVICIO (nombre_servicio, descripcion, costo_base, estado) VALUES (?, ?, ?, ?)");
            ps.setString(1, nombre);
            ps.setString(2, txaDescripcion.getText().trim().isEmpty() ? null : txaDescripcion.getText().trim());
            ps.setDouble(3, precio);
            ps.setBoolean(4, chkActivo.isSelected());
            ps.executeUpdate(); con.close();
            Utilidades.mostrarExito(this, "Servicio guardado exitosamente.");
            limpiarCampos(); cargarDatos();
        } catch (NumberFormatException e) {
            Utilidades.mostrarError(this, "Precio inválido. Ingrese un número válido.");
        } catch (Exception e) {
            Utilidades.mostrarError(this, "Error al guardar: " + e.getMessage());
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        int fila = tblServicios.getSelectedRow();
        if (fila == -1) { Utilidades.mostrarError(this, "Seleccione un servicio de la tabla."); return; }
        int id = (int) tblServicios.getValueAt(fila, 0);
        String nombre = txtNombre.getText().trim();
        String precioStrE = txtPrecio.getText().trim();
        if (Utilidades.campoVacio(nombre)) { Utilidades.mostrarError(this, "El nombre es obligatorio."); return; }
        if (!Utilidades.validarLongitud(nombre, 3, 100)) { Utilidades.mostrarError(this, "El nombre del servicio debe tener al menos 3 caracteres."); return; }
        if (Utilidades.campoVacio(precioStrE)) { Utilidades.mostrarError(this, "El precio es obligatorio."); return; }
        try {
            double precio = Double.parseDouble(precioStrE);
            if (precio < 0) { Utilidades.mostrarError(this, "El precio no puede ser negativo."); return; }
            Connection con = Conexion.getConnection();
            if (con == null) return;
            PreparedStatement ps = con.prepareStatement(
                "UPDATE SERVICIO SET nombre_servicio=?, descripcion=?, costo_base=?, estado=? WHERE id_servicio=?");
            ps.setString(1, nombre);
            ps.setString(2, txaDescripcion.getText().trim());
            ps.setDouble(3, precio);
            ps.setBoolean(4, chkActivo.isSelected());
            ps.setInt(5, id);
            ps.executeUpdate(); con.close();
            Utilidades.mostrarExito(this, "Servicio actualizado.");
            limpiarCampos(); cargarDatos();
        } catch (NumberFormatException e) {
            Utilidades.mostrarError(this, "Precio inválido.");
        } catch (Exception e) {
            Utilidades.mostrarError(this, "Error al actualizar: " + e.getMessage());
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int fila = tblServicios.getSelectedRow();
        if (fila == -1) { Utilidades.mostrarError(this, "Seleccione un servicio de la tabla."); return; }
        int id = (int) tblServicios.getValueAt(fila, 0);
        if (!Utilidades.confirmar(this, "¿Eliminar el servicio seleccionado?")) return;
        try {
            Connection con = Conexion.getConnection();
            if (con == null) return;
            PreparedStatement ps = con.prepareStatement("DELETE FROM SERVICIO WHERE id_servicio=?");
            ps.setInt(1, id); ps.executeUpdate(); con.close();
            Utilidades.mostrarExito(this, "Servicio eliminado.");
            limpiarCampos(); cargarDatos();
        } catch (Exception e) {
            Utilidades.mostrarError(this, "Error (puede estar en uso en citas): " + e.getMessage());
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void tblServiciosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblServiciosMouseClicked
        int fila = tblServicios.getSelectedRow();
        if (fila >= 0) {
            txtNombre.setText(obj2str(tblServicios.getValueAt(fila, 1)));
            txaDescripcion.setText(obj2str(tblServicios.getValueAt(fila, 2)));
            txtPrecio.setText(obj2str(tblServicios.getValueAt(fila, 3)));
            chkActivo.setSelected("Sí".equals(obj2str(tblServicios.getValueAt(fila, 4))));
        }
    }//GEN-LAST:event_tblServiciosMouseClicked

    private String obj2str(Object o) { return o != null ? o.toString() : ""; }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JCheckBox chkActivo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JTable tblServicios;
    private javax.swing.JTextArea txaDescripcion;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables
}
