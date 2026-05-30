package com.mycompany.clinica_veterinaria;

import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class Mascota extends javax.swing.JInternalFrame {

    public Mascota() {
        initComponents();
        aplicarEstilo();
        aplicarFiltros();
        cargarClientes();
        cargarDatos();
    }

    private void aplicarFiltros() {
        Utilidades.soloLetras(txtNombre);
        Utilidades.soloLetras(txtApellido);
    }

    private void aplicarEstilo() {
        EstiloUI.aplicarVentana(getContentPane(), pnlHeader,
            new javax.swing.JTable[]{tblMascotas},
            new javax.swing.JButton[]{btnGuardar, btnEditar, btnEliminar, btnLimpiar});
    }

    private void cargarClientes() {
        cmbIdCliente.removeAllItems();
        try {
            Connection con = Conexion.getConnection();
            if (con == null) return;
            PreparedStatement ps = con.prepareStatement(
                "SELECT id_cliente, nombre, apellido FROM CLIENTE ORDER BY apellido, nombre");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cmbIdCliente.addItem(rs.getInt("id_cliente") + " - " + rs.getString("apellido") + ", " + rs.getString("nombre"));
            }
            con.close();
        } catch (Exception e) {
            Utilidades.mostrarError(this, "Error cargando clientes: " + e.getMessage());
        }
    }

    private void cargarDatos() {
        DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"ID", "Cliente", "Nombre", "Apellido", "Especie", "Raza", "Género", "Fecha Nac.", "Peso", "Observaciones"}, 0
        ) { public boolean isCellEditable(int r, int c) { return false; } };
        tblMascotas.setModel(modelo);
        try {
            Connection con = Conexion.getConnection();
            if (con == null) return;
            PreparedStatement ps = con.prepareStatement(
                "SELECT m.id_mascota, CONCAT(c.apellido, ', ', c.nombre) AS cliente, " +
                "m.nombre, m.apellido, m.especie, m.raza, m.genero, m.fecha_nacimiento, m.peso_aproximado, m.observaciones " +
                "FROM MASCOTA m JOIN CLIENTE c ON m.id_cliente = c.id_cliente ORDER BY m.nombre");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id_mascota"), rs.getString("cliente"),
                    rs.getString("nombre"), rs.getString("apellido"),
                    rs.getString("especie"), rs.getString("raza"), rs.getString("genero"),
                    rs.getString("fecha_nacimiento"), rs.getString("peso_aproximado"),
                    rs.getString("observaciones")
                });
            }
            con.close();
        } catch (Exception e) {
            Utilidades.mostrarError(this, "Error cargando mascotas: " + e.getMessage());
        }
    }

    private int getIdClienteSeleccionado() {
        String sel = (String) cmbIdCliente.getSelectedItem();
        if (sel == null) return -1;
        try { return Integer.parseInt(sel.split(" - ")[0]); } catch (Exception e) { return -1; }
    }

    private void limpiarCampos() {
        if (cmbIdCliente.getItemCount() > 0) cmbIdCliente.setSelectedIndex(0);
        txtNombre.setText(""); txtApellido.setText(""); txtRaza.setText("");
        spnFechaNacimiento.setValue(new java.util.Date()); txaObservaciones.setText("");
        cmbEspecie.setSelectedIndex(0); cmbGenero.setSelectedIndex(0);
        spnPeso.setValue(0.0); tblMascotas.clearSelection();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblIdCliente = new javax.swing.JLabel();
        cmbIdCliente = new javax.swing.JComboBox<>();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblApellido = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        lblEspecie = new javax.swing.JLabel();
        cmbEspecie = new javax.swing.JComboBox<>();
        lblRaza = new javax.swing.JLabel();
        txtRaza = new javax.swing.JTextField();
        lblGenero = new javax.swing.JLabel();
        cmbGenero = new javax.swing.JComboBox<>();
        lblFechaNacimiento = new javax.swing.JLabel();
        spnFechaNacimiento = new javax.swing.JSpinner(new javax.swing.SpinnerDateModel());
        lblPeso = new javax.swing.JLabel();
        spnPeso = new javax.swing.JSpinner();
        lblObservaciones = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaObservaciones = new javax.swing.JTextArea();
        btnGuardar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMascotas = new javax.swing.JTable();

        setClosable(true); setMaximizable(true); setResizable(true);
        setTitle("Gestión de Mascotas");

        pnlHeader.setBackground(new java.awt.Color(30, 100, 160));
        lblTitulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        lblTitulo.setForeground(java.awt.Color.WHITE);
        lblTitulo.setText("  Gestión de Mascotas");
        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        pnlHeaderLayout.setVerticalGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup().addGap(12).addComponent(lblTitulo).addGap(12)));

        lblIdCliente.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); lblIdCliente.setText("Cliente:");
        cmbIdCliente.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));

        lblNombre.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); lblNombre.setText("Nombre:");
        txtNombre.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        lblApellido.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); lblApellido.setText("Apellido:");
        txtApellido.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        lblEspecie.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); lblEspecie.setText("Especie:");
        cmbEspecie.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        cmbEspecie.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Canino", "Felino", "Ave", "Reptil", "Otro"}));
        lblRaza.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); lblRaza.setText("Raza:");
        txtRaza.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        lblGenero.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); lblGenero.setText("Género:");
        cmbGenero.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        cmbGenero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Macho", "Hembra"}));
        lblFechaNacimiento.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); lblFechaNacimiento.setText("Fecha Nac.:");
        spnFechaNacimiento.setEditor(new javax.swing.JSpinner.DateEditor(spnFechaNacimiento, "yyyy-MM-dd"));
        lblPeso.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); lblPeso.setText("Peso (kg):");
        spnPeso.setModel(new javax.swing.SpinnerNumberModel(0.0, 0.0, 500.0, 0.1));
        lblObservaciones.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); lblObservaciones.setText("Observaciones:");
        txaObservaciones.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        txaObservaciones.setRows(3); txaObservaciones.setLineWrap(true);
        jScrollPane2.setViewportView(txaObservaciones);

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

        tblMascotas.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        tblMascotas.setAutoCreateRowSorter(true);
        tblMascotas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblMascotas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) { tblMascotasMouseClicked(evt); }
        });
        jScrollPane1.setViewportView(tblMascotas);

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
                            .addComponent(lblIdCliente)
                            .addComponent(cmbIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombre)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblApellido)
                            .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEspecie)
                            .addComponent(cmbEspecie, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblRaza)
                            .addComponent(txtRaza, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblGenero)
                            .addComponent(cmbGenero, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFechaNacimiento)
                            .addComponent(spnFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPeso)
                            .addComponent(spnPeso, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblObservaciones)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addComponent(lblIdCliente).addComponent(lblNombre).addComponent(lblApellido)
                    .addComponent(lblEspecie).addComponent(lblRaza).addComponent(lblGenero))
                .addGap(4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cmbIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbEspecie, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRaza, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbGenero, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFechaNacimiento).addComponent(lblPeso).addComponent(lblObservaciones))
                .addGap(4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(spnFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnPeso, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        int idCliente = getIdClienteSeleccionado();
        if (idCliente == -1) { Utilidades.mostrarError(this, "Seleccione un cliente."); return; }
        String nombre = txtNombre.getText().trim();
        if (Utilidades.campoVacio(nombre)) { Utilidades.mostrarError(this, "El nombre es obligatorio."); return; }
        if (!Utilidades.validarSoloLetras(nombre)) { Utilidades.mostrarError(this, "El nombre solo puede contener letras."); return; }
        String apellidoMasc = txtApellido.getText().trim();
        if (!apellidoMasc.isEmpty() && !Utilidades.validarSoloLetras(apellidoMasc)) { Utilidades.mostrarError(this, "El apellido solo puede contener letras."); return; }
        java.util.Date fechaSeleccionada = (java.util.Date) spnFechaNacimiento.getValue();
        if (fechaSeleccionada.after(new java.util.Date())) {
            Utilidades.mostrarError(this, "La fecha de nacimiento no puede ser una fecha futura."); return;
        }
        String fechaNac = new java.text.SimpleDateFormat("yyyy-MM-dd").format(fechaSeleccionada);
        try {
            Connection con = Conexion.getConnection();
            if (con == null) return;
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO MASCOTA (id_cliente, nombre, apellido, especie, raza, genero, fecha_nacimiento, peso_aproximado, observaciones) VALUES (?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, idCliente);
            ps.setString(2, nombre);
            ps.setString(3, txtApellido.getText().trim().isEmpty() ? null : txtApellido.getText().trim());
            ps.setString(4, cmbEspecie.getSelectedItem().toString());
            ps.setString(5, txtRaza.getText().trim().isEmpty() ? null : txtRaza.getText().trim());
            ps.setString(6, cmbGenero.getSelectedItem().toString());
            ps.setString(7, fechaNac);
            ps.setDouble(8, ((Number) spnPeso.getValue()).doubleValue());
            ps.setString(9, txaObservaciones.getText().trim().isEmpty() ? null : txaObservaciones.getText().trim());
            ps.executeUpdate(); con.close();
            Utilidades.mostrarExito(this, "Mascota guardada exitosamente.");
            limpiarCampos(); cargarDatos();
        } catch (Exception e) {
            Utilidades.mostrarError(this, "Error al guardar: " + e.getMessage());
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        int fila = tblMascotas.getSelectedRow();
        if (fila == -1) { Utilidades.mostrarError(this, "Seleccione una mascota de la tabla."); return; }
        int id = (int) tblMascotas.getValueAt(fila, 0);
        int idCliente = getIdClienteSeleccionado();
        if (idCliente == -1) { Utilidades.mostrarError(this, "Seleccione un cliente."); return; }
        String nombre = txtNombre.getText().trim();
        if (Utilidades.campoVacio(nombre)) { Utilidades.mostrarError(this, "El nombre es obligatorio."); return; }
        if (!Utilidades.validarSoloLetras(nombre)) { Utilidades.mostrarError(this, "El nombre solo puede contener letras."); return; }
        String apellidoMasc = txtApellido.getText().trim();
        if (!apellidoMasc.isEmpty() && !Utilidades.validarSoloLetras(apellidoMasc)) { Utilidades.mostrarError(this, "El apellido solo puede contener letras."); return; }
        java.util.Date fechaEdit = (java.util.Date) spnFechaNacimiento.getValue();
        if (fechaEdit.after(new java.util.Date())) {
            Utilidades.mostrarError(this, "La fecha de nacimiento no puede ser una fecha futura."); return;
        }
        try {
            Connection con = Conexion.getConnection();
            if (con == null) return;
            PreparedStatement ps = con.prepareStatement(
                "UPDATE MASCOTA SET id_cliente=?, nombre=?, apellido=?, especie=?, raza=?, genero=?, fecha_nacimiento=?, peso_aproximado=?, observaciones=? WHERE id_mascota=?");
            ps.setInt(1, idCliente);
            ps.setString(2, nombre);
            ps.setString(3, txtApellido.getText().trim());
            ps.setString(4, cmbEspecie.getSelectedItem().toString());
            ps.setString(5, txtRaza.getText().trim());
            ps.setString(6, cmbGenero.getSelectedItem().toString());
            ps.setString(7, new java.text.SimpleDateFormat("yyyy-MM-dd").format(fechaEdit));
            ps.setDouble(8, ((Number) spnPeso.getValue()).doubleValue());
            ps.setString(9, txaObservaciones.getText().trim());
            ps.setInt(10, id);
            ps.executeUpdate(); con.close();
            Utilidades.mostrarExito(this, "Mascota actualizada.");
            limpiarCampos(); cargarDatos();
        } catch (Exception e) {
            Utilidades.mostrarError(this, "Error al actualizar: " + e.getMessage());
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int fila = tblMascotas.getSelectedRow();
        if (fila == -1) { Utilidades.mostrarError(this, "Seleccione una mascota de la tabla."); return; }
        int id = (int) tblMascotas.getValueAt(fila, 0);
        if (!Utilidades.confirmar(this, "¿Eliminar la mascota seleccionada?")) return;
        try {
            Connection con = Conexion.getConnection();
            if (con == null) return;
            PreparedStatement ps = con.prepareStatement("DELETE FROM MASCOTA WHERE id_mascota=?");
            ps.setInt(1, id); ps.executeUpdate(); con.close();
            Utilidades.mostrarExito(this, "Mascota eliminada.");
            limpiarCampos(); cargarDatos();
        } catch (Exception e) {
            Utilidades.mostrarError(this, "Error (puede tener citas asociadas): " + e.getMessage());
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void tblMascotasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMascotasMouseClicked
        int fila = tblMascotas.getSelectedRow();
        if (fila >= 0) {
            txtNombre.setText(obj2str(tblMascotas.getValueAt(fila, 2)));
            txtApellido.setText(obj2str(tblMascotas.getValueAt(fila, 3)));
            setCombo(cmbEspecie, obj2str(tblMascotas.getValueAt(fila, 4)));
            txtRaza.setText(obj2str(tblMascotas.getValueAt(fila, 5)));
            setCombo(cmbGenero, obj2str(tblMascotas.getValueAt(fila, 6)));
            String fnStr = obj2str(tblMascotas.getValueAt(fila, 7));
            try { spnFechaNacimiento.setValue(new java.text.SimpleDateFormat("yyyy-MM-dd").parse(fnStr)); } catch (Exception ex) { spnFechaNacimiento.setValue(new java.util.Date()); }
            Object peso = tblMascotas.getValueAt(fila, 8);
            if (peso != null) { try { spnPeso.setValue(Double.parseDouble(peso.toString())); } catch (Exception ex) {} }
            txaObservaciones.setText(obj2str(tblMascotas.getValueAt(fila, 9)));
        }
    }//GEN-LAST:event_tblMascotasMouseClicked

    private void setCombo(javax.swing.JComboBox<String> cmb, String val) {
        for (int i = 0; i < cmb.getItemCount(); i++) {
            if (cmb.getItemAt(i).equals(val)) { cmb.setSelectedIndex(i); return; }
        }
    }
    private String obj2str(Object o) { return o != null ? o.toString() : ""; }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JComboBox<String> cmbEspecie;
    private javax.swing.JComboBox<String> cmbGenero;
    private javax.swing.JComboBox<String> cmbIdCliente;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblApellido;
    private javax.swing.JLabel lblEspecie;
    private javax.swing.JLabel lblFechaNacimiento;
    private javax.swing.JLabel lblGenero;
    private javax.swing.JLabel lblIdCliente;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblObservaciones;
    private javax.swing.JLabel lblPeso;
    private javax.swing.JLabel lblRaza;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JSpinner spnFechaNacimiento;
    private javax.swing.JSpinner spnPeso;
    private javax.swing.JTable tblMascotas;
    private javax.swing.JTextArea txaObservaciones;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtRaza;
    // End of variables declaration//GEN-END:variables
}
