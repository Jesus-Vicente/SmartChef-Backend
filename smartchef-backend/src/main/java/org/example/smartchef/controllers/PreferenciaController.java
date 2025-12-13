package org.example.smartchef.controllers;


import lombok.AllArgsConstructor;
import org.example.smartchef.dto.CrearPreferenciaDTO;
import org.example.smartchef.dto.PreferenciaDTO;
import org.example.smartchef.services.PreferenciaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/preferencia")
@AllArgsConstructor
public class PreferenciaController {

    private PreferenciaService service;

    @GetMapping("/all")
    public List<PreferenciaDTO> obtenerPreferencias(){
        return service.obtenerPreferencias();
    }

    @PostMapping("/crear")
    public void crearPreferencia(@RequestBody CrearPreferenciaDTO dto){
        service.crearPreferencia(dto);
    }

    @PutMapping("/modificar/{id}")
    public void modificarPreferencia(@PathVariable Integer id, @RequestBody PreferenciaDTO dto){
        service.modificarPreferencia(id, dto);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarPreferencia(@PathVariable Integer id){
        service.eliminarPreferencia(id);
    }

}
