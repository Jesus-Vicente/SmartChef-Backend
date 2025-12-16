package org.example.smartchef.converters;

import org.example.smartchef.dto.CrearRecetaDTO;
import org.example.smartchef.dto.IngredienteRecetaDTO;
import org.example.smartchef.dto.RecetaDTO;
import org.example.smartchef.dto.RecetaFiltrosDTO;
import org.example.smartchef.models.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "Spring")
public interface RecetaMapper {

    @Mapping(source = "usuario_creador_id", target = "usuario_creador_id", qualifiedByName = "mapUsuario")
    @Mapping(source = "id_foto", target = "id_foto", qualifiedByName = "mapFoto")
    @Mapping(target = "recetaPreferencias", ignore = true)
    Receta convertirAEntity(RecetaDTO dto);

    List<Receta> convertirAEntity(List<RecetaDTO> dtos);


    @Mapping(source = "usuario_creador_id.id", target = "usuario_creador_id")
    @Mapping(source = "id_foto.id", target = "id_foto")
    @Mapping(source = "id_foto.url", target = "url_foto")  // Mapeo correcto de url_foto
    @Mapping(source = "receta_ingrediente", target = "ingredientesConDetalle", qualifiedByName = "mapRecetaIngredienteToDTO")
    @Mapping(source = "recetaPreferencias", target = "preferencias", qualifiedByName = "mapPreferenciasToString")
    RecetaDTO convertirADTO(Receta entity);

    List<RecetaDTO> convertirADTO(List<Receta> receta);


    @Mapping(target = "receta_ingrediente", ignore = true)
    @Mapping(target = "recetaPreferencias", ignore = true)
    @Mapping(source = "idUsuarioCreador", target = "usuario_creador_id", qualifiedByName = "mapUsuario")
    @Mapping(source = "id_foto", target = "id_foto", qualifiedByName = "mapFoto")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "valoracion_promedio", ignore = true)
    @Mapping(target = "fecha_creacion", ignore = true)
    @Mapping(target = "activa", ignore = true)
    Receta convertirAEntityCrearRecetaConIngredientes(CrearRecetaDTO dto);

    @Mapping(source = "receta_ingrediente", target = "ingredientesConDetalle", qualifiedByName = "mapRecetaIngredienteToDTO")
    @Mapping(source = "recetaPreferencias", target = "idPreferencias", qualifiedByName = "mapPreferenciasToIds")
    @Mapping(source = "usuario_creador_id.id", target = "idUsuarioCreador")
    @Mapping(source = "id_foto.id", target = "id_foto")
    CrearRecetaDTO convertirADTOCrearRecetaConIngredientes(Receta receta);


    @Mapping(source = "receta_ingrediente", target = "nombresIngredientes", qualifiedByName = "mapRecetaIngredienteToString")
    RecetaFiltrosDTO convertirARecetaFiltrosDTO(Receta entity);

    List<RecetaFiltrosDTO> convertirARecetaFiltrosDTO(List<Receta> recetas);

    // Mapea Set<RecetaIngrediente> a List<IngredienteRecetaDTO> (para obtener detalles)
    @org.mapstruct.Named("mapRecetaIngredienteToDTO")
    default List<IngredienteRecetaDTO> mapRecetaIngredienteToDTO(Set<RecetaIngrediente> recetaIngredientes){
        if (recetaIngredientes == null) {
            return null;
        }
        return recetaIngredientes.stream()
                .map(relacion -> {
                    IngredienteRecetaDTO dto = new IngredienteRecetaDTO();
                    dto.setNombre(relacion.getId_ingrediente().getNombre());
                    dto.setCantidad(relacion.getCantidad());
                    dto.setUnidad(relacion.getUnidad());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // Mapea Set<RecetaIngrediente> a List<String> (solo nombres, para filtros o vistas simples)
    @org.mapstruct.Named("mapRecetaIngredienteToString")
    default List<String> mapRecetaIngredienteToString(Set<RecetaIngrediente> recetaIngredientes){
        if (recetaIngredientes == null) {
            return null;
        }
        return recetaIngredientes.stream()
                .map(relacion -> relacion.getId_ingrediente().getNombre())
                .collect(Collectors.toList());
    }


    // MÉTODOS PARA RELACIONES CON PREFERENCIAS

    // Convierte Set<RecetaPreferencia> a List<String> (Nombres de Preferencias)
    @org.mapstruct.Named("mapPreferenciasToString")
    default List<String> mapPreferenciasToString(Set<RecetaPreferencia> recetaPreferencias){
        if (recetaPreferencias == null) {
            return null;
        } else {
            return recetaPreferencias.stream()
                    .map(rp -> rp.getIdPreferencia().getNombrePreferencia())
                    .collect(Collectors.toList());
        }
    }

    // Convierte List<Preferencia> + Receta a List<RecetaPreferencia> (para guardar la relación)
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

    // Convierte Set<RecetaPreferencia> a List<Integer> (IDs de Preferencias)
    @org.mapstruct.Named("mapPreferenciasToIds")
    default List<Integer> mapPreferenciasToIds(Set<RecetaPreferencia> recetaPreferencias){
        if (recetaPreferencias == null) {
            return null;
        } else {
            return recetaPreferencias.stream()
                    .map(rp -> rp.getIdPreferencia().getId())
                    .collect(Collectors.toList());
        }
    }


    // MÉTODOS PARA ID/ENTIDAD (Foto y Usuario)

    // Mapeo: Integer (ID) a Foto (Entidad Stub)
    @org.mapstruct.Named("mapFoto")
    default Foto map(Integer id){
        if (id == null) {
            return null;
        }
        Foto foto = new Foto();
        foto.setId(id);
        return foto;
    }

    // Mapeo: Foto (Entidad) a Integer (ID)
    default Integer map(Foto foto){
        return (foto == null) ? null : foto.getId();
    }


    // Mapeo: Integer (ID) a Usuario (Entidad Stub)
    @org.mapstruct.Named("mapUsuario")
    default Usuario mapUsuario(Integer id){
        if (id == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }

    // Mapeo: Usuario (Entidad) a Integer (ID)
    default Integer mapUsuario(Usuario usuario){
        return (usuario == null) ? null : usuario.getId();
    }
}
