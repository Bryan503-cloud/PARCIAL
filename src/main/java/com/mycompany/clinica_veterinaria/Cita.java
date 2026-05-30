package com.mycompany.clinica_veterinaria;

import java.sql.*;
import java.util.Calendar;
import javax.swing.table.DefaultTableModel;

public class Cita extends javax.swing.JInternalFrame {

    private DefaultTableModel modeloServicios;

    public Cita() {
        initComponents();
        aplicarEstilo();
        cargarCombos();
        cargarCitas();
    }

    private void aplicarEstilo() {
        EstiloUI.aplicarVentana(getContentPane(), pnlHeader,
            new javax.swing.JTable[]{tblCitas, tblServiciosCita},
            new javax.swing.JButton[]{btnGuardarCita, btnEditarCita, btnEliminarCita,
                                      btnLimpiarCita, btnAgregarServicio, btnQuitarServicio});
    }

    private void cargarCombos() {
        try {
            Connection con = Conexion.getConnection();
            if (con == null) return;

            cmbIdMascota.removeAllItems();
            PreparedStatement ps = con.prepareStatement(
                "SELECT m.id_mascota, m.nombre, c.apellido FROM MASCOTA m JOIN CLIENTE c ON m.id_cliente=c.id_cliente ORDER BY m.nombre");
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                cmbIdMascota.addItem(rs.getInt("id_mascota") + " - " + rs.getString("nombre") + " (" + rs.getString("apellido") + ")");

            cmbIdVeterinario.removeAllItems();
            ps = con.prepareStatement("SELECT id_veterinario, nombre, apellido FROM VETERINARIO ORDER BY apellido, nombre");
            rs = ps.executeQuery();
            while (rs.next())
                cmbIdVeterinario.addItem(rs.getInt("id_veterinario") + " - Dr. " + rs.getString("nombre") + " " + rs.getString("apellido"));

            cmbServicio.removeAllItems();
            ps = con.prepareStatement("SELECT id_servicio, nombre_servicio, costo_base FROM SERVICIO WHERE estado=1 ORDER BY nombre_servicio");
            rs = ps.executeQuery();
            while (rs.next())
                cmbServicio.addItem(rs.getInt("id_servicio") + " - " + rs.getString("nombre_servicio") + " ($" + rs.getBigDecimal("costo_base") + ")");

            con.close();
        } catch (Exception e) {
            Utilidades.mostrarError(this, "Error cargando combos: " + e.getMessage());
        }
    }

    private void cargarCitas() {
        DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"ID", "Mascota", "Veterinario", "Fecha/Hora", "Estado", "Motivo", "id_masc", "id_vet"}, 0
        ) { public boolean isCellEditable(int r, int c) { return false; } };
        tblCitas.setModel(modelo);
        try {
            Connection con = Conexion.getConnection();
            if (con == null) return;
            PreparedStatement ps = con.prepareStatement(
                "SELECT c.id_cita, m.nombre AS mascota, CONCAT(v.nombre,' ',v.apellido) AS veterinario, " +
                "c.fecha_hora, c.estado_cita, c.motivo_consulta, c.id_mascota, c.id_veterinario " +
                "FROM CITA c JOIN MASCOTA m ON c.id_mascota=m.id_mascota " +
                "JOIN VETERINARIO v ON c.id_veterinario=v.id_veterinario ORDER BY c.fecha_hora DESC");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{rs.getInt("id_cita"), rs.getString("mascota"),
                    rs.getString("veterinario"), rs.getString("fecha_hora"),
                    rs.getString("estado_cita"), rs.getString("motivo_consulta"),
                    rs.getInt("id_mascota"), rs.getInt("id_veterinario")});
            }
            con.close();
        } catch (Exception e) {
            Utilidades.mostrarError(this, "Error cargando citas: " + e.getMessage());
        }
        // Hide internal ID columns
        for (int col : new int[]{6, 7}) {
            tblCitas.getColumnModel().getColumn(col).setMinWidth(0);
            tblCitas.getColumnModel().getColumn(col).setMaxWidth(0);
            tblCitas.getColumnModel().getColumn(col).setWidth(0);
        }
    }

    private void cargarServiciosDeCita(int idCita) {
        modeloServicios.setRowCount(0);
        try {
            Connection con = Conexion.getConnection();
            if (con == null) return;
            PreparedStatement ps = con.prepareStatement(
                "SELECT s.id_servicio, s.nombre_servicio, cs.cantidad, cs.costo_aplicado, " +
                "(cs.cantidad * cs.costo_aplicado) AS subtotal " +
                "FROM CITA_SERVICIO cs JOIN SERVICIO s ON cs.id_servicio=s.id_servicio WHERE cs.id_cita=?");
            ps.setInt(1, idCita);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modeloServicios.addRow(new Object[]{
                    rs.getInt("id_servicio"), rs.getString("nombre_servicio"),
                    rs.getInt("cantidad"), rs.getBigDecimal("costo_aplicado"),
                    rs.getBigDecimal("subtotal")});
            }
            con.close();
        } catch (Exception e) {
            Utilidades.mostrarError(this, "Error cargando servicios: " + e.getMessage());
        }
    }

    private int extraerId(javax.swing.JComboBox<?> cmb) {
        Object sel = cmb.getSelectedItem();
        if (sel == null) return -1;
        try { return Integer.parseInt(sel.toString().split(" - ")[0]); } catch (Exception e) { return -1; }
    }

    private void setComboById(javax.swing.JComboBox<String> cmb, int id) {
        String prefix = id + " - ";
        for (int i = 0; i < cmb.getItemCount(); i++) {
            if (cmb.getItemAt(i).startsWith(prefix)) { cmb.setSelectedIndex(i); return; }
        }
    }

    private java.sql.Timestamp getCitaTimestamp() {
        java.util.Date fechaDate = (java.util.Date) spnFechaCita.getValue();
        java.util.Date horaDate = (java.util.Date) spnHoraCita.getValue();
        Calendar calFecha = Calendar.getInstance();
        calFecha.setTime(fechaDate);
        Calendar calHora = Calendar.getInstance();
        calHora.setTime(horaDate);
        calFecha.set(Calendar.HOUR_OF_DAY, calHora.get(Calendar.HOUR_OF_DAY));
        calFecha.set(Calendar.MINUTE, calHora.get(Calendar.MINUTE));
        calFecha.set(Calendar.SECOND, 0);
        calFecha.set(Calendar.MILLISECOND, 0);
        return new java.sql.Timestamp(calFecha.getTimeInMillis());
    }

    private void limpiarCampos() {
        if (cmbIdMascota.getItemCount() > 0) cmbIdMascota.setSelectedIndex(0);
        if (cmbIdVeterinario.getItemCount() > 0) cmbIdVeterinario.setSelectedIndex(0);
        java.util.Date now = new java.util.Date();
        spnFechaCita.setValue(now);
        spnHoraCita.setValue(now);
        cmbEstado.setSelectedIndex(0);
        txaObservaciones.setText("");
        modeloServicios.setRowCount(0);
        tblCitas.clearSelection();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblMascota = new javax.swing.JLabel();
        cmbIdMascota = new javax.swing.JComboBox<>();
        lblVeterinario = new javax.swing.JLabel();
        cmbIdVeterinario = new javax.swing.JComboBox<>();
        lblFechaCita = new javax.swing.JLabel();
        spnFechaCita = new javax.swing.JSpinner(new javax.swing.SpinnerDateModel());
        lblHoraCita = new javax.swing.JLabel();
        spnHoraCita = new javax.swing.JSpinner(new javax.swing.SpinnerDateModel());
        lblEstado = new javax.swing.JLabel();
        cmbEstado = new javax.swing.JComboBox<>();
        lblObservaciones = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txaObservaciones = new javax.swing.JTextArea();
        lblAgregarServicio = new javax.swing.JLabel();
        cmbServicio = new javax.swing.JComboBox<>();
        lblCantidad = new javax.swing.JLabel();
        spnCantidad = new javax.swing.JSpinner();
        btnAgregarServicio = new javax.swing.JButton();
        btnQuitarServicio = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblServiciosCita = new javax.swing.JTable();
        btnGuardarCita = new javax.swing.JButton();
        btnEditarCita = new javax.swing.JButton();
        btnEliminarCita = new javax.swing.JButton();
        btnLimpiarCita = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCitas = new javax.swing.JTable();

        setClosable(true); setMaximizable(true); setResizable(true);
        setTitle("Gestión de Citas");

        pnlHeader.setBackground(new java.awt.Color(30, 100, 160));
        lblTitulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        lblTitulo.setForeground(java.awt.Color.WHITE);
        lblTitulo.setText("  Gestión de Citas");
        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        pnlHeaderLayout.setVerticalGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup().addGap(12).addComponent(lblTitulo).addGap(12)));

        lblMascota.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); lblMascota.setText("Mascota:");
        cmbIdMascota.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        lblVeterinario.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); lblVeterinario.setText("Veterinario:");
        cmbIdVeterinario.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        lblFechaCita.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); lblFechaCita.setText("Fecha:");
        spnFechaCita.setEditor(new javax.swing.JSpinner.DateEditor(spnFechaCita, "yyyy-MM-dd"));
        lblHoraCita.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); lblHoraCita.setText("Hora:");
        spnHoraCita.setEditor(new javax.swing.JSpinner.DateEditor(spnHoraCita, "HH:mm"));
        lblEstado.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); lblEstado.setText("Estado:");
        cmbEstado.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        cmbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Programada", "Confirmada", "Atendida", "Cancelada"}));
        lblObservaciones.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); lblObservaciones.setText("Motivo:");
        txaObservaciones.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        txaObservaciones.setRows(3); txaObservaciones.setLineWrap(true);
        jScrollPane3.setViewportView(txaObservaciones);

        lblAgregarServicio.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); lblAgregarServicio.setText("Servicio:");
        cmbServicio.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        lblCantidad.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); lblCantidad.setText("Cantidad:");
        spnCantidad.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));

        btnAgregarServicio.setBackground(new java.awt.Color(30, 100, 160)); btnAgregarServicio.setForeground(java.awt.Color.WHITE);
        btnAgregarServicio.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); btnAgregarServicio.setText("+ Agregar");
        btnAgregarServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { btnAgregarServicioActionPerformed(evt); }
        });
        btnQuitarServicio.setBackground(new java.awt.Color(180, 40, 40)); btnQuitarServicio.setForeground(java.awt.Color.WHITE);
        btnQuitarServicio.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); btnQuitarServicio.setText("- Quitar");
        btnQuitarServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { btnQuitarServicioActionPerformed(evt); }
        });

        modeloServicios = new DefaultTableModel(
            new String[]{"ID_Serv", "Servicio", "Cantidad", "Costo Unit.", "Subtotal"}, 0
        ) { public boolean isCellEditable(int r, int c) { return false; } };
        tblServiciosCita.setModel(modeloServicios);
        tblServiciosCita.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 11));
        tblServiciosCita.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tblServiciosCita);

        btnGuardarCita.setBackground(new java.awt.Color(30, 100, 160)); btnGuardarCita.setForeground(java.awt.Color.WHITE);
        btnGuardarCita.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); btnGuardarCita.setText("Guardar Cita");
        btnGuardarCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { btnGuardarCitaActionPerformed(evt); }
        });
        btnEditarCita.setBackground(new java.awt.Color(50, 150, 50)); btnEditarCita.setForeground(java.awt.Color.WHITE);
        btnEditarCita.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); btnEditarCita.setText("Actualizar Cita");
        btnEditarCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { btnEditarCitaActionPerformed(evt); }
        });
        btnEliminarCita.setBackground(new java.awt.Color(180, 40, 40)); btnEliminarCita.setForeground(java.awt.Color.WHITE);
        btnEliminarCita.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); btnEliminarCita.setText("Eliminar Cita");
        btnEliminarCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { btnEliminarCitaActionPerformed(evt); }
        });
        btnLimpiarCita.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); btnLimpiarCita.setText("Limpiar");
        btnLimpiarCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { btnLimpiarCitaActionPerformed(evt); }
        });

        tblCitas.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        tblCitas.setAutoCreateRowSorter(true);
        tblCitas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblCitas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) { tblCitasMouseClicked(evt); }
        });
        jScrollPane1.setViewportView(tblCitas);

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
                            .addComponent(lblMascota)
                            .addComponent(cmbIdMascota, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblVeterinario)
                            .addComponent(cmbIdVeterinario, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFechaCita)
                            .addComponent(spnFechaCita, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblHoraCita)
                            .addComponent(spnHoraCita, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEstado)
                            .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblObservaciones)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblAgregarServicio)
                        .addGap(5)
                        .addComponent(cmbServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10)
                        .addComponent(lblCantidad)
                        .addGap(5)
                        .addComponent(spnCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10)
                        .addComponent(btnAgregarServicio)
                        .addGap(8)
                        .addComponent(btnQuitarServicio))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnGuardarCita).addGap(10)
                        .addComponent(btnEditarCita).addGap(10)
                        .addComponent(btnEliminarCita).addGap(10)
                        .addComponent(btnLimpiarCita)))
                .addGap(20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMascota).addComponent(lblVeterinario).addComponent(lblFechaCita)
                    .addComponent(lblHoraCita).addComponent(lblEstado).addComponent(lblObservaciones))
                .addGap(4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cmbIdMascota, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbIdVeterinario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnFechaCita, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnHoraCita, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblAgregarServicio)
                    .addComponent(cmbServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCantidad)
                    .addComponent(spnCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnQuitarServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarCita, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditarCita, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarCita, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiarCita, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarServicioActionPerformed
        int idServicio = extraerId(cmbServicio);
        if (idServicio == -1) { Utilidades.mostrarError(this, "Seleccione un servicio."); return; }
        String selStr = cmbServicio.getSelectedItem().toString();
        String nombreServicio = selStr.split(" - ")[1].split(" \\(")[0];
        double costo = 0;
        try { costo = Double.parseDouble(selStr.replaceAll(".*\\(\\$", "").replace(")", "")); } catch (Exception ignored) {}
        int cantidad = ((Number) spnCantidad.getValue()).intValue();
        double subtotal = costo * cantidad;
        for (int i = 0; i < modeloServicios.getRowCount(); i++) {
            if ((int) modeloServicios.getValueAt(i, 0) == idServicio) {
                Utilidades.mostrarError(this, "Este servicio ya está en la lista."); return;
            }
        }
        modeloServicios.addRow(new Object[]{idServicio, nombreServicio, cantidad, costo, subtotal});
    }//GEN-LAST:event_btnAgregarServicioActionPerformed

    private void btnQuitarServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarServicioActionPerformed
        int fila = tblServiciosCita.getSelectedRow();
        if (fila == -1) { Utilidades.mostrarError(this, "Seleccione un servicio de la lista."); return; }
        modeloServicios.removeRow(fila);
    }//GEN-LAST:event_btnQuitarServicioActionPerformed

    private void btnGuardarCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCitaActionPerformed
        int idMascota = extraerId(cmbIdMascota);
        int idVeterinario = extraerId(cmbIdVeterinario);
        if (idMascota == -1 || idVeterinario == -1) { Utilidades.mostrarError(this, "Seleccione mascota y veterinario."); return; }
        if (modeloServicios.getRowCount() == 0) { Utilidades.mostrarError(this, "Agregue al menos un servicio."); return; }
        java.sql.Timestamp fechaHora = getCitaTimestamp();
        long ahora = System.currentTimeMillis();
        // Advertir si la cita ya pasó (con 5 min de tolerancia)
        if (fechaHora.getTime() < ahora - 300_000L) {
            if (!Utilidades.confirmar(this, "La fecha/hora seleccionada ya pasó.\n¿Desea guardar la cita de todas formas?")) return;
        }
        // Bloquear si la cita es más de 2 años en el futuro
        long dosAnios = 2L * 365 * 24 * 60 * 60 * 1000;
        if (fechaHora.getTime() > ahora + dosAnios) {
            Utilidades.mostrarError(this, "La fecha de la cita no puede ser más de 2 años en el futuro."); return;
        }

        Connection con = null;
        try {
            con = Conexion.getConnection();
            if (con == null) return;
            con.setAutoCommit(false);

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO CITA (id_mascota, id_veterinario, fecha_hora, estado_cita, motivo_consulta) VALUES (?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idMascota); ps.setInt(2, idVeterinario);
            ps.setTimestamp(3, fechaHora);
            ps.setString(4, cmbEstado.getSelectedItem().toString());
            String motivo = txaObservaciones.getText().trim();
            ps.setString(5, motivo.isEmpty() ? null : motivo);
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (!keys.next()) throw new Exception("No se generó ID de cita.");
            int idCita = keys.getInt(1);

            PreparedStatement psCS = con.prepareStatement(
                "INSERT INTO CITA_SERVICIO (id_cita, id_servicio, cantidad, costo_aplicado) VALUES (?,?,?,?)");
            for (int i = 0; i < modeloServicios.getRowCount(); i++) {
                psCS.setInt(1, idCita);
                psCS.setInt(2, (int) modeloServicios.getValueAt(i, 0));
                psCS.setInt(3, ((Number) modeloServicios.getValueAt(i, 2)).intValue());
                psCS.setDouble(4, ((Number) modeloServicios.getValueAt(i, 3)).doubleValue());
                psCS.addBatch();
            }
            psCS.executeBatch();
            con.commit(); con.close();
            Utilidades.mostrarExito(this, "Cita guardada exitosamente.");
            limpiarCampos(); cargarCitas();
        } catch (Exception e) {
            try { if (con != null) { con.rollback(); con.close(); } } catch (Exception ex) {}
            Utilidades.mostrarError(this, "Error al guardar cita: " + e.getMessage());
        }
    }//GEN-LAST:event_btnGuardarCitaActionPerformed

    private void btnEditarCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarCitaActionPerformed
        int fila = tblCitas.getSelectedRow();
        if (fila == -1) { Utilidades.mostrarError(this, "Seleccione una cita de la tabla."); return; }
        int idCita = (int) tblCitas.getValueAt(fila, 0);
        if (extraerId(cmbIdMascota) == -1 || extraerId(cmbIdVeterinario) == -1) {
            Utilidades.mostrarError(this, "Seleccione mascota y veterinario."); return;
        }
        if (modeloServicios.getRowCount() == 0) {
            Utilidades.mostrarError(this, "La cita debe tener al menos un servicio."); return;
        }
        java.sql.Timestamp fechaHora = getCitaTimestamp();

        Connection con = null;
        try {
            con = Conexion.getConnection();
            if (con == null) return;
            con.setAutoCommit(false);

            PreparedStatement ps = con.prepareStatement(
                "UPDATE CITA SET id_mascota=?, id_veterinario=?, fecha_hora=?, estado_cita=?, motivo_consulta=? WHERE id_cita=?");
            ps.setInt(1, extraerId(cmbIdMascota)); ps.setInt(2, extraerId(cmbIdVeterinario));
            ps.setTimestamp(3, fechaHora);
            ps.setString(4, cmbEstado.getSelectedItem().toString());
            ps.setString(5, txaObservaciones.getText().trim()); ps.setInt(6, idCita);
            ps.executeUpdate();

            PreparedStatement psDel = con.prepareStatement("DELETE FROM CITA_SERVICIO WHERE id_cita=?");
            psDel.setInt(1, idCita); psDel.executeUpdate();

            if (modeloServicios.getRowCount() > 0) {
                PreparedStatement psCS = con.prepareStatement(
                    "INSERT INTO CITA_SERVICIO (id_cita, id_servicio, cantidad, costo_aplicado) VALUES (?,?,?,?)");
                for (int i = 0; i < modeloServicios.getRowCount(); i++) {
                    psCS.setInt(1, idCita);
                    psCS.setInt(2, (int) modeloServicios.getValueAt(i, 0));
                    psCS.setInt(3, ((Number) modeloServicios.getValueAt(i, 2)).intValue());
                    psCS.setDouble(4, ((Number) modeloServicios.getValueAt(i, 3)).doubleValue());
                    psCS.addBatch();
                }
                psCS.executeBatch();
            }
            con.commit(); con.close();
            Utilidades.mostrarExito(this, "Cita actualizada.");
            limpiarCampos(); cargarCitas();
        } catch (Exception e) {
            try { if (con != null) { con.rollback(); con.close(); } } catch (Exception ex) {}
            Utilidades.mostrarError(this, "Error al actualizar: " + e.getMessage());
        }
    }//GEN-LAST:event_btnEditarCitaActionPerformed

    private void btnEliminarCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCitaActionPerformed
        int fila = tblCitas.getSelectedRow();
        if (fila == -1) { Utilidades.mostrarError(this, "Seleccione una cita de la tabla."); return; }
        int idCita = (int) tblCitas.getValueAt(fila, 0);
        if (!Utilidades.confirmar(this, "¿Eliminar la cita y sus servicios asociados?")) return;
        Connection con = null;
        try {
            con = Conexion.getConnection();
            if (con == null) return;
            con.setAutoCommit(false);
            PreparedStatement psDel = con.prepareStatement("DELETE FROM CITA_SERVICIO WHERE id_cita=?");
            psDel.setInt(1, idCita); psDel.executeUpdate();
            PreparedStatement ps = con.prepareStatement("DELETE FROM CITA WHERE id_cita=?");
            ps.setInt(1, idCita); ps.executeUpdate();
            con.commit(); con.close();
            Utilidades.mostrarExito(this, "Cita eliminada.");
            limpiarCampos(); cargarCitas();
        } catch (Exception e) {
            try { if (con != null) { con.rollback(); con.close(); } } catch (Exception ex) {}
            Utilidades.mostrarError(this, "Error al eliminar: " + e.getMessage());
        }
    }//GEN-LAST:event_btnEliminarCitaActionPerformed

    private void btnLimpiarCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarCitaActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarCitaActionPerformed

    private void tblCitasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCitasMouseClicked
        int fila = tblCitas.getSelectedRow();
        if (fila >= 0) {
            int idCita = (int) tblCitas.getValueAt(fila, 0);
            String fechaHoraStr = obj2str(tblCitas.getValueAt(fila, 3));
            try {
                java.util.Date dt = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fechaHoraStr);
                spnFechaCita.setValue(dt);
                spnHoraCita.setValue(dt);
            } catch (Exception ex) {}
            setCombo(cmbEstado, obj2str(tblCitas.getValueAt(fila, 4)));
            txaObservaciones.setText(obj2str(tblCitas.getValueAt(fila, 5)));
            Object idMascObj = tblCitas.getValueAt(fila, 6);
            Object idVetObj  = tblCitas.getValueAt(fila, 7);
            if (idMascObj != null) setComboById(cmbIdMascota,     (int) idMascObj);
            if (idVetObj  != null) setComboById(cmbIdVeterinario, (int) idVetObj);
            cargarServiciosDeCita(idCita);
        }
    }//GEN-LAST:event_tblCitasMouseClicked

    private void setCombo(javax.swing.JComboBox<String> cmb, String val) {
        for (int i = 0; i < cmb.getItemCount(); i++) {
            if (cmb.getItemAt(i).equals(val)) { cmb.setSelectedIndex(i); return; }
        }
    }
    private String obj2str(Object o) { return o != null ? o.toString() : ""; }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarServicio;
    private javax.swing.JButton btnEditarCita;
    private javax.swing.JButton btnEliminarCita;
    private javax.swing.JButton btnGuardarCita;
    private javax.swing.JButton btnLimpiarCita;
    private javax.swing.JButton btnQuitarServicio;
    private javax.swing.JComboBox<String> cmbEstado;
    private javax.swing.JComboBox<String> cmbIdMascota;
    private javax.swing.JComboBox<String> cmbIdVeterinario;
    private javax.swing.JComboBox<String> cmbServicio;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblAgregarServicio;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblFechaCita;
    private javax.swing.JLabel lblHoraCita;
    private javax.swing.JLabel lblMascota;
    private javax.swing.JLabel lblObservaciones;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblVeterinario;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JSpinner spnCantidad;
    private javax.swing.JSpinner spnFechaCita;
    private javax.swing.JSpinner spnHoraCita;
    private javax.swing.JTable tblCitas;
    private javax.swing.JTable tblServiciosCita;
    private javax.swing.JTextArea txaObservaciones;
    // End of variables declaration//GEN-END:variables
}
