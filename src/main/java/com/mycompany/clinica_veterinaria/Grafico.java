package com.mycompany.clinica_veterinaria;

import java.awt.*;
import java.sql.*;
import java.util.*;

public class Grafico extends javax.swing.JInternalFrame {

    private java.util.List<String[]> datosCitas = new java.util.ArrayList<>();
    private java.util.List<String[]> datosServicios = new java.util.ArrayList<>();
    private PieChartPanel piePanel;
    private BarChartPanel barPanel;

    public Grafico() {
        initComponents();
        aplicarEstilo();
        piePanel = new PieChartPanel();
        barPanel = new BarChartPanel();
        pnlGraficoCitas.setLayout(new java.awt.BorderLayout());
        pnlGraficoCitas.add(piePanel, java.awt.BorderLayout.CENTER);
        pnlGraficoServicios.setLayout(new java.awt.BorderLayout());
        pnlGraficoServicios.add(barPanel, java.awt.BorderLayout.CENTER);
        cargarDatosGrafico();
        // Repintar después de que el layout esté listo
        javax.swing.SwingUtilities.invokeLater(() -> {
            piePanel.repaint();
            barPanel.repaint();
        });
    }

    private void aplicarEstilo() {
        EstiloUI.aplicarVentana(getContentPane(), pnlHeader,
            new javax.swing.JTable[]{},
            new javax.swing.JButton[]{btnActualizar});
    }

    private class PieChartPanel extends javax.swing.JPanel {
        public PieChartPanel() { setBackground(java.awt.Color.WHITE); }
        @Override
        protected void paintComponent(java.awt.Graphics g) {
            super.paintComponent(g);
            dibujarPieChart((java.awt.Graphics2D) g, datosCitas, getWidth(), getHeight());
        }
    }

    private class BarChartPanel extends javax.swing.JPanel {
        public BarChartPanel() { setBackground(java.awt.Color.WHITE); }
        @Override
        protected void paintComponent(java.awt.Graphics g) {
            super.paintComponent(g);
            dibujarBarChart((java.awt.Graphics2D) g, datosServicios, getWidth(), getHeight());
        }
    }

    private void cargarDatosGrafico() {
        datosCitas.clear();
        datosServicios.clear();
        try {
            Connection con = Conexion.getConnection();
            if (con == null) return;
            PreparedStatement ps = con.prepareStatement(
                "SELECT estado_cita, COUNT(*) as total FROM CITA GROUP BY estado_cita ORDER BY estado_cita");
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                datosCitas.add(new String[]{rs.getString("estado_cita"), String.valueOf(rs.getInt("total"))});

            ps = con.prepareStatement(
                "SELECT s.nombre_servicio, SUM(cs.cantidad) as total FROM CITA_SERVICIO cs " +
                "JOIN SERVICIO s ON cs.id_servicio=s.id_servicio " +
                "GROUP BY s.nombre_servicio ORDER BY total DESC LIMIT 8");
            rs = ps.executeQuery();
            while (rs.next())
                datosServicios.add(new String[]{rs.getString("nombre_servicio"), String.valueOf(rs.getInt("total"))});
            con.close();
        } catch (Exception e) { Utilidades.mostrarError(this, "Error cargando datos: " + e.getMessage()); }

        if (piePanel != null) piePanel.repaint();
        if (barPanel != null) barPanel.repaint();
    }

    private void dibujarPieChart(java.awt.Graphics2D g2, java.util.List<String[]> datos, int w, int h) {
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        if (datos.isEmpty()) {
            g2.setColor(java.awt.Color.GRAY);
            g2.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
            g2.drawString("Sin datos disponibles", w / 2 - 70, h / 2);
            return;
        }
        int total = 0;
        for (String[] d : datos) total += Integer.parseInt(d[1]);
        if (total == 0) return;

        java.awt.Color[] colores = {
            new java.awt.Color(30, 100, 160), new java.awt.Color(50, 150, 50),
            new java.awt.Color(180, 40, 40), new java.awt.Color(220, 150, 0),
            new java.awt.Color(150, 50, 150), new java.awt.Color(0, 150, 150)
        };

        int size = Math.min(w - 200, h - 80);
        if (size < 60) size = 60;
        int pieX = 40, pieY = 40;

        int startAngle = 0;
        for (int i = 0; i < datos.size(); i++) {
            int val = Integer.parseInt(datos.get(i)[1]);
            int arc = (i == datos.size() - 1) ? (360 - startAngle) : (int) Math.round(360.0 * val / total);
            g2.setColor(colores[i % colores.length]);
            g2.fillArc(pieX, pieY, size, size, startAngle, arc);
            g2.setColor(java.awt.Color.WHITE);
            g2.setStroke(new java.awt.BasicStroke(1.5f));
            g2.drawArc(pieX, pieY, size, size, startAngle, arc);
            startAngle += arc;
        }

        int legX = pieX + size + 20, legY = pieY + 10;
        g2.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 11));
        for (int i = 0; i < datos.size(); i++) {
            g2.setColor(colores[i % colores.length]);
            g2.fillRect(legX, legY + i * 24, 16, 16);
            g2.setColor(java.awt.Color.DARK_GRAY);
            g2.drawString(datos.get(i)[0] + " (" + datos.get(i)[1] + ")", legX + 22, legY + i * 24 + 13);
        }

        g2.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        g2.setColor(new java.awt.Color(30, 100, 160));
        g2.drawString("Distribución de Citas por Estado", pieX, pieY + size + 25);
    }

    private void dibujarBarChart(java.awt.Graphics2D g2, java.util.List<String[]> datos, int w, int h) {
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        if (datos.isEmpty()) {
            g2.setColor(java.awt.Color.GRAY);
            g2.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
            g2.drawString("Sin datos disponibles", w / 2 - 70, h / 2);
            return;
        }
        int maxVal = 0;
        for (String[] d : datos) maxVal = Math.max(maxVal, Integer.parseInt(d[1]));
        if (maxVal == 0) return;

        int mL = 60, mB = 80, mT = 40, mR = 20;
        int cW = w - mL - mR, cH = h - mT - mB;
        if (cW < 50 || cH < 50) return;

        g2.setColor(java.awt.Color.DARK_GRAY);
        g2.setStroke(new java.awt.BasicStroke(1.5f));
        g2.drawLine(mL, mT, mL, mT + cH);
        g2.drawLine(mL, mT + cH, mL + cW, mT + cH);

        int n = datos.size();
        int barW = Math.max(10, (cW / n) - 8);
        int gap = Math.max(4, (cW - barW * n) / (n + 1));

        g2.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 10));
        for (int i = 0; i < n; i++) {
            int val = Integer.parseInt(datos.get(i)[1]);
            int bH = (int) ((double) val / maxVal * cH);
            int x = mL + gap + i * (barW + gap);
            int y = mT + cH - bH;

            g2.setColor(new java.awt.Color(30, 100, 160));
            g2.fillRect(x, y, barW, bH);
            g2.setColor(new java.awt.Color(20, 70, 120));
            g2.drawRect(x, y, barW, bH);

            g2.setColor(java.awt.Color.DARK_GRAY);
            g2.drawString(String.valueOf(val), x + barW / 2 - 4, y - 4);

            java.awt.Graphics2D g2r = (java.awt.Graphics2D) g2.create();
            g2r.translate(x + barW / 2, mT + cH + 8);
            g2r.rotate(Math.PI / 4);
            String name = datos.get(i)[0];
            if (name.length() > 15) name = name.substring(0, 13) + "..";
            g2r.drawString(name, 0, 0);
            g2r.dispose();
        }

        g2.setColor(java.awt.Color.DARK_GRAY);
        for (int i = 0; i <= 4; i++) {
            int y = mT + cH - (cH * i / 4);
            g2.drawLine(mL - 4, y, mL, y);
            g2.drawString(String.valueOf(maxVal * i / 4), mL - 30, y + 4);
        }

        g2.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        g2.setColor(new java.awt.Color(30, 100, 160));
        g2.drawString("Servicios más Utilizados", mL, mT - 15);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        btnActualizar = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnlGraficoCitas = new javax.swing.JPanel();
        pnlGraficoServicios = new javax.swing.JPanel();

        setClosable(true); setMaximizable(true); setResizable(true);
        setTitle("Gráficos Estadísticos");

        pnlHeader.setBackground(new java.awt.Color(30, 100, 160));
        lblTitulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        lblTitulo.setForeground(java.awt.Color.WHITE);
        lblTitulo.setText("  Gráficos Estadísticos");
        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        pnlHeaderLayout.setVerticalGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup().addGap(12).addComponent(lblTitulo).addGap(12)));

        btnActualizar.setBackground(new java.awt.Color(30, 100, 160)); btnActualizar.setForeground(java.awt.Color.WHITE);
        btnActualizar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { btnActualizarActionPerformed(evt); }
        });

        pnlGraficoCitas.setBackground(java.awt.Color.WHITE);
        pnlGraficoServicios.setBackground(java.awt.Color.WHITE);

        javax.swing.GroupLayout pnlGraficoCitasLayout = new javax.swing.GroupLayout(pnlGraficoCitas);
        pnlGraficoCitas.setLayout(pnlGraficoCitasLayout);
        pnlGraficoCitasLayout.setHorizontalGroup(pnlGraficoCitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400, Short.MAX_VALUE));
        pnlGraficoCitasLayout.setVerticalGroup(pnlGraficoCitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 300, Short.MAX_VALUE));
        jTabbedPane1.addTab("Citas por Estado", pnlGraficoCitas);

        javax.swing.GroupLayout pnlGraficoServiciosLayout = new javax.swing.GroupLayout(pnlGraficoServicios);
        pnlGraficoServicios.setLayout(pnlGraficoServiciosLayout);
        pnlGraficoServiciosLayout.setHorizontalGroup(pnlGraficoServiciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400, Short.MAX_VALUE));
        pnlGraficoServiciosLayout.setVerticalGroup(pnlGraficoServiciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 300, Short.MAX_VALUE));
        jTabbedPane1.addTab("Servicios más Usados", pnlGraficoServicios);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnActualizar)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10)
                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        cargarDatosGrafico();
    }//GEN-LAST:event_btnActualizarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlGraficoCitas;
    private javax.swing.JPanel pnlGraficoServicios;
    private javax.swing.JPanel pnlHeader;
    // End of variables declaration//GEN-END:variables
}
