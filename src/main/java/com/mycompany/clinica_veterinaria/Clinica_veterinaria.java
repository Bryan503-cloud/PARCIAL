package com.mycompany.clinica_veterinaria;

public class Clinica_veterinaria {

    public static void main(String[] args) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        try {
            // L&F nativo del sistema operativo (Windows en Windows, Mac en Mac...)
            javax.swing.UIManager.setLookAndFeel(
                javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) { /* queda el Metal por defecto */ }

        repararAdmin();

        java.awt.EventQueue.invokeLater(() -> new Login().setVisible(true));
    }

    /**
     * Verifica si el usuario 'admin' puede entrar con 'Admin123'.
     * Si el hash no coincide (contraseña desconocida o corrupta),
     * lo corrige y avisa al usuario.
     * Si el usuario 'admin' no existe, lo crea.
     */
    private static void repararAdmin() {
        try {
            java.sql.Connection con = Conexion.getConnection();
            if (con == null) return;

            String hashAdmin123 = Utilidades.hashSHA256("Admin123");

            // ¿Existe admin Y su hash coincide con Admin123?
            java.sql.PreparedStatement chk = con.prepareStatement(
                "SELECT COUNT(*) FROM USUARIO " +
                "WHERE username = 'admin' AND password_hash = ? AND estado = 1");
            chk.setString(1, hashAdmin123);
            java.sql.ResultSet rs = chk.executeQuery();
            boolean okAdmin = rs.next() && rs.getInt(1) > 0;

            if (!okAdmin) {
                // Insertar o actualizar el usuario admin con hash correcto
                java.sql.PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO USUARIO (username, password_hash, email, rol, estado) " +
                    "VALUES ('admin', ?, 'admin@clinica.com', 'Administrador', 1) " +
                    "ON DUPLICATE KEY UPDATE " +
                    "  password_hash = VALUES(password_hash), " +
                    "  rol = 'Administrador', " +
                    "  estado = 1");
                ps.setString(1, hashAdmin123);
                ps.executeUpdate();

                javax.swing.JOptionPane.showMessageDialog(null,
                    "Credenciales de administrador restablecidas:\n\n" +
                    "   Usuario  :  admin\n" +
                    "   Contrasena:  Admin123\n\n" +
                    "Cambiala en Administracion > Gestion de Usuarios.",
                    "Admin listo", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }

            con.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null,
                "No se pudo conectar a la base de datos.\n" + e.getMessage(),
                "Error de conexion", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
}
