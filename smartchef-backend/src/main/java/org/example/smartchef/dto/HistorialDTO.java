package org.example.smartchef.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistorialDTO {

    private Integer id;
    private LocalDateTime fecha_realizacion;
    private Integer duracion;
    private Double calificacion;
    private String comentario;
    private String estado;

    private Integer id_usuario;
    private Integer id_receta;


}
