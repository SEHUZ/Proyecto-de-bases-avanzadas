/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import Conexion.IConexionBD;
import DAO.IPacienteDAO;
import DAO.PacienteDAO;
import DTO.PacienteNuevoDTO;
import Entidades.Paciente;
import Exception.NegocioException;
import Exception.PersistenciaClinicaException;
import Mappers.PacienteMapper;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sonic XDDDDDD
 */
public class PacienteBO {

    private static final Logger logger = Logger.getLogger(PacienteBO.class.getName());
    private final IPacienteDAO pacienteDAO;
    private final PacienteMapper mapper = new PacienteMapper();

    public PacienteBO(IConexionBD conexion) {
        this.pacienteDAO = new PacienteDAO(conexion);
    }

    public boolean registrarPaciente(PacienteNuevoDTO pacienteNuevo) throws NegocioException {
        if (pacienteNuevo == null) {
            throw new NegocioException("El paciente no puede ser nulo.");
        }

        if (pacienteNuevo.getNombres().isEmpty() || pacienteNuevo.getApellidoPaterno().isEmpty()
                || pacienteNuevo.getTelefono().isEmpty() || pacienteNuevo.getIdUsuario().isEmpty()) {
            throw new NegocioException("No puede haber campos vacios.");
        }

        if (pacienteNuevo.getTelefono().length() > 10 || pacienteNuevo.getTelefono().length() < 0) {
            throw new NegocioException("Debe ingresar un numero de telefono valido.");
        }

        if (pacienteNuevo.getFechaNacimiento() == null) {
            throw new NegocioException("La fecha de nacimiento no puede estar vacia.");
        }

        Paciente paciente = mapper.toEntity(pacienteNuevo);

        try {
            Paciente pacienteGuardado = pacienteDAO.registrarPaciente(paciente);
            return pacienteGuardado != null;
        } catch (PersistenciaClinicaException ex) {
            logger.log(Level.SEVERE, "Error al registrar el paciente", ex);
            throw new NegocioException("Hubo un error al registrar el paciente.", ex);
        }
    }
}
