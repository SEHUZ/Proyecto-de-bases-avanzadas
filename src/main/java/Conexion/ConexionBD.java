/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import Exception.PersistenciaClinicaException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sonic XDDD XDDD DANIEL PAPUMONTES
 */
public class ConexionBD implements IConexionBD {

    final String USUARIO = "root";
    final String PASS = "diamantepuro123";
    final String CADENA_CONEXION = "jdbc:mysql://localhost:3306/clinicaprivada";

    @Override
    public Connection crearConexion() throws PersistenciaClinicaException {

        try {
            Connection conexion = DriverManager.getConnection(CADENA_CONEXION, USUARIO, PASS);
            return conexion;
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaClinicaException("Error al conectar a la base de datos", ex);

        }

    }
}
