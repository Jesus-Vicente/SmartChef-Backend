package org.example.smartchef.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrarHistorialDTO {

    private Integer idUsuario;
    private Integer idReceta;

    private LocalDateTime fecha_realizacion;



}
