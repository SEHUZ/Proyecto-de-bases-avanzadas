/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.IConexionBD;
import Entidades.Paciente;
import Exception.PersistenciaClinicaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jose
 */
public class PacienteDAO implements IPacienteDAO {

    IConexionBD conexion;

    public PacienteDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    private static final Logger logger = Logger.getLogger(PacienteDAO.class.getName());

    @Override
    public Paciente registrarPaciente(Paciente paciente) throws PersistenciaClinicaException {

        String sentenciaSQL = "INSERT INTO pacientes (idUsuario, nombres, apellidoPaterno, apellidoMaterno, fechaNacimiento, telefono) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = conexion.crearConexion();
                 PreparedStatement ps = con.prepareStatement(sentenciaSQL, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, paciente.getIdUsuario());
            ps.setString(2, paciente.getNombres());
            ps.setString(3, paciente.getApellidoPaterno());
            ps.setString(4, paciente.getApellidoMaterno());
            ps.setObject(5, paciente.getFechaNacimiento());
            ps.setString(6, paciente.getTelefono());
            
            int filasAfectadas = ps.executeUpdate();
            if(filasAfectadas == 0) {
                logger.severe("Falló el registro del paciente, ninguna fila ha sido afectada.");
                throw new PersistenciaClinicaException("Falló el registro del paciente, ninguna fila ha sido afectada.");
            }
            
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    paciente.setIdPaciente(generatedKeys.getInt(1));
                    logger.info("Paciente registrado exitosamente. ID: " + paciente.getIdPaciente());
                } else {
                    logger.severe("Falló el registro del paciente, no se obtuvo ID.");
                    throw new PersistenciaClinicaException("Falló el registro del paciente, no se obtuvo ID.");
                }
            }
            
            return paciente;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error en el registro del paciente", ex);
            throw new PersistenciaClinicaException("Error al registrar al paciente", ex);
        }
    }

}
