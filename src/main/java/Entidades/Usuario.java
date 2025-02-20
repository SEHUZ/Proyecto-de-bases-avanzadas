/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author sonic
 */
public class Usuario {
    private int idUsuario;
    private String correoElectronico;
    private String contrasenia;
    private String rol;

    public Usuario(int idUsuario, String correoElectronico, String contrasenia, String rol) {
        this.idUsuario = idUsuario;
        this.correoElectronico = correoElectronico;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }

    public Usuario() {
    }

    public Usuario(String correoElectronico, String contrasenia, String rol) {
        this.correoElectronico = correoElectronico;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Usuario{" + "idUsuario=" + idUsuario + ", correoElectronico=" + correoElectronico + ", contrasenia=" + contrasenia + ", rol=" + rol + '}';
    }
    
    
    
    
}
