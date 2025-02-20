/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import Conexion.ConexionBD;
import Conexion.IConexionBD;
import DAO.DireccionesPacientesDAO;
import DAO.PacienteDAO;
import DAO.UsuarioDAO;
import DTO.DireccionNuevoDTO;
import DTO.PacienteNuevoDTO;
import DTO.UsuarioNuevoDTO;
import Entidades.DireccionPaciente;
import Entidades.Paciente;
import Entidades.Usuario;
import Exception.NegocioException;
import Exception.PersistenciaClinicaException;
import Mappers.DireccionPacienteMapper;
import Mappers.PacienteMapper;
import Mappers.UsuarioMapper;
import java.sql.SQLException;

/**
 *
 * @author sonic
 */
public class UsuarioBO {
    private final UsuarioDAO usuarioDAO;
    private final PacienteDAO pacienteDAO;
    private final DireccionesPacientesDAO direccionDAO;

    public UsuarioBO(IConexionBD conexionBD) {
        this.usuarioDAO = new UsuarioDAO(conexionBD);
        this.pacienteDAO = new PacienteDAO(conexionBD); // Inicializar
        this.direccionDAO = new DireccionesPacientesDAO(conexionBD); // Inicializar
    }
    public void registrarUsuario(UsuarioNuevoDTO usuarioDTO, PacienteNuevoDTO pacientenuevoDTO, DireccionNuevoDTO direccionnuevoDTO) throws NegocioException, PersistenciaClinicaException, SQLException {
        if (usuarioDAO.consultarUsuarioPorCorreo(usuarioDTO.getCorreoElectronico()) != null) {
            throw new PersistenciaClinicaException("El correo ya está registrado.");
        }
        if (usuarioDTO.getCorreoElectronico().isEmpty()) {
            throw new PersistenciaClinicaException("El correo electronico no puede estar vacio");
        }
        if (usuarioDTO.getContrasenia().isEmpty()) {
            throw new PersistenciaClinicaException("El usuario necesita una contraseña para registrarse");
        }
        if (usuarioDTO.getRol() == null || (!usuarioDTO.getRol().equalsIgnoreCase("médico") && !usuarioDTO.getRol().equalsIgnoreCase("paciente"))) {
            throw new PersistenciaClinicaException("Solo se puede elegir el rol de medico o usuario y es obligatorio");
        }
        if (pacientenuevoDTO.getApellidoPaterno() == null || pacientenuevoDTO.getApellidoPaterno().isEmpty()){
            throw new PersistenciaClinicaException("Se necesita un apellido paterno obligatoriamente");
        }
        if (pacientenuevoDTO.getFechaNacimiento() == null) {
            throw new PersistenciaClinicaException("Se necesita ingresar una fecha de nacimiento");
        }
        if (pacientenuevoDTO.getTelefono().isEmpty()){
            throw new PersistenciaClinicaException("No se puede registrar sin un telefono");
        }
        
        try {
            // 1. Registrar el usuario y obtener su idUsuario
            Usuario usuario = UsuarioMapper.toEntity(usuarioDTO);
            boolean usuarioRegistrado = usuarioDAO.registrarUsuario(usuario);
            if (!usuarioRegistrado) {
                throw new PersistenciaClinicaException("No se pudo registrar el usuario.");
            }
            
            // 2. Registrar el paciente asignando el idUsuario obtenido
            Paciente paciente = PacienteMapper.toEntity(pacientenuevoDTO);
            paciente.setIdUsuario(usuario.getIdUsuario());
            Paciente pacienteRegistrado = pacienteDAO.registrarPaciente(paciente);
            if (pacienteRegistrado == null) {
                throw new PersistenciaClinicaException("No se pudo registrar el paciente.");
            }
            
            // 3. Registrar la dirección asignando el idPaciente obtenido
            DireccionPaciente direccion = DireccionPacienteMapper.toEntity(direccionnuevoDTO);
            direccion.setIdPaciente(paciente.getIdPaciente());
            boolean direccionRegistrada = direccionDAO.agregarDireccion(direccion);
            if (!direccionRegistrada) {
                throw new PersistenciaClinicaException("No se pudo registrar la dirección del paciente.");
            }
            
        } catch (PersistenciaClinicaException e) {
            throw new NegocioException("Error al registrar el usuario: " + e.getMessage(), e);
        }
    }
}
