package org.example.smartchef.services;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.smartchef.converters.RecetaMapper;
import org.example.smartchef.converters.UsuarioMapper;
import org.example.smartchef.dto.*;
import org.example.smartchef.models.Ingrediente;
import org.example.smartchef.models.Preferencia;
import org.example.smartchef.models.Receta;
import org.example.smartchef.models.RecetaPreferencia;
import org.example.smartchef.repositories.IIngredientesRepository;
import org.example.smartchef.repositories.IPreferenciaRepository;
import org.example.smartchef.repositories.IRecetaPreferenciaRepository;
import org.example.smartchef.repositories.IRecetaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecetaService {

    private IRecetaRepository repository;
    private IIngredientesRepository ingredientesRepository;
    private IPreferenciaRepository preferenciaRepository;
    private IRecetaPreferenciaRepository recetaPreferenciaRepository;

    private RecetaMapper mapper;
    private UsuarioMapper usuarioMapper;

    public List<RecetaDTO> obtenerRecetas(){
        return mapper.convertirADTO(repository.findAll());
    }


    public void crearReceta(CrearRecetaDTO dto){
        Receta receta = mapper.convertirAEntityCrearRecetaConIngredientes(dto);

        List<String> nombresIngredientes = dto.getNombresIngredientes();
        if (nombresIngredientes != null && !nombresIngredientes.isEmpty()) {
            Set<String> nombresIngredientesCorrectos = nombresIngredientes.stream()
                    .map(String::toLowerCase).collect(Collectors.toSet());

            List<Ingrediente> ingredientes = ingredientesRepository.findByNombreIn(nombresIngredientesCorrectos);
            receta.setIngredientes(new HashSet<>(ingredientes));
        }

        Receta recetaGuardada = repository.save(receta);

        List<Integer> idPreferencias = dto.getIdPreferencias();

        if (idPreferencias != null && !idPreferencias.isEmpty()) {
            List<Preferencia> preferencias = preferenciaRepository.findAllById(idPreferencias);

            List<RecetaPreferencia> relaciones = mapper.mapPreferenciasToRelaciones(preferencias, recetaGuardada);

            recetaPreferenciaRepository.saveAll(relaciones);
        }
    }


    public List<CrearRecetaDTO> obtenerRecetasConIngredientes(){
        return mapper.convertirADTOCrearRecetaConIngredientes(repository.findAll());
    }

    public void crearRecetaConIngredientes(CrearRecetaDTO dto){
        Receta receta = mapper.convertirAEntityCrearRecetaConIngredientes(dto);

        List<String> nombresIngredientes = dto.getNombresIngredientes();
        Set<String> nombresIngredientesCorrectos = nombresIngredientes.stream()
                .map(String::toLowerCase).collect(Collectors.toSet());

        List<Ingrediente> ingredientes = ingredientesRepository.findByNombreIn(nombresIngredientesCorrectos);

        receta.setIngredientes(new HashSet<>(ingredientes));

        repository.save(receta);
    }


    @Transactional
    public List<RecetaFiltrosDTO> buscarRecetasConFiltros(CrearRecetaFiltrosDTO filtros){

        Integer idPreferencia = filtros.getIdPreferencia();

        List<Integer> ingredientes = filtros.getIngredientes();

        if (ingredientes == null || ingredientes.isEmpty()) {
            ingredientes = null;
        }

        List<Receta> recetas;

        if (ingredientes != null) {
            recetas = repository.buscarConFiltros(
                    idPreferencia,
                    ingredientes
            );
        } else {
            recetas = repository.buscarSinFiltroIngredientes(
                    idPreferencia
            );
        }

        return mapper.convertirARecetaFiltrosDTO(recetas);
    }

    public List<IngredienteEstadisticasDTO> obtenerTop5Ingredientes() {
        return repository.findTop5IngredientesMasUtilizados();
    }

    public Optional<UsuarioPopularDTO> obtenerUsuarioConRecetaMasFavorita() {
        return repository.findTop1UsuarioPopular();
    }


    public void modificarReceta(Integer id, CrearRecetaDTO dto) {

        Receta recetaNueva = repository.findById(id).orElse(null);

        if (recetaNueva != null) {
            recetaNueva.setNombre(dto.getNombre());
            recetaNueva.setDescripcion(dto.getDescripcion());
            recetaNueva.setInstrucciones(dto.getInstrucciones());
            recetaNueva.setDificultad(dto.getDificultad());
            recetaNueva.setTiempo_preparacion(dto.getTiempo_preparacion());
            recetaNueva.setCosto_estimado(dto.getCosto_estimado());
            recetaNueva.setPorciones(dto.getPorciones());
            repository.save(recetaNueva);
        }
    }

    @Transactional
    public void eliminarReceta(Integer id){
        repository.eliminarRelacionesReceta(id);
        repository.deleteById(id);
    }
}
