package org.example.smartchef.converters;

import org.example.smartchef.dto.CrearPreferenciaDTO;
import org.example.smartchef.dto.PreferenciaDTO;
import org.example.smartchef.models.Preferencia;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PreferenciaMapper {

    Preferencia convertirAEntity(PreferenciaDTO dto);
    List<Preferencia> convertirAEntity(List<PreferenciaDTO> dtos);


    PreferenciaDTO convertirADTO(Preferencia entity);
    List<PreferenciaDTO> convertirADTO(List<Preferencia> preferencia);

    Preferencia convertirAEntityCrearPreferencia(CrearPreferenciaDTO dto);
    CrearPreferenciaDTO convertirADTOCrearPreferencia(Preferencia preferencia);
    List<CrearPreferenciaDTO> convertirADTOCrearPreferencia(List<Preferencia> preferencias);


}
