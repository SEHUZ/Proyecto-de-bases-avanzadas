/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Entidades.Paciente;
import Exception.PersistenciaClinicaException;

/**
 *
 * @author sonic
 */
public interface IPacienteDAO {
    
    public Paciente registrarPaciente(Paciente paciente) throws PersistenciaClinicaException;
    
    public Paciente consultarPacientePorID(int id) throws PersistenciaClinicaException;
}
