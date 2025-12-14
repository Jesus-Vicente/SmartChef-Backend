package org.example.smartchef.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioPopularDTO {

    private Integer idUsuario;
    private String nombreUsuario;
    private String nombreRecetaPopular;
    private Long cantidadFavoritos;

}