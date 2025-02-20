/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Entidades.Usuario;
import Exception.PersistenciaClinicaException;
import java.util.List;

/**
 *
 * @author sonic
 */
public interface IUsuarioDAO {
    public boolean registrarUsuario(Usuario usuario) throws PersistenciaClinicaException;
    
    public Usuario consultarUsuarioPorID(int idUsuario) throws PersistenciaClinicaException;
    
    public Usuario consultarUsuarioPorCorreo (String correoElectronico) throws PersistenciaClinicaException;
    
}
