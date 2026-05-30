/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.clinica_veterinaria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author bra03
 */
public class Conexion {
       private static final String URL  = "jdbc:mysql://localhost:3306/clinica_veterinaria?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASS = "0307";          // ← pon tu contraseña aquí si tienes

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al conectar con la base de datos:\n" + e.getMessage()
                    + "\n\nVerifica que MySQL esté corriendo y la BD exista.",
                    "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
}
