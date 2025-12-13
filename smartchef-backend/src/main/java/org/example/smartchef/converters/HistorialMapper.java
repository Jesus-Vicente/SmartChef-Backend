package org.example.smartchef.converters;

import org.example.smartchef.dto.HistorialDTO;
import org.example.smartchef.models.Historial;
import org.example.smartchef.models.Receta;
import org.example.smartchef.models.Usuario;
import org.example.smartchef.services.RecetaService;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HistorialMapper {

    Historial convertirAEntity(HistorialDTO dto);

    List<Historial> convertirAEntity(List<HistorialDTO> dtos);

    HistorialDTO convertirADTO(Historial entity);

    List<HistorialDTO> convertirADTO(List<Historial> entidades);



    //Convertir Integer a Usuario
    default Usuario mapUsuario(Integer id){
        if (id == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }

    //Convertir Usurio a Integer
    default Integer mapUsuario(Usuario usuario){
        if (usuario == null) {
            return null;
        }
        return usuario.getId();
    }

    //Covertir Integer a Receta
    default Integer mapReceta(Integer id){
        if (id == null) {
            return null;
        }
        Receta receta = new Receta();
        receta.setId(id);
        return receta.getId();
    }

    //Convertir Receta a Integer
    default Integer mapReceta(Receta receta){
        if (receta == null) {
            return null;
        }
        return receta.getId();
    }

}
