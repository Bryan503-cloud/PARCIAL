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
}
