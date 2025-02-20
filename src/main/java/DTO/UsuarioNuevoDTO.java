/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author sonic
 */
public class UsuarioNuevoDTO {
    private int idUsuario;
    private String correoElectronico;
    private String contrasenia;
    private String rol;
    
    public UsuarioNuevoDTO(int idUsuario, String correoElectronico, String contrasenia, String rol) {
        this.idUsuario = idUsuario;
        this.correoElectronico = correoElectronico;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public String getRol() {
        return rol;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" + "idUsuario=" + idUsuario + ", correoElectronico=" + correoElectronico + ", contrasenia=" + contrasenia + ", rol=" + rol + '}';
    }
}
