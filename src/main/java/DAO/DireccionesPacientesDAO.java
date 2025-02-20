/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.IConexionBD;
import Entidades.DireccionPaciente;
import Exception.PersistenciaClinicaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

/**
 *
 * @author sonic
 */
public class DireccionesPacientesDAO {
    public class DireccionPacienteDAO implements IDireccionesPacientesDAO {

    IConexionBD conexion;

    public DireccionPacienteDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    private static final Logger logger = Logger.getLogger(DireccionPacienteDAO.class.getName());

    public boolean agregarDireccion(DireccionPaciente direccion) throws PersistenciaClinicaException, SQLException {
        String sentenciaSQL = "INSERT INTO DireccionesPacientes (idPaciente, Calle, Numero, CP) VALUES (?,?,?,?)";
        
        try (Connection con = conexion.crearConexion();
                PreparedStatement ps = con.prepareStatement(sentenciaSQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, direccion.getIdPaciente());
            ps.setString(2, direccion.getCalle());
            ps.setString(3, direccion.getNumero());
            ps.setString(4, direccion.getCp());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas == 0) {
                throw new PersistenciaClinicaException("La creación de la dirección falló, no se insertó ninguna fila.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    direccion.setIdDireccion(generatedKeys.getInt(1));
                    logger.info("Dirección creada con éxito: " + direccion.getIdDireccion());
                    return true;
                } else {
                    throw new PersistenciaClinicaException("La creación de la dirección falló");
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaClinicaException("Error al crear la dirección", e);
        }
    }

  
    public DireccionPaciente consultarDireccionPorPaciente(int idPaciente) throws PersistenciaClinicaException {
        String consultaSQL = "SELECT idDireccion, idPaciente, Calle, Numero, CP FROM DireccionesPacientes WHERE idPaciente = ?";
        
        DireccionPaciente direccion = null;
        try (Connection con = conexion.crearConexion();
                PreparedStatement ps = con.prepareStatement(consultaSQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idPaciente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    direccion = new DireccionPaciente(
                            rs.getInt("idDireccion"),
                            rs.getInt("idPaciente"),
                            rs.getString("Calle"),
                            rs.getString("Numero"),
                            rs.getString("CP")
                    );
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaClinicaException("Error al obtener la dirección del paciente: ID inexistente", e);
        }
        return direccion;
    }

  
    public boolean actualizarDireccion(DireccionPaciente direccion) throws PersistenciaClinicaException {
        String sentenciaSQL = "UPDATE DireccionesPacientes SET Calle = ?, Numero = ?, CP = ? WHERE idPaciente = ?";
        
        try (Connection con = conexion.crearConexion();
                PreparedStatement ps = con.prepareStatement(sentenciaSQL)) {

            ps.setString(1, direccion.getCalle());
            ps.setString(2, direccion.getNumero());
            ps.setString(3, direccion.getCp());
            ps.setInt(4, direccion.getIdPaciente());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas == 0) {
                throw new PersistenciaClinicaException("La actualización de la dirección falló, no se actualizó ninguna fila.");
            }
            return true;
        } catch (SQLException e) {
            throw new PersistenciaClinicaException("Error al actualizar la dirección", e);
        }
    }
    }

}
