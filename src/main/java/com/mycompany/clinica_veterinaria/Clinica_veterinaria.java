package com.mycompany.clinica_veterinaria;

public class Clinica_veterinaria {

    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            // usa look and feel por defecto
        }
        java.awt.EventQueue.invokeLater(() -> new Login().setVisible(true));
    }
}
