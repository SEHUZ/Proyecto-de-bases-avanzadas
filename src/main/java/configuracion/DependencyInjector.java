/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package configuracion;

import BO.PacienteBO;
import Conexion.ConexionBD;
import Conexion.IConexionBD;

/**
 *
 * @author Daniel M
 */
public class DependencyInjector {

    public static PacienteBO crearPacienteBO() {
        IConexionBD conexion = new ConexionBD();
        PacienteBO pacienteBO = new PacienteBO(conexion);

        return pacienteBO;
    }
}
