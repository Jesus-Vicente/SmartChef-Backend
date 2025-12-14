package org.example.smartchef.controllers;


import lombok.AllArgsConstructor;
import org.example.smartchef.dto.HistorialDTO;
import org.example.smartchef.dto.RegistrarHistorialDTO;
import org.example.smartchef.models.Historial;
import org.example.smartchef.services.HistorialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historial-cocina")
@AllArgsConstructor
public class HistorialController {

    private HistorialService service;


    @GetMapping("/all/{idUsuario}")
    public List<HistorialDTO> obtenerHistorialUsuario(@PathVariable Integer idUsuario){
        return service.obtenerHistorialUsuario(idUsuario);
    }


    @GetMapping("/semanal/{idUsuario}")
    public List<HistorialDTO> obtenerHistorialSemanal(@PathVariable Integer idUsuario){
        return service.obtenerHistorialSemanal(idUsuario);
    }


    @PostMapping("/registrar-historial")
    public ResponseEntity<Historial> registarHistorial(@RequestBody RegistrarHistorialDTO dto){

        Historial nuevoHistorial = service.registrarHistorial(dto);

        return ResponseEntity.ok(nuevoHistorial);

    }


}
