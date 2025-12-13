package org.example.smartchef.controllers;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import org.example.smartchef.dto.CrearRecetaDTO;
import org.example.smartchef.dto.FavoritoDTO;
import org.example.smartchef.dto.MarcarFavoritoDTO;
import org.example.smartchef.dto.RecetaDTO;
import org.example.smartchef.models.Favorito;
import org.example.smartchef.models.Receta;
import org.example.smartchef.services.FavoritoService;
import org.example.smartchef.services.RecetaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/receta")
@AllArgsConstructor
public class RecetaController {

    private RecetaService service;

    private FavoritoService favoritoService;


    @GetMapping("/all")
    public List<RecetaDTO> obtenerRecetas(){
        return service.obtenerRecetas();
    }

    @PostMapping("/crear")
    public void crearReceta(@RequestBody RecetaDTO dto){
        service.crearReceta(dto);
    }



    @GetMapping("/recetasConIngredientes/{id}")
    public List<CrearRecetaDTO> obtenerRecetasConIngredientes(){
        return service.obtenerRecetasConIngredientes();
    }

    @PostMapping("/crearConIngredientes")
    public void crearRecetaConIngredientes(@RequestBody CrearRecetaDTO dto){
        service.crearRecetaConIngredientes(dto);
    }



    @PostMapping("/{idReceta}/favorito")
    public ResponseEntity<FavoritoDTO> marcarComoFavorito(@PathVariable("idReceta") Integer idReceta, @RequestBody MarcarFavoritoDTO dto){

        FavoritoDTO favorito = favoritoService.marcarComoFavorito(dto.getUsuarioId(), idReceta);

        if (favorito == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(favorito, HttpStatus.CREATED);
        }
    }


    @PutMapping("/ingrediente/vincular")
    public void vincularIngrediente(@Valid @RequestBody CrearRecetaDTO dto){}


    @PutMapping("/modificar/{id}")
    public void modificarReceta(@PathVariable Integer id, @RequestBody CrearRecetaDTO dto){
        service.modificarReceta(id, dto);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarReceta(@PathVariable Integer id){
        service.eliminarReceta(id);
    }

}
