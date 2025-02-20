/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTO.DireccionNuevoDTO;
import Entidades.DireccionPaciente;

/**
 *
 * @author sonic
 */
public class DireccionPacienteMapper {

    // Convertir de Entidad a DTO (DireccionPaciente → DireccionNuevoDTO)
    public static DireccionNuevoDTO toDTO(DireccionPaciente direccion) {
        if (direccion == null) {
            return null;
        }
        return new DireccionNuevoDTO(
            direccion.getIdDireccion(),
            direccion.getIdPaciente(),
            direccion.getCalle(),
            direccion.getNumero(),
            direccion.getCp()
        );
    }

    // Convertir de DTO a Entidad (DireccionNuevoDTO → DireccionPaciente)
    public static DireccionPaciente toEntity(DireccionNuevoDTO direccionDTO) {
        if (direccionDTO == null) {
            return null;
        }
        return new DireccionPaciente(
            direccionDTO.getIdDireccion(),  // Puede ser 0 o nulo si es nuevo y se asigna luego el ID
            direccionDTO.getIdPaciente(),   // Igualmente, se asigna cuando se conoce el idPaciente
            direccionDTO.getCalle(),
            direccionDTO.getNumero(),
            direccionDTO.getCp()
        );
    }
}