/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTO.PacienteNuevoDTO;
import DTO.PacienteViejoDTO;
import Entidades.Paciente;

/**
 *
 * @author sonic
 */
public class PacienteMapper {

    // Convertir PacienteNuevoDTO a entidad Paciente
    public static Paciente toEntity(PacienteNuevoDTO pacienteNuevo) {
        if (pacienteNuevo == null) {
            return null;
        }
        return new Paciente(
                Integer.parseInt(pacienteNuevo.getIdUsuario()),
                pacienteNuevo.getNombres(),
                pacienteNuevo.getApellidoPaterno(),
                pacienteNuevo.getApellidoMaterno(),
                pacienteNuevo.getTelefono(),
                pacienteNuevo.getFechaNacimiento()
        );
    }

    // Convertir entidad Paciente a PacienteNuevoDTO
    public PacienteNuevoDTO toNuevoDTO(Paciente paciente) {
        if (paciente == null) {
            return null;
        }
        return new PacienteNuevoDTO(
                String.valueOf(paciente.getIdUsuario()),
                paciente.getNombres(),
                paciente.getApellidoPaterno(),
                paciente.getApellidoMaterno(),
                paciente.getTelefono(),
                paciente.getFechaNacimiento()
        );
    }

    // Convertir entidad Paciente a PacienteViejoDTO
    public PacienteViejoDTO toViejoDTO(Paciente paciente) {
        if (paciente == null) {
            return null;
        }
        return new PacienteViejoDTO(
                String.valueOf(paciente.getIdPaciente()),
                String.valueOf(paciente.getIdUsuario()),
                paciente.getNombres(),
                paciente.getApellidoPaterno(),
                paciente.getApellidoMaterno(),
                paciente.getTelefono(),
                paciente.getFechaNacimiento()
        );
    }
}
