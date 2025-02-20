/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.ConexionBD;
import Conexion.IConexionBD;
import Entidades.Usuario;
import Exception.PersistenciaClinicaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sonic
 */
public class UsuarioDAO implements IUsuarioDAO{
    
    IConexionBD conexion;

    public UsuarioDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }
    private static final Logger logger = Logger.getLogger(UsuarioDAO.class.getName());

    @Override
    public boolean registrarUsuario(Usuario usuario) throws PersistenciaClinicaException {
        String sentenciaSQL = "INSERT INTO USUARIOS (correoElectronico, contrasenia, rol) VALUES (?,?,?)";
        
        try (Connection con = conexion.crearConexion();
                PreparedStatement ps = con.prepareStatement(sentenciaSQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, usuario.getCorreoElectronico()); 
            ps.setString(2, usuario.getContrasenia());
            ps.setString(3, usuario.getRol());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas == 0) {
                throw new PersistenciaClinicaException("La creación del activista falló, no se insertó ninguna fila.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) { 
                    usuario.setIdUsuario(generatedKeys.getInt(1));
                    logger.info("Usuario creado con exito: " + usuario.getIdUsuario());
                    return true;
                } else {
                    throw new PersistenciaClinicaException("La creación del Usuario fallo");
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaClinicaException("Error al crear al usuario", e);
        }

    }
    
    
    @Override
    public Usuario consultarUsuarioPorID(int idUsuario) throws PersistenciaClinicaException {
        String consultaSQL = "SELECT idUsuario, correoElectronico, contrasenia, rol FROM usuarios WHERE idUsuario = ?";
        
        Usuario usuario = null;
        try (Connection con = conexion.crearConexion();
                PreparedStatement ps = con.prepareStatement(consultaSQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario(
                            rs.getInt("idUsuario"),
                            rs.getString("correoElectronico"),
                            rs.getString("contrasenia"),
                            rs.getString("rol")
                    );
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaClinicaException("Error al obtener al usuario por ID: Id inexistente", e);
        }
        return usuario;
    }
    
    @Override 
    public Usuario consultarUsuarioPorCorreo(String correoElectronico) throws PersistenciaClinicaException {
        String consultaSQL = "SELECT idUsuario, correoElectronico, contrasenia, rol FROM usuarios WHERE correoElectronico = ?";
        
        Usuario usuario = null;
        try (Connection con = conexion.crearConexion();
                PreparedStatement ps = con.prepareStatement (consultaSQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, correoElectronico);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario(
                            rs.getInt("idUsuario"),
                            rs.getString("correoElectronico"),
                            rs.getString("contrasenia"),
                            rs.getString("rol")
                    );
                }
                
            }
        }catch (SQLException e){
            throw new PersistenciaClinicaException ("Error al obtener el usuario: Correo inexistente", e);
        }
        return usuario;
    }
}
