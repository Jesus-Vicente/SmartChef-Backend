package org.example.smartchef.dto;

import lombok.*;
import org.example.smartchef.models.Dificultad;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RecetaDTO {

    private Integer id;
    private String nombre;
    private String descripcion;
    private Dificultad dificultad;
    private String instrucciones;
    private Integer tiempo_preparacion;
    private Double costo_estimado;
    private Integer porciones;
    private Double valoracion_promedio;
    private LocalDate fecha_creacion;
    private boolean activa;

    private List<String> preferencias;
    private List<IngredienteRecetaDTO> ingredientesConDetalle;

    //FK
    private Integer usuario_creador_id;
    private Integer id_foto;
    private String url_foto;
}
