package com.mycompany.clinica_veterinaria;

import java.security.MessageDigest;
import javax.swing.JOptionPane;

public class Utilidades {

    public static String hashSHA256(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(texto.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error al hashear contraseña", e);
        }
    }

    public static boolean validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) return false;
        return email.matches("^[\\w.+\\-]+@[\\w\\-]+\\.[a-zA-Z]{2,}$");
    }

    public static boolean validarDUI(String dui) {
        if (dui == null) return false;
        return dui.matches("^\\d{8}-\\d$");
    }

    public static boolean validarTelefono(String tel) {
        if (tel == null) return false;
        return tel.matches("^[0-9+\\-\\s]{7,15}$");
    }

    public static boolean validarFecha(String fecha) {
        if (fecha == null) return false;
        return fecha.matches("^\\d{4}-\\d{2}-\\d{2}$");
    }

    public static boolean campoVacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    /** true si la longitud del valor (trim) está entre min y max (inclusive). */
    public static boolean validarLongitud(String valor, int min, int max) {
        if (valor == null) return false;
        int len = valor.trim().length();
        return len >= min && len <= max;
    }

    /** true si el String representa un decimal >= 0. */
    public static boolean validarDecimalPositivo(String s) {
        if (s == null || s.trim().isEmpty()) return false;
        try { return Double.parseDouble(s.trim()) >= 0; } catch (Exception e) { return false; }
    }

    public static void mostrarError(java.awt.Component parent, String mensaje) {
        JOptionPane.showMessageDialog(parent, mensaje, "Error de validación", JOptionPane.ERROR_MESSAGE);
    }

    public static void mostrarExito(java.awt.Component parent, String mensaje) {
        JOptionPane.showMessageDialog(parent, mensaje, "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirmar(java.awt.Component parent, String mensaje) {
        return JOptionPane.showConfirmDialog(parent, mensaje, "Confirmar acción",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    /**
     * Aplica estilo visual moderno a una JTable:
     * filas alternas azul/blanco, cabecera azul, selección celeste.
     */
    public static void estilizarTabla(javax.swing.JTable tabla) {
        tabla.setRowHeight(28);
        tabla.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        tabla.setSelectionBackground(new java.awt.Color(173, 216, 230));
        tabla.setSelectionForeground(new java.awt.Color(20, 20, 20));
        tabla.setGridColor(new java.awt.Color(210, 225, 240));
        tabla.setShowGrid(true);
        tabla.setIntercellSpacing(new java.awt.Dimension(0, 1));

        javax.swing.table.JTableHeader header = tabla.getTableHeader();
        header.setBackground(new java.awt.Color(25, 90, 150));
        header.setForeground(java.awt.Color.WHITE);
        header.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        header.setReorderingAllowed(false);
        try {
            ((javax.swing.table.DefaultTableCellRenderer) header.getDefaultRenderer())
                .setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        } catch (Exception ignored) {}

        tabla.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    javax.swing.JTable t, Object value, boolean isSelected,
                    boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
                setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 8, 2, 8));
                if (!isSelected) {
                    setBackground(row % 2 == 0
                        ? java.awt.Color.WHITE
                        : new java.awt.Color(235, 244, 255));
                    setForeground(new java.awt.Color(35, 35, 35));
                }
                return this;
            }
        });
    }

    /**
     * Aplica estilo visual moderno a un JButton:
     * sin borde de foco, cursor de mano, opaco.
     */
    public static void estilizarBoton(javax.swing.JButton btn) {
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn.setOpaque(true);
    }
}
