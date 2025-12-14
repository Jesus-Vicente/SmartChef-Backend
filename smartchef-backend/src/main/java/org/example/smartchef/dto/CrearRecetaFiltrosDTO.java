package org.example.smartchef.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearRecetaFiltrosDTO {

    private List<Integer> ingredientes;

    private Integer idPreferencia;

}
