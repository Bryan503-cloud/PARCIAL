package com.mycompany.clinica_veterinaria;

public class Main extends javax.swing.JFrame {

    public static int    currentUserId;
    public static String currentUsername;
    public static String currentRol;   // "Administrador" | "Veterinario" | "Recepcionista"

    public Main(int idUsuario, String username, String rol) {
        currentUserId   = idUsuario;
        currentUsername = username;
        currentRol      = rol;
        initComponents();
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        lblUsuarioActual.setText("  Usuario: " + username + "  |  " + rol + "  |  Clínica Veterinaria");
        aplicarPermisos();
        aplicarEstiloMain();
    }

    private void aplicarEstiloMain() {
        jDesktopPane1.setBackground(new java.awt.Color(220, 232, 245));
        pnlStatus.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 0, 0, 0, new java.awt.Color(10, 60, 120)));
        lblUsuarioActual.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
    }

    /** Muestra u oculta menús según el rol del usuario autenticado. */
    private void aplicarPermisos() {
        boolean esAdmin = "Administrador".equals(currentRol);
        boolean esVet   = "Veterinario".equals(currentRol);

        // ── Administración (solo Admin) ──────────────────
        menuAdministracion.setVisible(esAdmin);

        // ── Registros ───────────────────────────────────
        // Clientes y Mascotas → todos
        mniVeterinarios.setVisible(esAdmin);          // solo Admin
        mniServicios.setVisible(esAdmin || esVet);    // Admin + Veterinario

        // ── Reportes ────────────────────────────────────
        mniReportes.setVisible(esAdmin);              // reportes financieros: solo Admin
        mniGraficos.setVisible(esAdmin || esVet);     // gráficos: Admin + Veterinario
        menuReportes.setVisible(esAdmin || esVet);    // ocultar menú completo si no aplica
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        pnlStatus = new javax.swing.JPanel();
        lblUsuarioActual = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuAdministracion = new javax.swing.JMenu();
        mniGestionUsuarios = new javax.swing.JMenuItem();
        menuRegistros = new javax.swing.JMenu();
        mniClientes = new javax.swing.JMenuItem();
        mniMascotas = new javax.swing.JMenuItem();
        mniVeterinarios = new javax.swing.JMenuItem();
        mniServicios = new javax.swing.JMenuItem();
        menuOperaciones = new javax.swing.JMenu();
        mniCitas = new javax.swing.JMenuItem();
        menuReportes = new javax.swing.JMenu();
        mniReportes = new javax.swing.JMenuItem();
        mniGraficos = new javax.swing.JMenuItem();
        menuSesion = new javax.swing.JMenu();
        mniCerrarSesion = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Clínica Veterinaria - Sistema de Gestión");

        jDesktopPane1.setBackground(new java.awt.Color(220, 235, 245));

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1024, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 650, Short.MAX_VALUE)
        );

        pnlStatus.setBackground(new java.awt.Color(30, 100, 160));

        lblUsuarioActual.setForeground(new java.awt.Color(255, 255, 255));
        lblUsuarioActual.setText("Usuario: -");

        javax.swing.GroupLayout pnlStatusLayout = new javax.swing.GroupLayout(pnlStatus);
        pnlStatus.setLayout(pnlStatusLayout);
        pnlStatusLayout.setHorizontalGroup(
            pnlStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStatusLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblUsuarioActual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        pnlStatusLayout.setVerticalGroup(
            pnlStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStatusLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(lblUsuarioActual)
                .addGap(4, 4, 4))
        );

        menuAdministracion.setText("Administración");

        mniGestionUsuarios.setText("Gestión de Usuarios");
        mniGestionUsuarios.addActionListener(this::mniGestionUsuariosActionPerformed);
        menuAdministracion.add(mniGestionUsuarios);

        jMenuBar1.add(menuAdministracion);

        menuRegistros.setText("Registros");

        mniClientes.setText("Clientes");
        mniClientes.addActionListener(this::mniClientesActionPerformed);
        menuRegistros.add(mniClientes);

        mniMascotas.setText("Mascotas");
        mniMascotas.addActionListener(this::mniMascotasActionPerformed);
        menuRegistros.add(mniMascotas);

        mniVeterinarios.setText("Veterinarios");
        mniVeterinarios.addActionListener(this::mniVeterinariosActionPerformed);
        menuRegistros.add(mniVeterinarios);

        mniServicios.setText("Servicios");
        mniServicios.addActionListener(this::mniServiciosActionPerformed);
        menuRegistros.add(mniServicios);

        jMenuBar1.add(menuRegistros);

        menuOperaciones.setText("Operaciones");

        mniCitas.setText("Citas");
        mniCitas.addActionListener(this::mniCitasActionPerformed);
        menuOperaciones.add(mniCitas);

        jMenuBar1.add(menuOperaciones);

        menuReportes.setText("Reportes");

        mniReportes.setText("Reportes Administrativos");
        mniReportes.addActionListener(this::mniReportesActionPerformed);
        menuReportes.add(mniReportes);

        mniGraficos.setText("Gráficos Estadísticos");
        mniGraficos.addActionListener(this::mniGraficosActionPerformed);
        menuReportes.add(mniGraficos);

        jMenuBar1.add(menuReportes);

        menuSesion.setText("Sesión");

        mniCerrarSesion.setText("Cerrar Sesión");
        mniCerrarSesion.addActionListener(this::mniCerrarSesionActionPerformed);
        menuSesion.add(mniCerrarSesion);

        jMenuBar1.add(menuSesion);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
            .addComponent(pnlStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jDesktopPane1)
                .addGap(0, 0, 0)
                .addComponent(pnlStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void abrirVentana(javax.swing.JInternalFrame ventana) {
        for (javax.swing.JInternalFrame f : jDesktopPane1.getAllFrames()) {
            if (f.getClass().equals(ventana.getClass())) {
                f.setVisible(true);
                try { f.setSelected(true); } catch (Exception ex) {}
                return;
            }
        }
        jDesktopPane1.add(ventana);
        ventana.setVisible(true);
    }

    private void mniGestionUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniGestionUsuariosActionPerformed
        abrirVentana(new GestionUsuarios());
    }//GEN-LAST:event_mniGestionUsuariosActionPerformed

    private void mniClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniClientesActionPerformed
        abrirVentana(new Cliente());
    }//GEN-LAST:event_mniClientesActionPerformed

    private void mniMascotasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniMascotasActionPerformed
        abrirVentana(new Mascota());
    }//GEN-LAST:event_mniMascotasActionPerformed

    private void mniVeterinariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniVeterinariosActionPerformed
        abrirVentana(new Veterinario());
    }//GEN-LAST:event_mniVeterinariosActionPerformed

    private void mniServiciosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniServiciosActionPerformed
        abrirVentana(new Servicio());
    }//GEN-LAST:event_mniServiciosActionPerformed

    private void mniCitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniCitasActionPerformed
        abrirVentana(new Cita());
    }//GEN-LAST:event_mniCitasActionPerformed

    private void mniReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniReportesActionPerformed
        abrirVentana(new Reporte());
    }//GEN-LAST:event_mniReportesActionPerformed

    private void mniGraficosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniGraficosActionPerformed
        abrirVentana(new Grafico());
    }//GEN-LAST:event_mniGraficosActionPerformed

    private void mniCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniCerrarSesionActionPerformed
        if (Utilidades.confirmar(this, "¿Desea cerrar la sesión actual?")) {
            new Login().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_mniCerrarSesionActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JLabel lblUsuarioActual;
    private javax.swing.JMenu menuAdministracion;
    private javax.swing.JMenu menuOperaciones;
    private javax.swing.JMenu menuRegistros;
    private javax.swing.JMenu menuReportes;
    private javax.swing.JMenu menuSesion;
    private javax.swing.JMenuItem mniCerrarSesion;
    private javax.swing.JMenuItem mniCitas;
    private javax.swing.JMenuItem mniClientes;
    private javax.swing.JMenuItem mniGestionUsuarios;
    private javax.swing.JMenuItem mniGraficos;
    private javax.swing.JMenuItem mniMascotas;
    private javax.swing.JMenuItem mniReportes;
    private javax.swing.JMenuItem mniServicios;
    private javax.swing.JMenuItem mniVeterinarios;
    private javax.swing.JPanel pnlStatus;
    // End of variables declaration//GEN-END:variables
}
