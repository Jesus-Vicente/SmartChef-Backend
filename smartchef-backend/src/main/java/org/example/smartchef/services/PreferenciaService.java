package org.example.smartchef.services;

import lombok.AllArgsConstructor;
import org.example.smartchef.converters.PreferenciaMapper;
import org.example.smartchef.dto.CrearPreferenciaDTO;
import org.example.smartchef.dto.PreferenciaDTO;
import org.example.smartchef.models.Preferencia;
import org.example.smartchef.repositories.IPreferenciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PreferenciaService {

    private IPreferenciaRepository repository;

    private PreferenciaMapper mapper;

    public List<PreferenciaDTO> obtenerPreferencias(){
        return mapper.convertirADTO(repository.findAll());
    }

    public void crearPreferencia(CrearPreferenciaDTO dto){
        repository.save(mapper.convertirAEntityCrearPreferencia(dto));
    }

    public void modificarPreferencia(Integer id, PreferenciaDTO dto){

        Optional<Preferencia> preferencia = repository.findById(id);

        if (preferencia.isPresent()) {
            Preferencia preferenciaAModificar = preferencia.get();
            preferenciaAModificar.setNombrePreferencia(dto.getNombrePreferencia());
        }
    }

    public void eliminarPreferencia(Integer id){
        repository.deleteById(id);
    }

}
