package org.example.smartchef.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoritoDTO {

    private Integer id;
    private LocalDate fecha_guardado;

    private Integer usuario;
    private Integer receta;

}
