package org.example.smartchef.dto;

import lombok.*;
import org.example.smartchef.models.Preferencia;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearUsuarioDTO {

    private String nombre;
    private String email;
    private String password;
    private String direccion;
    private String ciudad;
    private String pais;
    private String codigo_postal;
    private String telefono;

    private Set<Integer> preferenciasID;

}
