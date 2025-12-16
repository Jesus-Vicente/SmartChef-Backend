package org.example.smartchef.services;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import org.example.smartchef.converters.IngredienteMapper;
import org.example.smartchef.dto.IngredienteDTO;
import org.example.smartchef.repositories.IIngredientesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class IngredienteService {

    private IIngredientesRepository repository;

    private IngredienteMapper mapper;

    public List<IngredienteDTO> obtenerIngredientes() {
        return mapper.convertirADTO(repository.findAll());
    }

    public List<String> obtenerTodosIngredientesFiltro(){
        return repository.findAll()
                .stream()
                .map(ingrediente -> ingrediente.getNombre())
                .collect(Collectors.toList());
    }

}
