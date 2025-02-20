/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTO.PacienteNuevoDTO;
import DTO.UsuarioNuevoDTO;
import Entidades.Paciente;
import Entidades.Usuario;

/**
 *
 * @author sonic
 */
public class PacienteMapper {
    public static PacienteNuevoDTO toDTO(Paciente paciente) {
            if (paciente == null) {
                return null;
            }
            return new PacienteNuevoDTO(
                    paciente.getIdPaciente(),
                    paciente.getApellidoPaterno(),
                    paciente.getApellidoMaterno(),
                    paciente.getNombres(),
                    paciente.getTelefono(),
                    paciente.getFechaNacimiento()
            );
        }

        // Convertir de DTO a Entidad (ClienteDTO → Cliente)
        public static Usuario toEntity(UsuarioNuevoDTO usuarioDTO) {
            if (usuarioDTO == null) {
                return null;
            }
            return new Usuario(
                    usuarioDTO.getIdUsuario(),
                    usuarioDTO.getCorreoElectronico(),
                    usuarioDTO.getContrasenia(),
                    usuarioDTO.getRol()
            );
        }
}
