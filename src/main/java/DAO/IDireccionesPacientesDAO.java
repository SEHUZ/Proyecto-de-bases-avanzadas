/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Entidades.DireccionPaciente;
import Exception.PersistenciaClinicaException;

/**
 *
 * @author sonic
 */
public interface IDireccionesPacientesDAO {
    public interface IDireccionPacienteDAO {

    // Método para agregar una nueva dirección para un paciente
    public boolean agregarDireccion(DireccionPaciente direccion) throws PersistenciaClinicaException;

    // Método para consultar la dirección de un paciente por su ID
    public DireccionPaciente consultarDireccionPorPaciente(int idPaciente) throws PersistenciaClinicaException;

    // Método para actualizar la dirección de un paciente
    public boolean actualizarDireccion(DireccionPaciente direccion) throws PersistenciaClinicaException;
    }

}
