package org.example.smartchef.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.smartchef.models.Foto;
import org.example.smartchef.models.Preferencia;
import org.example.smartchef.models.Receta;

import java.security.Timestamp;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UsuarioDTO {

    private Integer id;
    private String nombre;
    private String email;
    private String password;
    private String direccion;
    private String ciudad;
    private String pais;
    private String codigo_postal;
    private String telefono;
    private LocalDate fecha_registro;


    private Set<Preferencia> preferencias;

    //FK
    private Foto foto;

}
