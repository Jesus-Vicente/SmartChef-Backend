package org.example.smartchef.converters;

import org.example.smartchef.dto.FavoritoDTO;
import org.example.smartchef.models.Favorito;
import org.example.smartchef.models.Receta;
import org.example.smartchef.models.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FavoritoMapper {

    @Mapping(source = "usuario", target = "usuario.id")
    @Mapping(source = "receta", target = "receta.id")
    Favorito convertirAEntity(FavoritoDTO dto);

    @Mapping(source = "usuario.id", target = "usuario")
    @Mapping(source = "receta.id", target = "receta")
    FavoritoDTO convertirADTO(Favorito entity);


    default Usuario mapIdToUsuario(Integer id) {
        if (id == null) return null;
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }


    default Receta mapIdToReceta(Integer id) {
        if (id == null) return null;
        Receta receta = new Receta();
        receta.setId(id);
        return receta;
    }

}
