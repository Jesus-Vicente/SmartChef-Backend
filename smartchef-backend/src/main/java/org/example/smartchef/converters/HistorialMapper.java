package org.example.smartchef.converters;

import org.example.smartchef.dto.HistorialDTO;
import org.example.smartchef.models.Historial;
import org.example.smartchef.models.Receta;
import org.example.smartchef.models.Usuario;
import org.example.smartchef.services.RecetaService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HistorialMapper {

    @Mapping(source = "idUsuario", target = "idUsuario")
    @Mapping(source = "idReceta", target = "idReceta")
    Historial convertirAEntity(HistorialDTO dto);

    List<Historial> convertirAEntity(List<HistorialDTO> dtos);

    @Mapping(source = "idUsuario", target = "idUsuario")
    @Mapping(source = "idReceta", target = "idReceta")
    HistorialDTO convertirADTO(Historial entity);

    List<HistorialDTO> convertirADTO(List<Historial> entidades);



    //Convertir Integer a Usuario
    default Usuario mapIdAUsuario(Integer id){
        if (id == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }

    //Convertir Usurio a Integer
    default Integer mapUsuarioAId(Usuario usuario){
        if (usuario == null) {
            return null;
        }
        return usuario.getId();
    }

    //Covertir Integer a Receta
    default Receta mapIdAReceta(Integer id){
        if (id == null) {
            return null;
        }
        Receta receta = new Receta();
        receta.setId(id);
        return receta;
    }

    //Convertir Receta a Integer
    default Integer mapRecetaAId(Receta receta){
        if (receta == null) {
            return null;
        }
        return receta.getId();
    }

}
