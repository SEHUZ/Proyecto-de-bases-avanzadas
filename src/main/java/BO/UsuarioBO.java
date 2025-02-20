/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import Conexion.IConexionBD;
import DAO.UsuarioDAO;
import DTO.DireccionNuevoDTO;
import DTO.PacienteNuevoDTO;
import DTO.UsuarioNuevoDTO;
import Exception.NegocioException;
import Exception.PersistenciaClinicaException;
import Mappers.UsuarioMapper;

/**
 *
 * @author sonic
 */
public class UsuarioBO {
    private final UsuarioDAO usuarioDAO;

    public UsuarioBO(IConexionBD conexionBD) {
        this.usuarioDAO = new UsuarioDAO(conexionBD);
    }
    public void registrarUsuario(UsuarioNuevoDTO usuarioDTO, PacienteNuevoDTO pacientenuevoDTO, DireccionNuevoDTO direccionnuevoDTO) throws NegocioException, PersistenciaClinicaException {
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
        
        try {
            usuarioDAO.registrarUsuario(UsuarioMapper.toEntity(usuarioDTO));
        

        }catch (PersistenciaClinicaException e) {
            throw new NegocioException("Error al registrar el usuario: " + e.getMessage(), e);
        }

    }
}
