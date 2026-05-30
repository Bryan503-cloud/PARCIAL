package com.mycompany.clinica_veterinaria;

import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class Veterinario extends javax.swing.JInternalFrame {

    public Veterinario() {
        initComponents();
        aplicarEstilo();
        aplicarFiltros();
        cargarDatos();
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = jTable1.getSelectedRow();
                if (fila >= 0) {
                    txtLicencia.setText(obj2str(jTable1.getValueAt(fila, 1)));
                    txtNombre.setText(obj2str(jTable1.getValueAt(fila, 2)));
                    txtApellido.setText(obj2str(jTable1.getValueAt(fila, 3)));
                    txtCorreo.setText(obj2str(jTable1.getValueAt(fila, 4)));
                    txtDui.setText(obj2str(jTable1.getValueAt(fila, 5)));
                    txtTelefono.setText(obj2str(jTable1.getValueAt(fila, 6)));
                    txtEspecialidad.setText(obj2str(jTable1.getValueAt(fila, 7)));
                }
            }
        });
    }

    private void aplicarEstilo() {
        // Solo renderer de tabla — todo lo demás está en el GEN block
        EstiloUI.aplicarVentana(getContentPane(), pnlHeader,
            new javax.swing.JTable[]{jTable1},
            new javax.swing.JButton[]{btnGuardar, btnEditar, btnEliminar, btnLimpiar});
    }

    private void aplicarFiltros() {
        Utilidades.soloLetras(txtNombre);
        Utilidades.soloLetras(txtApellido);
    }

    private void limpiarCampos() {
        txtLicencia.setText(""); txtNombre.setText(""); txtApellido.setText("");
        txtCorreo.setText(""); txtDui.setText(""); txtTelefono.setText("");
        txtEspecialidad.setText(""); jTable1.clearSelection();
    }

    private void cargarDatos() {
        DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"ID","Licencia","Nombre","Apellido","Correo","DUI","Teléfono","Especialidad"}, 0
        ) { public boolean isCellEditable(int r, int c) { return false; } };
        jTable1.setModel(modelo);
        try {
            Connection con = Conexion.getConnection();
            if (con != null) {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(
                    "SELECT id_veterinario, numero_licencia, nombre, apellido, correo, dui, telefono, especialidad FROM VETERINARIO ORDER BY apellido, nombre");
                while (rs.next()) {
                    modelo.addRow(new Object[]{
                        rs.getInt("id_veterinario"), rs.getString("numero_licencia"),
                        rs.getString("nombre"), rs.getString("apellido"),
                        rs.getString("correo"), rs.getString("dui"),
                        rs.getString("telefono"), rs.getString("especialidad")});
                }
                con.close();
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Error al cargar datos: " + e.getMessage(), "Error",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private String obj2str(Object o) { return o != null ? o.toString() : ""; }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader      = new javax.swing.JPanel();
        lblTitulo      = new javax.swing.JLabel();
        lblLicencia    = new javax.swing.JLabel();
        txtLicencia    = new javax.swing.JTextField();
        lblNombre      = new javax.swing.JLabel();
        txtNombre      = new javax.swing.JTextField();
        lblApellido    = new javax.swing.JLabel();
        txtApellido    = new javax.swing.JTextField();
        lblCorreo      = new javax.swing.JLabel();
        txtCorreo      = new javax.swing.JTextField();
        lblDui         = new javax.swing.JLabel();
        txtDui         = new javax.swing.JTextField();
        lblTelefono    = new javax.swing.JLabel();
        txtTelefono    = new javax.swing.JTextField();
        lblEspecialidad= new javax.swing.JLabel();
        txtEspecialidad= new javax.swing.JTextField();
        btnGuardar     = new javax.swing.JButton();
        btnEditar      = new javax.swing.JButton();
        btnEliminar    = new javax.swing.JButton();
        btnLimpiar     = new javax.swing.JButton();
        jScrollPane1   = new javax.swing.JScrollPane();
        jTable1        = new javax.swing.JTable();

        setClosable(true); setMaximizable(true); setResizable(true);
        setTitle("Gestión de Veterinarios");

        // ── Header ──────────────────────────────────────────────────────────
        pnlHeader.setBackground(new java.awt.Color(31, 78, 121));
        lblTitulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        lblTitulo.setForeground(java.awt.Color.WHITE);
        lblTitulo.setText("  Gestión de Veterinarios");
        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGap(12).addComponent(lblTitulo).addGap(12)));

        // ── Labels ──────────────────────────────────────────────────────────
        lblLicencia.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        lblLicencia.setText("N° Licencia:");
        lblNombre.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        lblNombre.setText("Nombre:");
        lblApellido.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        lblApellido.setText("Apellido:");
        lblCorreo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        lblCorreo.setText("Correo:");
        lblDui.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        lblDui.setText("DUI:");
        lblTelefono.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        lblTelefono.setText("Teléfono:");
        lblEspecialidad.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        lblEspecialidad.setText("Especialidad:");

        // ── Text Fields ─────────────────────────────────────────────────────
        txtLicencia.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        txtLicencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { txtLicenciaActionPerformed(evt); }
        });
        txtNombre.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        txtApellido.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        txtCorreo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        txtDui.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        txtTelefono.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        txtEspecialidad.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));

        // ── Buttons ─────────────────────────────────────────────────────────
        btnGuardar.setBackground(new java.awt.Color(30, 100, 160));
        btnGuardar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        btnGuardar.setForeground(java.awt.Color.WHITE);
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { btnGuardarActionPerformed(evt); }
        });
        btnEditar.setBackground(new java.awt.Color(50, 150, 50));
        btnEditar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        btnEditar.setForeground(java.awt.Color.WHITE);
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { btnEditarActionPerformed(evt); }
        });
        btnEliminar.setBackground(new java.awt.Color(180, 40, 40));
        btnEliminar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        btnEliminar.setForeground(java.awt.Color.WHITE);
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { btnEliminarActionPerformed(evt); }
        });
        btnLimpiar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { btnLimpiarActionPerformed(evt); }
        });

        // ── Table ───────────────────────────────────────────────────────────
        jTable1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        jTable1.setAutoCreateRowSorter(true);
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jTable1);

        // ── Layout ──────────────────────────────────────────────────────────
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblLicencia)
                            .addComponent(txtLicencia, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombre)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblApellido)
                            .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCorreo)
                            .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDui)
                            .addComponent(txtDui, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTelefono)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEspecialidad)
                            .addComponent(txtEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLicencia).addComponent(lblNombre).addComponent(lblApellido)
                    .addComponent(lblCorreo).addComponent(lblDui)
                    .addComponent(lblTelefono).addComponent(lblEspecialidad))
                .addGap(4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLicencia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDui, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtLicenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLicenciaActionPerformed
    }//GEN-LAST:event_txtLicenciaActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        String licencia    = txtLicencia.getText().trim();
        String nombre      = txtNombre.getText().trim();
        String apellido    = txtApellido.getText().trim();
        String correo      = txtCorreo.getText().trim();
        String dui         = txtDui.getText().trim();
        String telefono    = txtTelefono.getText().trim();
        String especialidad = txtEspecialidad.getText().trim();

        if (Utilidades.campoVacio(licencia)) { Utilidades.mostrarError(this, "El número de licencia es obligatorio."); return; }
        if (!Utilidades.validarLongitud(licencia, 3, 30)) { Utilidades.mostrarError(this, "La licencia debe tener entre 3 y 30 caracteres."); return; }
        if (Utilidades.campoVacio(nombre)) { Utilidades.mostrarError(this, "El nombre es obligatorio."); return; }
        if (!Utilidades.validarSoloLetras(nombre)) { Utilidades.mostrarError(this, "El nombre solo puede contener letras."); return; }
        if (Utilidades.campoVacio(apellido)) { Utilidades.mostrarError(this, "El apellido es obligatorio."); return; }
        if (!Utilidades.validarSoloLetras(apellido)) { Utilidades.mostrarError(this, "El apellido solo puede contener letras."); return; }
        if (!dui.isEmpty() && !Utilidades.validarDUI(dui)) { Utilidades.mostrarError(this, "DUI inválido. Formato: 00000000-0"); return; }
        if (!correo.isEmpty() && !Utilidades.validarEmail(correo)) { Utilidades.mostrarError(this, "Correo electrónico inválido."); return; }
        if (!telefono.isEmpty() && !Utilidades.validarTelefono(telefono)) { Utilidades.mostrarError(this, "Teléfono inválido (7-15 dígitos)."); return; }

        try {
            Connection con = Conexion.getConnection();
            PreparedStatement chk = con.prepareStatement("SELECT COUNT(*) FROM VETERINARIO WHERE numero_licencia = ?");
            chk.setString(1, licencia);
            ResultSet rsChk = chk.executeQuery();
            if (rsChk.next() && rsChk.getInt(1) > 0) { Utilidades.mostrarError(this, "Ya existe un veterinario con esa licencia."); con.close(); return; }

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO VETERINARIO (id_usuario, numero_licencia, nombre, apellido, correo, dui, telefono, especialidad) VALUES (?,?,?,?,?,?,?,?)");
            ps.setInt(1, Main.currentUserId);
            ps.setString(2, licencia); ps.setString(3, nombre); ps.setString(4, apellido);
            ps.setString(5, correo.isEmpty() ? null : correo);
            ps.setString(6, dui.isEmpty() ? null : dui);
            ps.setString(7, telefono.isEmpty() ? null : telefono);
            ps.setString(8, especialidad.isEmpty() ? null : especialidad);
            ps.executeUpdate(); con.close();
            Utilidades.mostrarExito(this, "Veterinario guardado exitosamente.");
            limpiarCampos(); cargarDatos();
        } catch (Exception e) {
            Utilidades.mostrarError(this, "Error al guardar: " + e.getMessage());
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        int fila = jTable1.getSelectedRow();
        if (fila == -1) { Utilidades.mostrarError(this, "Seleccione un veterinario de la tabla para editar."); return; }
        int idVet = Integer.parseInt(jTable1.getValueAt(fila, 0).toString());

        String licencia    = txtLicencia.getText().trim();
        String nombre      = txtNombre.getText().trim();
        String apellido    = txtApellido.getText().trim();
        String correo      = txtCorreo.getText().trim();
        String dui         = txtDui.getText().trim();
        String telefono    = txtTelefono.getText().trim();
        String especialidad = txtEspecialidad.getText().trim();

        if (Utilidades.campoVacio(licencia)) { Utilidades.mostrarError(this, "El número de licencia es obligatorio."); return; }
        if (!Utilidades.validarLongitud(licencia, 3, 30)) { Utilidades.mostrarError(this, "La licencia debe tener entre 3 y 30 caracteres."); return; }
        if (Utilidades.campoVacio(nombre)) { Utilidades.mostrarError(this, "El nombre es obligatorio."); return; }
        if (!Utilidades.validarSoloLetras(nombre)) { Utilidades.mostrarError(this, "El nombre solo puede contener letras."); return; }
        if (Utilidades.campoVacio(apellido)) { Utilidades.mostrarError(this, "El apellido es obligatorio."); return; }
        if (!Utilidades.validarSoloLetras(apellido)) { Utilidades.mostrarError(this, "El apellido solo puede contener letras."); return; }
        if (!dui.isEmpty() && !Utilidades.validarDUI(dui)) { Utilidades.mostrarError(this, "DUI inválido. Formato: 00000000-0"); return; }
        if (!correo.isEmpty() && !Utilidades.validarEmail(correo)) { Utilidades.mostrarError(this, "Correo electrónico inválido."); return; }
        if (!telefono.isEmpty() && !Utilidades.validarTelefono(telefono)) { Utilidades.mostrarError(this, "Teléfono inválido (7-15 dígitos)."); return; }

        try {
            Connection con = Conexion.getConnection();
            PreparedStatement chk = con.prepareStatement("SELECT COUNT(*) FROM VETERINARIO WHERE numero_licencia = ? AND id_veterinario != ?");
            chk.setString(1, licencia); chk.setInt(2, idVet);
            ResultSet rsChk = chk.executeQuery();
            if (rsChk.next() && rsChk.getInt(1) > 0) { Utilidades.mostrarError(this, "Otro veterinario ya usa esa licencia."); con.close(); return; }

            PreparedStatement ps = con.prepareStatement(
                "UPDATE VETERINARIO SET numero_licencia=?, nombre=?, apellido=?, correo=?, dui=?, telefono=?, especialidad=? WHERE id_veterinario=?");
            ps.setString(1, licencia); ps.setString(2, nombre); ps.setString(3, apellido);
            ps.setString(4, correo.isEmpty() ? null : correo);
            ps.setString(5, dui.isEmpty() ? null : dui);
            ps.setString(6, telefono.isEmpty() ? null : telefono);
            ps.setString(7, especialidad.isEmpty() ? null : especialidad);
            ps.setInt(8, idVet);
            ps.executeUpdate(); con.close();
            Utilidades.mostrarExito(this, "Veterinario actualizado exitosamente.");
            limpiarCampos(); cargarDatos();
        } catch (Exception e) {
            Utilidades.mostrarError(this, "Error al editar: " + e.getMessage());
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int fila = jTable1.getSelectedRow();
        if (fila == -1) { Utilidades.mostrarError(this, "Seleccione un veterinario de la tabla."); return; }
        int idVet = Integer.parseInt(jTable1.getValueAt(fila, 0).toString());
        if (!Utilidades.confirmar(this, "¿Eliminar al veterinario seleccionado?")) return;
        try {
            Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM VETERINARIO WHERE id_veterinario=?");
            ps.setInt(1, idVet); ps.executeUpdate(); con.close();
            Utilidades.mostrarExito(this, "Veterinario eliminado.");
            limpiarCampos(); cargarDatos();
        } catch (Exception e) {
            Utilidades.mostrarError(this, "Error al eliminar (puede tener citas asignadas): " + e.getMessage());
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblApellido;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JLabel lblDui;
    private javax.swing.JLabel lblEspecialidad;
    private javax.swing.JLabel lblLicencia;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDui;
    private javax.swing.JTextField txtEspecialidad;
    private javax.swing.JTextField txtLicencia;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
