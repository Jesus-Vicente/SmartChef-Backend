package org.example.smartchef.dto;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.smartchef.models.CarritoIngrediente;
import org.example.smartchef.models.Usuario;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarritoCompraDTO {


    private Integer id;
    private LocalDate fecha_creacion;
    private String estado;
    private Double total_estimado;
    private String direccion_envio;
    private String notas;

    private Integer usuarioId;

    private List<CarritoIngredienteDTO> carritoIngrediente;
}
