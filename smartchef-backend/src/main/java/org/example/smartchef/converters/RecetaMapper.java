package org.example.smartchef.converters;


import org.example.smartchef.dto.CrearRecetaDTO;
import org.example.smartchef.dto.RecetaDTO;
import org.example.smartchef.dto.RecetaFiltrosDTO;
import org.example.smartchef.models.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "Spring")
public interface RecetaMapper {

    @Mapping(source = "usuario_creador_id", target = "usuario_creador_id")
    @Mapping(source = "id_foto", target = "id_foto")
    Receta convertirAEntity(RecetaDTO dto);

    List<Receta> convertirAEntity(List<RecetaDTO> dtos);


    @Mapping(source = "usuario_creador_id.id", target = "usuario_creador_id")
    @Mapping(source = "id_foto.id", target = "id_foto")
    RecetaDTO convertirADTO(Receta entity);

    List<RecetaDTO> convertirADTO(List<Receta> receta);

    Receta convertirAEntityCrearRecetaConIngredientes(CrearRecetaDTO dto);

    @Mapping(source = "ingredientes", target = "nombresIngredientes")
    CrearRecetaDTO convertirADTOCrearRecetaConIngredientes(Receta receta);

    @Mapping(source = "ingredientes", target = "nombresIngredientes")
    List<CrearRecetaDTO> convertirADTOCrearRecetaConIngredientes(List<Receta> recetas);


    @Mapping(source = "ingredientes", target = "nombresIngredientes")
    @Mapping(source = "recetaPreferencias", target = "idPreferencias")
    RecetaFiltrosDTO convertirARecetaFiltrosDTO(Receta entity);

    // Mapeo para listas
    List<RecetaFiltrosDTO> convertirARecetaFiltrosDTO(List<Receta> recetas);


    default List<String> map(Set<Ingrediente> ingredientesID){
        if (ingredientesID == null) {
            return null;
        } else {
            return ingredientesID.stream().map(Ingrediente::getNombre).collect(Collectors.toList());
        }
    }

    default List<RecetaPreferencia> mapPreferenciasToRelaciones(
            List<Preferencia> preferencias,
            Receta recetaGuardada) {

        if (preferencias == null || recetaGuardada == null) {
            return null;
        }

        return preferencias.stream()
                .map(pref -> new RecetaPreferencia(recetaGuardada, pref))
                .collect(Collectors.toList());
    }

    default List<Integer> mapPreferenciasToIds(Set<RecetaPreferencia> recetaPreferencias){
        if (recetaPreferencias == null) {
            return null;
        } else {
            return recetaPreferencias.stream()
                    .map(rp -> rp.getIdPreferencia().getId())
                    .collect(Collectors.toList());
        }
    }


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



}
