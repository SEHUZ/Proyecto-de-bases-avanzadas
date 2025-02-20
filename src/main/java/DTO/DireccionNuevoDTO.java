/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.time.LocalDate;

/**
 *
 * @author sonic
 */
public class DireccionNuevoDTO {
    private int idDireccion;
    private int idPaciente;
    private String calle;
    private String numero;
    private String cp;

    public DireccionNuevoDTO(int idDireccion, int idPaciente, String calle, String numero, String cp) {
        this.idDireccion = idDireccion;
        this.idPaciente = idPaciente;
        this.calle = calle;
        this.numero = numero;
        this.cp = cp;
    }

    public int getIdDireccion() {
        return idDireccion;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public String getCalle() {
        return calle;
    }

    public String getNumero() {
        return numero;
    }

    public String getCp() {
        return cp;
    }
    
    

    
    
    
}
