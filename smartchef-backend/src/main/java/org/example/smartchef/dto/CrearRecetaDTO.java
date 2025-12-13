package org.example.smartchef.dto;

import lombok.*;
import org.example.smartchef.models.Dificultad;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CrearRecetaDTO {

    private String nombre;
    private String descripcion;
    private String instrucciones;
    private Dificultad dificultad;
    private Integer tiempo_preparacion;
    private Double costo_estimado;
    private Integer porciones;

    private Integer id_foto;
    private List<String> nombresIngredientes;
}
