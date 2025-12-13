package org.example.smartchef.controllers;


import lombok.AllArgsConstructor;
import org.example.smartchef.dto.CrearUsuarioDTO;
import org.example.smartchef.dto.UsuarioDTO;
import org.example.smartchef.services.PreferenciaService;
import org.example.smartchef.services.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@AllArgsConstructor
public class UsuarioController {

    private UsuarioService service;



    @GetMapping("/all")
    public List<UsuarioDTO> obtenerUsuarios(){
        return service.obtenerUsuarios();
    }

    @PostMapping("/crear")
    public void crearUsuario(@RequestBody CrearUsuarioDTO dto){
        service.crearUsuario(dto);
    }


    @GetMapping("/obtenerConPreferencias")
    public List<UsuarioDTO> obtenerUsuariosPreferencias(){
        return service.obtenerUsuarioPreferencias();
    }

    @PostMapping("/crearConPreferencias")
    public void crearUsuarioConPreferencias(@RequestBody CrearUsuarioDTO dto){
        service.crearUsuarioConPreferencias(dto);
    }


    @PutMapping("/modificar/{id}")
    public void modificarUsuario(@PathVariable Integer id, @RequestBody CrearUsuarioDTO dto){
        service.modificarUsuario(id, dto);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarUsuario(@PathVariable Integer id){
        service.eliminarUsuario(id);
    }


}
