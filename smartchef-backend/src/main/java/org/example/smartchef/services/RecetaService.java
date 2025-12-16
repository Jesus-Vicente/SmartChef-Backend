package org.example.smartchef.services;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.smartchef.converters.RecetaMapper;
import org.example.smartchef.converters.UsuarioMapper;
import org.example.smartchef.dto.*;
import org.example.smartchef.models.*;
import org.example.smartchef.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecetaService {

    private IRecetaRepository repository;
    private IIngredientesRepository ingredientesRepository;
    private IPreferenciaRepository preferenciaRepository;
    private IRecetaPreferenciaRepository recetaPreferenciaRepository;
    private IRecetaIngredienteRepository recetaIngredienteRepository;
    private IUsuarioRepository usuarioRepository;
    private IFotoRepository fotoRepository;

    private RecetaMapper mapper;
    private UsuarioMapper usuarioMapper;

    public List<RecetaDTO> obtenerRecetas(){
        return mapper.convertirADTO(repository.findAll());
    }

    public RecetaDTO obtenerDetallesReceta (Integer idReceta){
        return repository.findById(idReceta)
                .map(mapper::convertirADTO)
                .orElse(null);
    }

    @Transactional
    public void crearReceta(CrearRecetaDTO dto){

        // 1. Manejo de la Foto
        if (dto.getUrl_foto() != null && !dto.getUrl_foto().isEmpty()) {
            Foto nuevaFoto = new Foto();
            nuevaFoto.setUrl(dto.getUrl_foto());
            Foto fotoGuardada = fotoRepository.save(nuevaFoto);
            dto.setId_foto(fotoGuardada.getId());
        }

        // 2. BUSCAR EL USUARIO CREADOR COMPLETO
        Usuario usuarioCreador = usuarioRepository.findById(dto.getIdUsuarioCreador())
                .orElseThrow(() -> new RuntimeException("Usuario Creador no encontrado con ID: " + dto.getIdUsuarioCreador()));

        // 3. Mapear DTO a la Entidad Receta
        Receta receta = mapper.convertirAEntityCrearRecetaConIngredientes(dto);

        // 4. ASIGNAR LA ENTIDAD USUARIO COMPLETA A LA RECETA
        receta.setUsuario_creador_id(usuarioCreador);

        // 5. Guardar Receta
        Receta recetaGuardada = repository.save(receta);

        List<IngredienteRecetaDTO> ingredientesDetalles = dto.getIngredientesConDetalle();

        if (ingredientesDetalles != null && !ingredientesDetalles.isEmpty()) {
            Set<String> nombresABuscar = ingredientesDetalles.stream()
                    .map(i -> i.getNombre().toLowerCase())
                    .collect(Collectors.toSet());

            Map<String, Ingrediente> ingredientesExistentes = ingredientesRepository.findByNombreIn(nombresABuscar).stream()
                    .collect(Collectors.toMap(i -> i.getNombre().toLowerCase(), i -> i));

            List<RecetaIngrediente> relacionesIngredientes = new ArrayList<>();

            for (IngredienteRecetaDTO ingDto : ingredientesDetalles) {
                Ingrediente ingrediente = ingredientesExistentes.get(ingDto.getNombre().toLowerCase());

                if (ingrediente != null) {
                    RecetaIngrediente relacion = new RecetaIngrediente();
                    relacion.setId_receta(recetaGuardada);
                    relacion.setId_ingrediente(ingrediente);
                    relacion.setCantidad(ingDto.getCantidad());
                    relacion.setUnidad(ingDto.getUnidad());

                    relacionesIngredientes.add(relacion);
                }
            }

            recetaIngredienteRepository.saveAll(relacionesIngredientes);
        }

        List<Integer> idPreferencias = dto.getIdPreferencias();

        if (idPreferencias != null && !idPreferencias.isEmpty()) {
            List<Preferencia> preferencias = preferenciaRepository.findAllById(idPreferencias);

            List<RecetaPreferencia> relaciones = mapper.mapPreferenciasToRelaciones(preferencias, recetaGuardada);

            recetaPreferenciaRepository.saveAll(relaciones);
        }
    }



    public CrearRecetaDTO obtenerRecetasConIngredientes(@PathVariable Integer id){
        return repository.findById(id)
                .map(mapper::convertirADTOCrearRecetaConIngredientes)
                .orElse(null);
    }

    @Transactional
    public void crearRecetaConIngredientes(CrearRecetaDTO dto){
        Receta receta = mapper.convertirAEntityCrearRecetaConIngredientes(dto);

        Receta recetaGuardada = repository.save(receta);

        List<IngredienteRecetaDTO> ingredientesDetalles = dto.getIngredientesConDetalle();
        if (ingredientesDetalles != null && !ingredientesDetalles.isEmpty()) {

            Set<String> nombresABuscar = ingredientesDetalles.stream()
                    .map(i -> i.getNombre().toLowerCase())
                    .collect(Collectors.toSet());

            Map<String, Ingrediente> ingredientesExistentes = ingredientesRepository.findByNombreIn(nombresABuscar).stream()
                    .collect(Collectors.toMap(i -> i.getNombre().toLowerCase(), i -> i));

            List<RecetaIngrediente> relacionesIngredientes = new ArrayList<>();

            for (IngredienteRecetaDTO ingDto : ingredientesDetalles) {
                Ingrediente ingrediente = ingredientesExistentes.get(ingDto.getNombre().toLowerCase());

                if (ingrediente != null) {
                    RecetaIngrediente relacion = new RecetaIngrediente();
                    relacion.setId_receta(recetaGuardada);
                    relacion.setId_ingrediente(ingrediente);
                    relacion.setCantidad(ingDto.getCantidad());
                    relacion.setUnidad(ingDto.getUnidad());

                    relacionesIngredientes.add(relacion);
                }
            }

            recetaIngredienteRepository.saveAll(relacionesIngredientes);
        }
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


    public List<RecetaDTO> buscarRecetasCombinadas(FiltroRecetaDTO filtros) {
        List<Integer> idPreferencias = null;
        List<Integer> idIngredientes = null;

        if (filtros.getNombresPreferencias() != null && !filtros.getNombresPreferencias().isEmpty()) {
            List<Preferencia> preferencias = preferenciaRepository.findByNombrePreferenciaIn(filtros.getNombresPreferencias());
            idPreferencias = preferencias
                    .stream()
                    .map(Preferencia::getId)
                    .collect(Collectors.toList());
        }

        if (filtros.getNombresIngredientes() != null && !filtros.getNombresIngredientes().isEmpty()) {
            List<String> nombresMinusculas = filtros.getNombresIngredientes()
                    .stream()
                    .map(String::toLowerCase)
                    .collect(Collectors.toList());

            List<Ingrediente> ingredientes = ingredientesRepository.findByNombreIn(nombresMinusculas);
            idIngredientes = ingredientes
                    .stream()
                    .map(Ingrediente::getId)
                    .collect(Collectors.toList());
        }

        if ((idPreferencias == null || idPreferencias.isEmpty()) && (idIngredientes == null || idIngredientes.isEmpty())) {
            return mapper.convertirADTO(repository.findAll());
        }

        List<Receta> recetas = repository.buscarRecetasPorPreferenciasEIngredientes(idPreferencias, idIngredientes);

        return mapper.convertirADTO(recetas);
    }



    public List<IngredienteEstadisticasDTO> obtenerTop5Ingredientes() {
        return repository.findTop5IngredientesMasUtilizados();
    }

    public Optional<UsuarioPopularDTO> obtenerUsuarioConRecetaMasFavorita() {
        return repository.findTop1UsuarioPopular();
    }

    @Transactional
    public void modificarReceta(Integer id, CrearRecetaDTO dto) {

        Receta recetaNueva = repository.findById(id).orElse(null);
        if (recetaNueva == null) return;

        // ---------------- FOTO ----------------
        if (dto.getUrl_foto() != null && !dto.getUrl_foto().isEmpty()) {
            Foto foto = recetaNueva.getId_foto();
            if (foto != null) {
                foto.setUrl(dto.getUrl_foto());
                fotoRepository.save(foto);
            } else {
                Foto nueva = new Foto();
                nueva.setUrl(dto.getUrl_foto());
                recetaNueva.setId_foto(fotoRepository.save(nueva));
            }
        }

        // ---------------- DATOS BÁSICOS ----------------
        recetaNueva.setNombre(dto.getNombre());
        recetaNueva.setDescripcion(dto.getDescripcion());
        recetaNueva.setInstrucciones(dto.getInstrucciones());
        recetaNueva.setDificultad(dto.getDificultad());
        recetaNueva.setTiempo_preparacion(dto.getTiempo_preparacion());
        recetaNueva.setCosto_estimado(dto.getCosto_estimado());
        recetaNueva.setPorciones(dto.getPorciones());

        // ================= INGREDIENTES (CLAVE) =================
        recetaIngredienteRepository.eliminarIngredientesPorReceta(id);

        if (dto.getIngredientesConDetalle() != null && !dto.getIngredientesConDetalle().isEmpty()) {

            Set<String> nombres = dto.getIngredientesConDetalle()
                    .stream()
                    .map(i -> i.getNombre().toLowerCase())
                    .collect(Collectors.toSet());

            Map<String, Ingrediente> ingredientesMap = ingredientesRepository
                    .findByNombreIn(nombres)
                    .stream()
                    .collect(Collectors.toMap(i -> i.getNombre().toLowerCase(), i -> i));

            for (IngredienteRecetaDTO ingDto : dto.getIngredientesConDetalle()) {
                Ingrediente ingrediente = ingredientesMap.get(ingDto.getNombre().toLowerCase());
                if (ingrediente != null) {
                    RecetaIngrediente ri = new RecetaIngrediente();
                    ri.setId_receta(recetaNueva);
                    ri.setId_ingrediente(ingrediente);
                    ri.setCantidad(ingDto.getCantidad());
                    ri.setUnidad(ingDto.getUnidad());

                    recetaIngredienteRepository.save(ri);
                }
            }
        }

        // ================= PREFERENCIAS =================
        recetaNueva.getRecetaPreferencias().clear();

        if (dto.getIdPreferencias() != null && !dto.getIdPreferencias().isEmpty()) {
            List<Preferencia> prefs = preferenciaRepository.findAllById(dto.getIdPreferencias());

            for (Preferencia pref : prefs) {
                boolean existe = recetaPreferenciaRepository.existsByIdRecetaIdAndIdPreferenciaId(recetaNueva.getId(), pref.getId());
                if (!existe) {
                    RecetaPreferencia nuevaRelacion = new RecetaPreferencia(recetaNueva, pref);
                    recetaPreferenciaRepository.save(nuevaRelacion);
                }
            }
        }

    }


    @Transactional
    public void eliminarReceta(Integer id){
        Receta receta = repository.findById(id).orElse(null);

        if (receta != null) {
            repository.eliminarRelacionesReceta(id);
            if (receta.getId_foto() != null) {
                fotoRepository.delete(receta.getId_foto());
            }
            repository.deleteById(id);
        }
    }
}