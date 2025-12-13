package org.example.smartchef.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreferenciaDTO {

    private Integer id;
    private String nombrePreferencia;
    private String descripcion;


}
