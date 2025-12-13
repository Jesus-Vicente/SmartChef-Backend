package org.example.smartchef.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecetaIngredienteDTO {
    private Integer id;

    @NotNull
    @Positive
    private Double cantidad;

    private String unidad;

    @NotNull
    private Integer id_receta;
    @NotNull
    private Integer id_ingrediente;
}
