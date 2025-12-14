package org.example.smartchef.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredienteEstadisticasDTO {

    private String nombreIngrediente;
    private Long cantidadRecetas;

}