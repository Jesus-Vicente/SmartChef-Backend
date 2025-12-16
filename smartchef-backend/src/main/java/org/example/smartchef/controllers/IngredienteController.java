package org.example.smartchef.controllers;

import lombok.AllArgsConstructor;
import org.example.smartchef.dto.IngredienteDTO;
import org.example.smartchef.services.IngredienteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ingrediente")
@AllArgsConstructor
public class IngredienteController {

    private IngredienteService service;

    @GetMapping("/all")
    public List<IngredienteDTO> obtenerIngredientes(){
        return service.obtenerIngredientes();
    }

    @GetMapping("/nombresFiltro")
    public List<String> obtenerTodosIngredientesFiltro() {
        return service.obtenerTodosIngredientesFiltro();
    }

}
