package com.mycompany.clinica_veterinaria;

import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class GestionUsuarios extends javax.swing.JInternalFrame {

    public GestionUsuarios() {
        initComponents();
        aplicarEstilo();
        cargarDatos();
    }

    private void aplicarEstilo() {
        EstiloUI.aplicarVentana(getContentPane(), pnlHeader,
            new javax.swing.JTable[]{tblUsuarios},
            new javax.swing.JButton[]{btnGuardar, btnEditar, btnEliminar, btnLimpiar});
    }

    private void cargarDatos() {
        DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"ID", "Username", "Email", "Rol", "Estado"}, 0
        ) { public boolean isCellEditable(int r, int c) { return false; } };
        tblUsuarios.setModel(modelo);
        try {
            Connection con = Conexion.getConnection();
            if (con == null) return;
            PreparedStatement ps = con.prepareStatement(
                "SELECT id_usuario, username, email, rol, estado FROM USUARIO ORDER BY username");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id_usuario"), rs.getString("username"),
                    rs.getString("email"), rs.getString("rol"),
                    rs.getBoolean("estado") ? "Activo" : "Inactivo"
                });
            }
            con.close();
        } catch (Exception e) {
            Utilidades.mostrarError(this, "Error cargando usuarios: " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        txtUsername.setText(""); txtPassword.setText(""); txtNombreCompleto.setText("");
        cmbRol.setSelectedIndex(0); chkEstado.setSelected(true); tblUsuarios.clearSelection();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        lblUsername = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        lblNombreCompleto = new javax.swing.JLabel();
        txtNombreCompleto = new javax.swing.JTextField();
        lblRol = new javax.swing.JLabel();
        cmbRol = new javax.swing.JComboBox<>();
        chkEstado = new javax.swing.JCheckBox();
        btnGuardar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Gestión de Usuarios");

        pnlHeader.setBackground(new java.awt.Color(30, 100, 160));

        lblUsername.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUsername.setText("Username:");

        lblPassword.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblPassword.setText("Contraseña:");

        lblNombreCompleto.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNombreCompleto.setText("Email:");

        lblRol.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblRol.setText("Rol:");

        cmbRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Veterinario", "Recepcionista" }));

        chkEstado.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        chkEstado.setSelected(true);
        chkEstado.setText("Activo");

        btnGuardar.setBackground(new java.awt.Color(30, 100, 160));
        btnGuardar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnEditar.setBackground(new java.awt.Color(50, 150, 50));
        btnEditar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEditar.setForeground(new java.awt.Color(255, 255, 255));
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(180, 40, 40));
        btnEliminar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnLimpiar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        tblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsuarios);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblUsername)
                                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblPassword)
                                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNombreCompleto)
                                    .addComponent(txtNombreCompleto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblRol)
                                    .addComponent(cmbRol, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(15, 15, 15)
                                .addComponent(chkEstado))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnGuardar)
                                .addGap(10, 10, 10)
                                .addComponent(btnEditar)
                                .addGap(10, 10, 10)
                                .addComponent(btnEliminar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnLimpiar)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsername)
                    .addComponent(lblPassword)
                    .addComponent(lblNombreCompleto)
                    .addComponent(lblRol))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreCompleto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbRol, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkEstado))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1)
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String email = txtNombreCompleto.getText().trim();
        if (Utilidades.campoVacio(username) || Utilidades.campoVacio(password) || Utilidades.campoVacio(email)) {
            Utilidades.mostrarError(this, "Username, contraseña y email son obligatorios."); return;
        }
        if (!Utilidades.validarLongitud(username, 3, 50)) {
            Utilidades.mostrarError(this, "El username debe tener entre 3 y 50 caracteres."); return;
        }
        if (!Utilidades.validarLongitud(password, 6, 100)) {
            Utilidades.mostrarError(this, "La contraseña debe tener al menos 6 caracteres."); return;
        }
        if (!Utilidades.validarEmail(email)) { Utilidades.mostrarError(this, "Email inválido."); return; }
        String rol = (String) cmbRol.getSelectedItem();
        try {
            Connection con = Conexion.getConnection();
            if (con == null) return;
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO USUARIO (username, password_hash, email, rol, estado) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, username);
            ps.setString(2, Utilidades.hashSHA256(password));
            ps.setString(3, email);
            ps.setString(4, rol);
            ps.setBoolean(5, chkEstado.isSelected());
            ps.executeUpdate(); con.close();
            Utilidades.mostrarExito(this, "Usuario creado exitosamente.");
            limpiarCampos(); cargarDatos();
        } catch (Exception e) {
            Utilidades.mostrarError(this, "Error (username duplicado o error de BD): " + e.getMessage());
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        int fila = tblUsuarios.getSelectedRow();
        if (fila == -1) { Utilidades.mostrarError(this, "Seleccione un usuario de la tabla."); return; }
        int id = (int) tblUsuarios.getValueAt(fila, 0);
        String email = txtNombreCompleto.getText().trim();
        if (Utilidades.campoVacio(email)) { Utilidades.mostrarError(this, "El email es obligatorio."); return; }
        if (!Utilidades.validarEmail(email)) { Utilidades.mostrarError(this, "Email inválido."); return; }
        String rol = (String) cmbRol.getSelectedItem();
        try {
            Connection con = Conexion.getConnection();
            if (con == null) return;
            String password = new String(txtPassword.getPassword()).trim();
            if (!password.isEmpty() && !Utilidades.validarLongitud(password, 6, 100)) {
                Utilidades.mostrarError(this, "La nueva contraseña debe tener al menos 6 caracteres."); return;
            }
            if (!password.isEmpty()) {
                PreparedStatement ps = con.prepareStatement(
                    "UPDATE USUARIO SET email=?, rol=?, estado=?, password_hash=? WHERE id_usuario=?");
                ps.setString(1, email);
                ps.setString(2, rol);
                ps.setBoolean(3, chkEstado.isSelected());
                ps.setString(4, Utilidades.hashSHA256(password)); ps.setInt(5, id);
                ps.executeUpdate();
            } else {
                PreparedStatement ps = con.prepareStatement(
                    "UPDATE USUARIO SET email=?, rol=?, estado=? WHERE id_usuario=?");
                ps.setString(1, email);
                ps.setString(2, rol);
                ps.setBoolean(3, chkEstado.isSelected()); ps.setInt(4, id);
                ps.executeUpdate();
            }
            con.close();
            Utilidades.mostrarExito(this, "Usuario actualizado.");
            limpiarCampos(); cargarDatos();
        } catch (Exception e) {
            Utilidades.mostrarError(this, "Error al actualizar: " + e.getMessage());
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int fila = tblUsuarios.getSelectedRow();
        if (fila == -1) { Utilidades.mostrarError(this, "Seleccione un usuario de la tabla."); return; }
        int id = (int) tblUsuarios.getValueAt(fila, 0);
        if (id == Main.currentUserId) { Utilidades.mostrarError(this, "No puede eliminar su propio usuario."); return; }
        if (!Utilidades.confirmar(this, "¿Eliminar al usuario seleccionado?")) return;
        try {
            Connection con = Conexion.getConnection();
            if (con == null) return;
            PreparedStatement ps = con.prepareStatement("DELETE FROM USUARIO WHERE id_usuario=?");
            ps.setInt(1, id); ps.executeUpdate(); con.close();
            Utilidades.mostrarExito(this, "Usuario eliminado.");
            limpiarCampos(); cargarDatos();
        } catch (Exception e) {
            Utilidades.mostrarError(this, "Error al eliminar: " + e.getMessage());
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void tblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuariosMouseClicked
        int fila = tblUsuarios.getSelectedRow();
        if (fila >= 0) {
            txtUsername.setText(obj2str(tblUsuarios.getValueAt(fila, 1)));
            txtPassword.setText("");
            txtNombreCompleto.setText(obj2str(tblUsuarios.getValueAt(fila, 2)));
            String rolFila = obj2str(tblUsuarios.getValueAt(fila, 3));
            cmbRol.setSelectedItem(rolFila);
            chkEstado.setSelected("Activo".equals(obj2str(tblUsuarios.getValueAt(fila, 4))));
        }
    }//GEN-LAST:event_tblUsuariosMouseClicked

    private String obj2str(Object o) { return o != null ? o.toString() : ""; }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JCheckBox chkEstado;
    private javax.swing.JComboBox<String> cmbRol;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblNombreCompleto;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblRol;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtNombreCompleto;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
