package org.example.smartchef.converters;


import org.example.smartchef.dto.CrearUsuarioDTO;
import org.example.smartchef.dto.UsuarioDTO;
import org.example.smartchef.models.Foto;
import org.example.smartchef.models.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface UsuarioMapper {


    Usuario convertirAEntity(UsuarioDTO dto);

    List<Usuario> convertirAEntity(List<UsuarioDTO> dtos);


    UsuarioDTO convertirADTO(Usuario entity);

    List<UsuarioDTO> convertirADTO(List<Usuario> usuario);


    Usuario convertirAEntityCrearUsuario(CrearUsuarioDTO dto);

    CrearUsuarioDTO convertirADTOCrearUsuario(Usuario usuario);

    List<CrearUsuarioDTO> convertirADTOCrearUsuario(List<Usuario> usuarios);




    //Convertir Integer a Foto
    default Foto map(Integer id){
        if (id == null) {
            return null;
        }
        Foto foto = new Foto();
        foto.setId(id);
        return foto;
    }

    //Convertir Foto a Integer
    default Integer map(Foto foto){
        if (foto == null) {
            return null;
        }
        return foto.getId();
    }
}
