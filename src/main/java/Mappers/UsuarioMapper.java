/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;
import DTO.UsuarioNuevoDTO;
import Entidades.Usuario;

/**
 *
 * @author sonic
 */
public class UsuarioMapper {
    // Convertir de Entidad a DTO (Cliente → ClienteDTO)
        public static UsuarioNuevoDTO toDTO(Usuario usuario) {
            if (usuario == null) {
                return null;
            }
            return new UsuarioNuevoDTO(
                    usuario.getIdUsuario(),
                    usuario.getCorreoElectronico(),
                    usuario.getContrasenia(),
                    usuario.getRol()
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
