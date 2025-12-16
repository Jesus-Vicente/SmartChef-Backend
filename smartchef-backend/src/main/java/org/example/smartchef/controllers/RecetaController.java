package org.example.smartchef.controllers;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.smartchef.dto.*;
import org.example.smartchef.models.Favorito;
import org.example.smartchef.models.Receta;
import org.example.smartchef.services.FavoritoService;
import org.example.smartchef.services.RecetaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


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
    public void crearReceta(@RequestBody CrearRecetaDTO dto){
        service.crearReceta(dto);
    }



    @GetMapping("/recetasConIngredientes/{id}")
    public ResponseEntity<CrearRecetaDTO> obtenerRecetasConIngredientes(@PathVariable Integer id){
        CrearRecetaDTO dto = service.obtenerRecetasConIngredientes(id);

        if (dto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(dto);
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


    @GetMapping("/filtros")
    public ResponseEntity<List<RecetaFiltrosDTO>> buscarRecetas(
            @RequestParam(required = false) Integer idPreferencia ,
            @RequestParam(name = "ingredientes", required = false) List<Integer> ingredientes) {

        CrearRecetaFiltrosDTO filtros = new CrearRecetaFiltrosDTO(ingredientes, idPreferencia);

        List<RecetaFiltrosDTO> recetas = service.buscarRecetasConFiltros(filtros);

        if (recetas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(recetas);
    }

    @GetMapping("filtros-combinado")
    public ResponseEntity<List<RecetaDTO>> buscarRecetasCombinadas(
            @RequestParam(required = false) List<String> ingredientes,
            @RequestParam(required = false) List<String> preferencias) {

        FiltroRecetaDTO filtros = new FiltroRecetaDTO();
        filtros.setNombresIngredientes(ingredientes);
        filtros.setNombresPreferencias(preferencias);

        List<RecetaDTO> recetaFiltradas = service.buscarRecetasCombinadas(filtros);

        if (recetaFiltradas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(recetaFiltradas);
    }

    @GetMapping("/estadisticas/ingredientes")
    public ResponseEntity<List<IngredienteEstadisticasDTO>> getTop5Ingredientes() {

        List<IngredienteEstadisticasDTO> estadisticas = service.obtenerTop5Ingredientes();

        if (estadisticas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(estadisticas);
    }

    @GetMapping("/estadisticas/usuarioPopular")
    public ResponseEntity<UsuarioPopularDTO> getUsuarioPopular() {

        Optional<UsuarioPopularDTO> usuarioPopular = service.obtenerUsuarioConRecetaMasFavorita();

        if (usuarioPopular.isPresent()) {
            return ResponseEntity.ok(usuarioPopular.get());
        }

        return ResponseEntity.notFound().build();
    }


    @GetMapping("/detalle/{idReceta}")
    public ResponseEntity<RecetaDTO> obtenerDetallesReceta(@PathVariable("idReceta") Integer idReceta){
        RecetaDTO dto = service.obtenerDetallesReceta(idReceta);

        if (dto == null) {
            return ResponseEntity.notFound().build(); 
        }

        return ResponseEntity.ok(dto);
    }


    @PutMapping("/modificar/{id}")
    public void modificarReceta(@PathVariable Integer id, @RequestBody CrearRecetaDTO dto){
        service.modificarReceta(id, dto);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarReceta(@PathVariable Integer id){
        service.eliminarReceta(id);
    }

}
