package org.example.smartchef.converters;

import org.example.smartchef.dto.IngredienteDTO;
import org.example.smartchef.models.Ingrediente;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface IngredienteMapper {

    IngredienteDTO convertirADTO(Ingrediente entity);
    List<IngredienteDTO> convertirADTO(List<Ingrediente> entities);

    Ingrediente convertirAEntity(IngredienteDTO dto);
    List<Ingrediente> convertirAEntity(List<IngredienteDTO> dtos);

}
