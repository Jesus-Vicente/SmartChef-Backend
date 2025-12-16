package org.example.smartchef.dto;

import jdk.dynalink.linker.LinkerServices;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FiltroRecetaDTO {

    private List<String> nombresIngredientes;

    private List<String> nombresPreferencias;

}
