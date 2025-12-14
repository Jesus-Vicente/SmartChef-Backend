package org.example.smartchef.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecetaFiltrosDTO {
    private Integer id;
    private String nombre;
    private List<String> nombresIngredientes;
    private List<Integer> idPreferencias;
}
