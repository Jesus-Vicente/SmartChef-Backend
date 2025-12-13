package org.example.smartchef.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.smartchef.models.CarritoCompra;
import org.example.smartchef.models.Ingrediente;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarritoIngredienteDTO {

    private Integer id;
    private Double cantidad;
    private String unidad;
    private Boolean comprado;

    private Integer id_carrito;
    private Integer id_ingrediente;
}
