package org.example.smartchef.services;


import lombok.AllArgsConstructor;
import org.example.smartchef.converters.RecetaMapper;
import org.example.smartchef.dto.CrearRecetaDTO;
import org.example.smartchef.dto.RecetaDTO;
import org.example.smartchef.models.Ingrediente;
import org.example.smartchef.models.Receta;
import org.example.smartchef.repositories.IIngredientesRepository;
import org.example.smartchef.repositories.IPreferenciaRepository;
import org.example.smartchef.repositories.IRecetaRepository;
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

    private RecetaMapper mapper;

    public List<RecetaDTO> obtenerRecetas(){
        return mapper.convertirADTO(repository.findAll());
    }

    public void crearReceta(RecetaDTO dto){
        repository.save(mapper.convertirAEntity(dto));
    }


    public List<CrearRecetaDTO> obtenerRecetasConIngredientes(){
        return mapper.convertirADTOCrearRecetaConIngredientes(repository.findAll());
    }

    public void crearRecetaConIngredientes(CrearRecetaDTO dto){
        Receta receta




        List<String> nombresIngredientes = dto.getNombresIngredientes();
        Set<String> nombresIngredientesCorrectos = nombresIngredientes.stream()
                .map(String::toLowerCase).collect(Collectors.toSet());

        List<Ingrediente> ingredientes = ingredientesRepository.findByNombreIn(nombresIngredientesCorrectos);

        receta.setIngredientes(new HashSet<>(ingredientes));

        repository.save(receta);
    }

    public void modificarReceta(Integer id, CrearRecetaDTO dto) {

        Optional<Receta> receta = repository.findById(id);
        if (receta.isPresent()) {
            Receta recetaAModificar = receta.get();
            recetaAModificar.setNombre(dto.getNombre());
            recetaAModificar.setDescripcion(dto.getDescripcion());
            recetaAModificar.setInstrucciones(dto.getInstrucciones());
            recetaAModificar.setDificultad(dto.getDificultad());
            recetaAModificar.setTiempo_preparacion(dto.getTiempo_preparacion());
            recetaAModificar.setCosto_estimado(dto.getCosto_estimado());
            recetaAModificar.setPorciones(dto.getPorciones());
            repository.save(recetaAModificar);
        }
    }

    public void eliminarReceta(Integer id){
        repository.deleteById(id);
    }
}
