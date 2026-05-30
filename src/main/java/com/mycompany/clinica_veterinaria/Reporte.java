package com.mycompany.clinica_veterinaria;

import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;

public class Reporte extends javax.swing.JInternalFrame {

    public Reporte() {
        initComponents();
        // Initialize date spinners: first day of current month → today
        java.util.Calendar cal = java.util.Calendar.getInstance();
        java.util.Date today = cal.getTime();
        cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
        java.util.Date firstOfMonth = cal.getTime();
        spnFechaDesde.setValue(firstOfMonth);
        spnFechaHasta.setValue(today);
        generarReportes();
    }

    private String spnToStr(javax.swing.JSpinner spn) {
        return new SimpleDateFormat("yyyy-MM-dd").format((java.util.Date) spn.getValue());
    }

    private void generarReportes() {
        String desde = spnToStr(spnFechaDesde);
        String hasta = spnToStr(spnFechaHasta);
        cargarResumenCitas(desde, hasta);
        cargarIngresos(desde, hasta);
        cargarMascotasEspecie();
        cargarMejoresClientes(desde, hasta);
    }

    private void cargarResumenCitas(String desde, String hasta) {
        DefaultTableModel m = new DefaultTableModel(new String[]{"Estado", "Total"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblResumenCitas.setModel(m);
        try {
            Connection con = Conexion.getConnection(); if (con == null) return;
            PreparedStatement ps = con.prepareStatement(
                "SELECT estado_cita, COUNT(*) as total FROM CITA " +
                "WHERE DATE(fecha_hora) BETWEEN ? AND ? GROUP BY estado_cita ORDER BY estado_cita");
            ps.setString(1, desde); ps.setString(2, hasta);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) m.addRow(new Object[]{rs.getString("estado_cita"), rs.getInt("total")});
            con.close();
        } catch (Exception e) { Utilidades.mostrarError(this, "Error reporte citas: " + e.getMessage()); }
    }

    private void cargarIngresos(String desde, String hasta) {
        DefaultTableModel m = new DefaultTableModel(new String[]{"Servicio", "Cant. Vendida", "Total ($)"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblIngresos.setModel(m);
        try {
            Connection con = Conexion.getConnection(); if (con == null) return;
            PreparedStatement ps = con.prepareStatement(
                "SELECT s.nombre_servicio, SUM(cs.cantidad) as cantidad, " +
                "SUM(cs.cantidad * cs.costo_aplicado) as total " +
                "FROM CITA_SERVICIO cs JOIN SERVICIO s ON cs.id_servicio=s.id_servicio " +
                "JOIN CITA c ON cs.id_cita=c.id_cita " +
                "WHERE DATE(c.fecha_hora) BETWEEN ? AND ? " +
                "GROUP BY s.nombre_servicio ORDER BY total DESC");
            ps.setString(1, desde); ps.setString(2, hasta);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) m.addRow(new Object[]{rs.getString("nombre_servicio"), rs.getInt("cantidad"), rs.getBigDecimal("total")});
            con.close();
        } catch (Exception e) { Utilidades.mostrarError(this, "Error reporte ingresos: " + e.getMessage()); }
    }

    private void cargarMascotasEspecie() {
        DefaultTableModel m = new DefaultTableModel(new String[]{"Especie", "Total"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblMascotasEspecie.setModel(m);
        try {
            Connection con = Conexion.getConnection(); if (con == null) return;
            PreparedStatement ps = con.prepareStatement(
                "SELECT especie, COUNT(*) as total FROM MASCOTA GROUP BY especie ORDER BY total DESC");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) m.addRow(new Object[]{rs.getString("especie"), rs.getInt("total")});
            con.close();
        } catch (Exception e) { Utilidades.mostrarError(this, "Error reporte especies: " + e.getMessage()); }
    }

    private void cargarMejoresClientes(String desde, String hasta) {
        DefaultTableModel m = new DefaultTableModel(new String[]{"Cliente", "Total Citas"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblMejoresClientes.setModel(m);
        try {
            Connection con = Conexion.getConnection(); if (con == null) return;
            PreparedStatement ps = con.prepareStatement(
                "SELECT CONCAT(c.nombre,' ',c.apellido) as cliente, COUNT(ci.id_cita) as total " +
                "FROM CLIENTE c JOIN MASCOTA m ON c.id_cliente=m.id_cliente " +
                "JOIN CITA ci ON m.id_mascota=ci.id_mascota " +
                "WHERE DATE(ci.fecha_hora) BETWEEN ? AND ? " +
                "GROUP BY c.id_cliente ORDER BY total DESC LIMIT 10");
            ps.setString(1, desde); ps.setString(2, hasta);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) m.addRow(new Object[]{rs.getString("cliente"), rs.getInt("total")});
            con.close();
        } catch (Exception e) { Utilidades.mostrarError(this, "Error reporte clientes: " + e.getMessage()); }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblFechaDesde = new javax.swing.JLabel();
        spnFechaDesde = new javax.swing.JSpinner(new javax.swing.SpinnerDateModel());
        lblFechaHasta = new javax.swing.JLabel();
        spnFechaHasta = new javax.swing.JSpinner(new javax.swing.SpinnerDateModel());
        btnGenerar = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnlResumen = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblResumenCitas = new javax.swing.JTable();
        pnlIngresos = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblIngresos = new javax.swing.JTable();
        pnlEspecie = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblMascotasEspecie = new javax.swing.JTable();
        pnlClientes = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblMejoresClientes = new javax.swing.JTable();

        setClosable(true); setMaximizable(true); setResizable(true);
        setTitle("Reportes");

        pnlHeader.setBackground(new java.awt.Color(30, 100, 160));
        lblTitulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        lblTitulo.setForeground(java.awt.Color.WHITE);
        lblTitulo.setText("  Reportes del Sistema");
        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        pnlHeaderLayout.setVerticalGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup().addGap(12).addComponent(lblTitulo).addGap(12)));

        lblFechaDesde.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); lblFechaDesde.setText("Fecha Desde:");
        spnFechaDesde.setEditor(new javax.swing.JSpinner.DateEditor(spnFechaDesde, "yyyy-MM-dd"));
        lblFechaHasta.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); lblFechaHasta.setText("Fecha Hasta:");
        spnFechaHasta.setEditor(new javax.swing.JSpinner.DateEditor(spnFechaHasta, "yyyy-MM-dd"));

        btnGenerar.setBackground(new java.awt.Color(30, 100, 160)); btnGenerar.setForeground(java.awt.Color.WHITE);
        btnGenerar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); btnGenerar.setText("Generar");
        btnGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { btnGenerarActionPerformed(evt); }
        });

        tblResumenCitas.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        jScrollPane1.setViewportView(tblResumenCitas);
        javax.swing.GroupLayout pnlResumenLayout = new javax.swing.GroupLayout(pnlResumen);
        pnlResumen.setLayout(pnlResumenLayout);
        pnlResumenLayout.setHorizontalGroup(pnlResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        pnlResumenLayout.setVerticalGroup(pnlResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        jTabbedPane1.addTab("Resumen de Citas", pnlResumen);

        tblIngresos.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        jScrollPane2.setViewportView(tblIngresos);
        javax.swing.GroupLayout pnlIngresosLayout = new javax.swing.GroupLayout(pnlIngresos);
        pnlIngresos.setLayout(pnlIngresosLayout);
        pnlIngresosLayout.setHorizontalGroup(pnlIngresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        pnlIngresosLayout.setVerticalGroup(pnlIngresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        jTabbedPane1.addTab("Ingresos", pnlIngresos);

        tblMascotasEspecie.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        jScrollPane3.setViewportView(tblMascotasEspecie);
        javax.swing.GroupLayout pnlEspecieLayout = new javax.swing.GroupLayout(pnlEspecie);
        pnlEspecie.setLayout(pnlEspecieLayout);
        pnlEspecieLayout.setHorizontalGroup(pnlEspecieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        pnlEspecieLayout.setVerticalGroup(pnlEspecieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        jTabbedPane1.addTab("Mascotas por Especie", pnlEspecie);

        tblMejoresClientes.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        jScrollPane4.setViewportView(tblMejoresClientes);
        javax.swing.GroupLayout pnlClientesLayout = new javax.swing.GroupLayout(pnlClientes);
        pnlClientes.setLayout(pnlClientesLayout);
        pnlClientesLayout.setHorizontalGroup(pnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        pnlClientesLayout.setVerticalGroup(pnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        jTabbedPane1.addTab("Mejores Clientes", pnlClientes);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFechaDesde)
                            .addComponent(spnFechaDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFechaHasta)
                            .addComponent(spnFechaHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15)
                        .addComponent(btnGenerar))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFechaDesde).addComponent(lblFechaHasta))
                .addGap(4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnFechaDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnFechaHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGenerar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarActionPerformed
        generarReportes();
    }//GEN-LAST:event_btnGenerarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGenerar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblFechaDesde;
    private javax.swing.JLabel lblFechaHasta;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlClientes;
    private javax.swing.JPanel pnlEspecie;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlIngresos;
    private javax.swing.JPanel pnlResumen;
    private javax.swing.JSpinner spnFechaDesde;
    private javax.swing.JSpinner spnFechaHasta;
    private javax.swing.JTable tblIngresos;
    private javax.swing.JTable tblMascotasEspecie;
    private javax.swing.JTable tblMejoresClientes;
    private javax.swing.JTable tblResumenCitas;
    // End of variables declaration//GEN-END:variables
}
