package org.example.smartchef.controllers;


import lombok.AllArgsConstructor;
import org.example.smartchef.dto.HistorialDTO;
import org.example.smartchef.services.HistorialService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/historial-cocina")
@AllArgsConstructor
public class HistorialController {

    private HistorialService service;


    @GetMapping("/all/{idUsuario}")
    public List<HistorialDTO> obtenerHistorialUsuario(Integer idUsuario){
        return service.obtenerHistorialUsuario(idUsuario);
    }

    @PostMapping("/")


}
