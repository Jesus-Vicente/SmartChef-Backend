package org.example.smartchef.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.smartchef.converters.HistorialMapper;
import org.example.smartchef.dto.HistorialDTO;
import org.example.smartchef.repositories.IHistorialRepository;
import org.example.smartchef.repositories.IRecetaRepository;
import org.example.smartchef.repositories.IUsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HistorialService {

    private IHistorialRepository repository;
    private IRecetaRepository recetaRepository;
    private IUsuarioRepository usuarioRepository;

    private HistorialMapper mapper;



    public List<HistorialDTO> obtenerHistorialUsuario(Integer idUsuario){
        return mapper.convertirADTO(repository.findAll());
    }

    @Transactional
    public void registrarHistorial(Integer id_usuario, Integer id_receta){

    }

}
