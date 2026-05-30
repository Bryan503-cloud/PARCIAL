package com.mycompany.clinica_veterinaria;

import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;

public class Reporte extends javax.swing.JInternalFrame {

    // ── Campos extra (fuera del GEN) ─────────────────────────────────────────
    private javax.swing.JComboBox<String> cmbFiltroVet;
    private javax.swing.JComboBox<String> cmbFiltroEstado;
    private javax.swing.JTable tblTopVeterinarios;
    private javax.swing.JTable tblTopServicios;
    private javax.swing.JLabel lblCardCitas;
    private javax.swing.JLabel lblCardIngresos;
    private javax.swing.JLabel lblCardClientes;

    // ═════════════════════════════════════════════════════════════════════════
    //  Constructor y arranque
    // ═════════════════════════════════════════════════════════════════════════

    public Reporte() {
        initComponents();
        inicializarExtras();
        construirLayout();
        aplicarEstilo();
        cargarFiltroVeterinario();
        // Fechas por defecto: primer día del mes → hoy
        java.util.Calendar cal = java.util.Calendar.getInstance();
        java.util.Date hoy = cal.getTime();
        cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
        spnFechaDesde.setValue(cal.getTime());
        spnFechaHasta.setValue(hoy);
        generarReportes();
    }

    // ═════════════════════════════════════════════════════════════════════════
    //  Inicialización de componentes extra y layout
    // ═════════════════════════════════════════════════════════════════════════

    private void inicializarExtras() {
        // Combo de veterinario
        cmbFiltroVet = new javax.swing.JComboBox<>();
        cmbFiltroVet.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        cmbFiltroVet.setPreferredSize(new java.awt.Dimension(185, 28));

        // Combo de estado de cita
        cmbFiltroEstado = new javax.swing.JComboBox<>(new String[]{
            "Todos los estados", "Programada", "Confirmada", "Atendida", "Cancelada"});
        cmbFiltroEstado.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        cmbFiltroEstado.setPreferredSize(new java.awt.Dimension(155, 28));

        // Tablas de los tabs nuevos
        tblTopVeterinarios = new javax.swing.JTable();
        tblTopServicios    = new javax.swing.JTable();

        // Labels de las tarjetas de resumen
        lblCardCitas     = crearLabelValor("—");
        lblCardIngresos  = crearLabelValor("—");
        lblCardClientes  = crearLabelValor("—");
    }

    private javax.swing.JLabel crearLabelValor(String texto) {
        javax.swing.JLabel lbl = new javax.swing.JLabel(texto, javax.swing.SwingConstants.CENTER);
        lbl.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 28));
        lbl.setForeground(new java.awt.Color(25, 90, 150));
        return lbl;
    }

    private void construirLayout() {
        // ── Panel de filtros ─────────────────────────────────────────────────
        javax.swing.JPanel pnlFiltros = new javax.swing.JPanel(
            new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 14, 8));
        pnlFiltros.setBackground(new java.awt.Color(236, 243, 252));
        pnlFiltros.setBorder(javax.swing.BorderFactory.createMatteBorder(
            0, 0, 1, 0, new java.awt.Color(180, 210, 238)));

        spnFechaDesde.setPreferredSize(new java.awt.Dimension(130, 28));
        spnFechaHasta.setPreferredSize(new java.awt.Dimension(130, 28));
        btnGenerar.setPreferredSize(new java.awt.Dimension(100, 28));

        pnlFiltros.add(mkLbl("Desde:"));      pnlFiltros.add(spnFechaDesde);
        pnlFiltros.add(mkLbl("Hasta:"));      pnlFiltros.add(spnFechaHasta);
        pnlFiltros.add(mkLbl("Veterinario:")); pnlFiltros.add(cmbFiltroVet);
        pnlFiltros.add(mkLbl("Estado:"));     pnlFiltros.add(cmbFiltroEstado);
        pnlFiltros.add(btnGenerar);

        // ── Tarjetas de resumen ──────────────────────────────────────────────
        javax.swing.JPanel pnlCards = new javax.swing.JPanel(
            new java.awt.GridLayout(1, 3, 12, 0));
        pnlCards.setBackground(new java.awt.Color(245, 248, 252));
        pnlCards.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 16, 10, 16));
        pnlCards.add(crearTarjeta("Total Citas",     lblCardCitas,    new java.awt.Color(25,  110, 200)));
        pnlCards.add(crearTarjeta("Ingresos ($)",    lblCardIngresos, new java.awt.Color(30,  145,  70)));
        pnlCards.add(crearTarjeta("Clientes Únicos", lblCardClientes, new java.awt.Color(140,  75, 185)));

        // ── Agregar pestañas nuevas al JTabbedPane ───────────────────────────
        jTabbedPane1.addTab("Top Veterinarios",
            new javax.swing.JScrollPane(tblTopVeterinarios));
        jTabbedPane1.addTab("Servicios Solicitados",
            new javax.swing.JScrollPane(tblTopServicios));

        // ── Reconstruir el content pane ──────────────────────────────────────
        javax.swing.JPanel pnlSuperior = new javax.swing.JPanel(new java.awt.BorderLayout());
        pnlSuperior.add(pnlFiltros, java.awt.BorderLayout.NORTH);
        pnlSuperior.add(pnlCards,   java.awt.BorderLayout.CENTER);

        javax.swing.JPanel pnlContenido = new javax.swing.JPanel(new java.awt.BorderLayout());
        pnlContenido.add(pnlSuperior,  java.awt.BorderLayout.NORTH);
        pnlContenido.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        getContentPane().removeAll();
        getContentPane().setLayout(new java.awt.BorderLayout());
        getContentPane().add(pnlHeader,    java.awt.BorderLayout.NORTH);
        getContentPane().add(pnlContenido, java.awt.BorderLayout.CENTER);
    }

    /** Label de encabezado para la fila de filtros. */
    private javax.swing.JLabel mkLbl(String texto) {
        javax.swing.JLabel l = new javax.swing.JLabel(texto);
        l.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        l.setForeground(new java.awt.Color(50, 80, 120));
        return l;
    }

    /** Tarjeta visual con borde de color superior, título y valor grande. */
    private javax.swing.JPanel crearTarjeta(String titulo,
                                            javax.swing.JLabel lblValor,
                                            java.awt.Color colorBorde) {
        javax.swing.JPanel card = new javax.swing.JPanel(new java.awt.BorderLayout(4, 4));
        card.setBackground(java.awt.Color.WHITE);
        card.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createMatteBorder(4, 0, 0, 0, colorBorde),
            javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(220, 230, 245)),
                javax.swing.BorderFactory.createEmptyBorder(8, 12, 12, 12))));

        javax.swing.JLabel lblTit = new javax.swing.JLabel(titulo, javax.swing.SwingConstants.CENTER);
        lblTit.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        lblTit.setForeground(new java.awt.Color(110, 130, 160));

        card.add(lblTit,   java.awt.BorderLayout.NORTH);
        card.add(lblValor, java.awt.BorderLayout.CENTER);
        return card;
    }

    private void aplicarEstilo() {
        EstiloUI.aplicarVentana(getContentPane(), pnlHeader,
            new javax.swing.JTable[]{tblResumenCitas, tblIngresos,
                                     tblMascotasEspecie, tblMejoresClientes,
                                     tblTopVeterinarios, tblTopServicios},
            new javax.swing.JButton[]{btnGenerar});
    }

    // ═════════════════════════════════════════════════════════════════════════
    //  Carga de datos en filtros
    // ═════════════════════════════════════════════════════════════════════════

    private void cargarFiltroVeterinario() {
        cmbFiltroVet.removeAllItems();
        cmbFiltroVet.addItem("Todos los veterinarios");
        try {
            Connection con = Conexion.getConnection();
            if (con == null) return;
            PreparedStatement ps = con.prepareStatement(
                "SELECT id_veterinario, nombre, apellido FROM VETERINARIO ORDER BY apellido, nombre");
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                cmbFiltroVet.addItem(rs.getInt("id_veterinario")
                    + " - Dr. " + rs.getString("nombre") + " " + rs.getString("apellido"));
            con.close();
        } catch (Exception e) {
            Utilidades.mostrarError(this, "Error cargando veterinarios: " + e.getMessage());
        }
    }

    // ── Helpers para leer los valores de los filtros ─────────────────────────

    private String spnToStr(javax.swing.JSpinner spn) {
        return new SimpleDateFormat("yyyy-MM-dd").format((java.util.Date) spn.getValue());
    }

    private int getFiltroVetId() {
        String sel = (String) cmbFiltroVet.getSelectedItem();
        if (sel == null || sel.startsWith("Todos")) return -1;
        try { return Integer.parseInt(sel.split(" - ")[0]); } catch (Exception e) { return -1; }
    }

    private String getFiltroEstado() {
        String sel = (String) cmbFiltroEstado.getSelectedItem();
        return (sel == null || sel.startsWith("Todos")) ? null : sel;
    }

    // ═════════════════════════════════════════════════════════════════════════
    //  Generación de reportes
    // ═════════════════════════════════════════════════════════════════════════

    private void generarReportes() {
        String desde  = spnToStr(spnFechaDesde);
        String hasta  = spnToStr(spnFechaHasta);
        if (desde.compareTo(hasta) > 0) {
            Utilidades.mostrarError(this, "La fecha 'Desde' no puede ser posterior a la fecha 'Hasta'."); return;
        }
        int    idVet  = getFiltroVetId();
        String estado = getFiltroEstado();

        actualizarTarjetas(desde, hasta, idVet, estado);
        cargarResumenCitas(desde, hasta, idVet, estado);
        cargarIngresos(desde, hasta, idVet);
        cargarMascotasEspecie();
        cargarMejoresClientes(desde, hasta, idVet);
        cargarTopVeterinarios(desde, hasta, estado);
        cargarTopServicios(desde, hasta, idVet);
    }

    // ── Tarjetas de resumen ──────────────────────────────────────────────────

    private void actualizarTarjetas(String desde, String hasta, int idVet, String estado) {
        try {
            Connection con = Conexion.getConnection();
            if (con == null) return;

            // Total citas
            StringBuilder sqlC = new StringBuilder(
                "SELECT COUNT(*) FROM CITA WHERE DATE(fecha_hora) BETWEEN ? AND ?");
            if (idVet  != -1)  sqlC.append(" AND id_veterinario = ?");
            if (estado != null) sqlC.append(" AND estado_cita = ?");
            PreparedStatement psC = con.prepareStatement(sqlC.toString());
            int p = 1;
            psC.setString(p++, desde); psC.setString(p++, hasta);
            if (idVet  != -1)  psC.setInt(p++, idVet);
            if (estado != null) psC.setString(p++, estado);
            ResultSet rsC = psC.executeQuery();
            if (rsC.next()) lblCardCitas.setText(String.valueOf(rsC.getInt(1)));

            // Total ingresos
            StringBuilder sqlI = new StringBuilder(
                "SELECT COALESCE(SUM(cs.cantidad * cs.costo_aplicado), 0) " +
                "FROM CITA c JOIN CITA_SERVICIO cs ON c.id_cita = cs.id_cita " +
                "WHERE DATE(c.fecha_hora) BETWEEN ? AND ?");
            if (idVet  != -1)  sqlI.append(" AND c.id_veterinario = ?");
            if (estado != null) sqlI.append(" AND c.estado_cita = ?");
            PreparedStatement psI = con.prepareStatement(sqlI.toString());
            p = 1;
            psI.setString(p++, desde); psI.setString(p++, hasta);
            if (idVet  != -1)  psI.setInt(p++, idVet);
            if (estado != null) psI.setString(p++, estado);
            ResultSet rsI = psI.executeQuery();
            if (rsI.next()) lblCardIngresos.setText("$" + String.format("%.2f", rsI.getDouble(1)));

            // Clientes únicos
            StringBuilder sqlCl = new StringBuilder(
                "SELECT COUNT(DISTINCT m.id_cliente) " +
                "FROM CITA c JOIN MASCOTA m ON c.id_mascota = m.id_mascota " +
                "WHERE DATE(c.fecha_hora) BETWEEN ? AND ?");
            if (idVet  != -1)  sqlCl.append(" AND c.id_veterinario = ?");
            if (estado != null) sqlCl.append(" AND c.estado_cita = ?");
            PreparedStatement psCl = con.prepareStatement(sqlCl.toString());
            p = 1;
            psCl.setString(p++, desde); psCl.setString(p++, hasta);
            if (idVet  != -1)  psCl.setInt(p++, idVet);
            if (estado != null) psCl.setString(p++, estado);
            ResultSet rsCl = psCl.executeQuery();
            if (rsCl.next()) lblCardClientes.setText(String.valueOf(rsCl.getInt(1)));

            con.close();
        } catch (Exception e) {
            Utilidades.mostrarError(this, "Error actualizando resumen: " + e.getMessage());
        }
    }

    // ── Pestaña 1: Resumen de Citas ──────────────────────────────────────────

    private void cargarResumenCitas(String desde, String hasta, int idVet, String estado) {
        DefaultTableModel m = new DefaultTableModel(
            new String[]{"Estado", "Total Citas", "% del Período"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblResumenCitas.setModel(m);
        try {
            Connection con = Conexion.getConnection(); if (con == null) return;
            StringBuilder sql = new StringBuilder(
                "SELECT estado_cita, COUNT(*) AS total FROM CITA " +
                "WHERE DATE(fecha_hora) BETWEEN ? AND ?");
            if (idVet  != -1)  sql.append(" AND id_veterinario = ?");
            if (estado != null) sql.append(" AND estado_cita = ?");
            sql.append(" GROUP BY estado_cita ORDER BY total DESC");

            PreparedStatement ps = con.prepareStatement(sql.toString());
            int p = 1;
            ps.setString(p++, desde); ps.setString(p++, hasta);
            if (idVet  != -1)  ps.setInt(p++, idVet);
            if (estado != null) ps.setString(p++, estado);
            ResultSet rs = ps.executeQuery();

            java.util.List<Object[]> filas = new java.util.ArrayList<>();
            int total = 0;
            while (rs.next()) {
                int cnt = rs.getInt("total");
                total += cnt;
                filas.add(new Object[]{rs.getString("estado_cita"), cnt, cnt});
            }
            for (Object[] fila : filas) {
                int cnt = (int) fila[1];
                String pct = total > 0 ? String.format("%.1f%%", (cnt * 100.0 / total)) : "0%";
                m.addRow(new Object[]{fila[0], cnt, pct});
            }
            con.close();
        } catch (Exception e) { Utilidades.mostrarError(this, "Error resumen citas: " + e.getMessage()); }
    }

    // ── Pestaña 2: Ingresos por servicio ─────────────────────────────────────

    private void cargarIngresos(String desde, String hasta, int idVet) {
        DefaultTableModel m = new DefaultTableModel(
            new String[]{"Servicio", "Cant. Vendida", "Total ($)", "Promedio ($)"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblIngresos.setModel(m);
        try {
            Connection con = Conexion.getConnection(); if (con == null) return;
            StringBuilder sql = new StringBuilder(
                "SELECT s.nombre_servicio, SUM(cs.cantidad) AS cantidad, " +
                "SUM(cs.cantidad * cs.costo_aplicado) AS total, " +
                "AVG(cs.costo_aplicado) AS promedio " +
                "FROM CITA_SERVICIO cs JOIN SERVICIO s ON cs.id_servicio = s.id_servicio " +
                "JOIN CITA c ON cs.id_cita = c.id_cita " +
                "WHERE DATE(c.fecha_hora) BETWEEN ? AND ?");
            if (idVet != -1) sql.append(" AND c.id_veterinario = ?");
            sql.append(" GROUP BY s.id_servicio ORDER BY total DESC");

            PreparedStatement ps = con.prepareStatement(sql.toString());
            int p = 1;
            ps.setString(p++, desde); ps.setString(p++, hasta);
            if (idVet != -1) ps.setInt(p++, idVet);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                m.addRow(new Object[]{
                    rs.getString("nombre_servicio"),
                    rs.getInt("cantidad"),
                    String.format("$%.2f", rs.getDouble("total")),
                    String.format("$%.2f", rs.getDouble("promedio"))});
            con.close();
        } catch (Exception e) { Utilidades.mostrarError(this, "Error ingresos: " + e.getMessage()); }
    }

    // ── Pestaña 3: Mascotas por especie ──────────────────────────────────────

    private void cargarMascotasEspecie() {
        DefaultTableModel m = new DefaultTableModel(
            new String[]{"Especie", "Total Mascotas", "% del Total"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblMascotasEspecie.setModel(m);
        try {
            Connection con = Conexion.getConnection(); if (con == null) return;
            PreparedStatement ps = con.prepareStatement(
                "SELECT especie, COUNT(*) AS total FROM MASCOTA GROUP BY especie ORDER BY total DESC");
            ResultSet rs = ps.executeQuery();
            java.util.List<Object[]> filas = new java.util.ArrayList<>();
            int totalMasc = 0;
            while (rs.next()) {
                int cnt = rs.getInt("total");
                totalMasc += cnt;
                filas.add(new Object[]{rs.getString("especie"), cnt});
            }
            for (Object[] f : filas) {
                int cnt = (int) f[1];
                String pct = totalMasc > 0 ? String.format("%.1f%%", (cnt * 100.0 / totalMasc)) : "0%";
                m.addRow(new Object[]{f[0], cnt, pct});
            }
            con.close();
        } catch (Exception e) { Utilidades.mostrarError(this, "Error especies: " + e.getMessage()); }
    }

    // ── Pestaña 4: Mejores clientes ───────────────────────────────────────────

    private void cargarMejoresClientes(String desde, String hasta, int idVet) {
        DefaultTableModel m = new DefaultTableModel(
            new String[]{"Cliente", "Total Citas", "Total Invertido ($)"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblMejoresClientes.setModel(m);
        try {
            Connection con = Conexion.getConnection(); if (con == null) return;
            StringBuilder sql = new StringBuilder(
                "SELECT CONCAT(c.nombre, ' ', c.apellido) AS cliente, " +
                "COUNT(ci.id_cita) AS citas, " +
                "COALESCE(SUM(cs.cantidad * cs.costo_aplicado), 0) AS total " +
                "FROM CLIENTE c JOIN MASCOTA m ON c.id_cliente = m.id_cliente " +
                "JOIN CITA ci ON m.id_mascota = ci.id_mascota " +
                "LEFT JOIN CITA_SERVICIO cs ON ci.id_cita = cs.id_cita " +
                "WHERE DATE(ci.fecha_hora) BETWEEN ? AND ?");
            if (idVet != -1) sql.append(" AND ci.id_veterinario = ?");
            sql.append(" GROUP BY c.id_cliente ORDER BY citas DESC, total DESC LIMIT 15");

            PreparedStatement ps = con.prepareStatement(sql.toString());
            int p = 1;
            ps.setString(p++, desde); ps.setString(p++, hasta);
            if (idVet != -1) ps.setInt(p++, idVet);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                m.addRow(new Object[]{
                    rs.getString("cliente"),
                    rs.getInt("citas"),
                    String.format("$%.2f", rs.getDouble("total"))});
            con.close();
        } catch (Exception e) { Utilidades.mostrarError(this, "Error mejores clientes: " + e.getMessage()); }
    }

    // ── Pestaña 5: Top veterinarios (NUEVA) ──────────────────────────────────

    private void cargarTopVeterinarios(String desde, String hasta, String estado) {
        DefaultTableModel m = new DefaultTableModel(
            new String[]{"Veterinario", "Total Citas", "Citas Atendidas", "Ingresos ($)"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblTopVeterinarios.setModel(m);
        try {
            Connection con = Conexion.getConnection(); if (con == null) return;
            StringBuilder sql = new StringBuilder(
                "SELECT CONCAT(v.nombre, ' ', v.apellido) AS vet, " +
                "COUNT(c.id_cita) AS total, " +
                "SUM(CASE WHEN c.estado_cita = 'Atendida' THEN 1 ELSE 0 END) AS atendidas, " +
                "COALESCE(SUM(cs.cantidad * cs.costo_aplicado), 0) AS ingresos " +
                "FROM VETERINARIO v " +
                "LEFT JOIN CITA c ON v.id_veterinario = c.id_veterinario " +
                "  AND DATE(c.fecha_hora) BETWEEN ? AND ?");
            if (estado != null) sql.append(" AND c.estado_cita = ?");
            sql.append(" LEFT JOIN CITA_SERVICIO cs ON c.id_cita = cs.id_cita " +
                       "GROUP BY v.id_veterinario ORDER BY total DESC, ingresos DESC");

            PreparedStatement ps = con.prepareStatement(sql.toString());
            int p = 1;
            ps.setString(p++, desde); ps.setString(p++, hasta);
            if (estado != null) ps.setString(p++, estado);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                m.addRow(new Object[]{
                    "Dr. " + rs.getString("vet"),
                    rs.getInt("total"),
                    rs.getInt("atendidas"),
                    String.format("$%.2f", rs.getDouble("ingresos"))});
            con.close();
        } catch (Exception e) { Utilidades.mostrarError(this, "Error top veterinarios: " + e.getMessage()); }
    }

    // ── Pestaña 6: Servicios más solicitados (NUEVA) ──────────────────────────

    private void cargarTopServicios(String desde, String hasta, int idVet) {
        DefaultTableModel m = new DefaultTableModel(
            new String[]{"Servicio", "Veces Solicitado", "Total Generado ($)", "Precio Promedio ($)"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblTopServicios.setModel(m);
        try {
            Connection con = Conexion.getConnection(); if (con == null) return;
            StringBuilder sql = new StringBuilder(
                "SELECT s.nombre_servicio, " +
                "SUM(cs.cantidad) AS veces, " +
                "SUM(cs.cantidad * cs.costo_aplicado) AS total, " +
                "AVG(cs.costo_aplicado) AS promedio " +
                "FROM SERVICIO s " +
                "JOIN CITA_SERVICIO cs ON s.id_servicio = cs.id_servicio " +
                "JOIN CITA c ON cs.id_cita = c.id_cita " +
                "WHERE DATE(c.fecha_hora) BETWEEN ? AND ?");
            if (idVet != -1) sql.append(" AND c.id_veterinario = ?");
            sql.append(" GROUP BY s.id_servicio ORDER BY veces DESC, total DESC");

            PreparedStatement ps = con.prepareStatement(sql.toString());
            int p = 1;
            ps.setString(p++, desde); ps.setString(p++, hasta);
            if (idVet != -1) ps.setInt(p++, idVet);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                m.addRow(new Object[]{
                    rs.getString("nombre_servicio"),
                    rs.getInt("veces"),
                    String.format("$%.2f", rs.getDouble("total")),
                    String.format("$%.2f", rs.getDouble("promedio"))});
            con.close();
        } catch (Exception e) { Utilidades.mostrarError(this, "Error servicios: " + e.getMessage()); }
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
