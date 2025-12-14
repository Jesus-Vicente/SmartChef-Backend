package org.example.smartchef.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.smartchef.converters.HistorialMapper;
import org.example.smartchef.dto.HistorialDTO;
import org.example.smartchef.dto.RegistrarHistorialDTO;
import org.example.smartchef.models.Historial;
import org.example.smartchef.models.Receta;
import org.example.smartchef.models.Usuario;
import org.example.smartchef.repositories.IHistorialRepository;
import org.example.smartchef.repositories.IRecetaRepository;
import org.example.smartchef.repositories.IUsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class HistorialService {

    private IHistorialRepository repository;
    private IRecetaRepository recetaRepository;
    private IUsuarioRepository usuarioRepository;

    private HistorialMapper mapper;



    public List<HistorialDTO> obtenerHistorialUsuario(Integer idUsuario){
        List<Historial> listaHistorial = repository.findByUsuarioId(idUsuario);

        return mapper.convertirADTO(listaHistorial);
    }


    public List<HistorialDTO> obtenerHistorialSemanal(Integer idUsuario){
        LocalDateTime fechaFin = LocalDateTime.now();
        LocalDateTime fechaInicio = fechaFin.minusWeeks(1);

        List<Historial> listaHistorial = repository.findHistorialSemanal(idUsuario, fechaInicio, fechaFin);

        return mapper.convertirADTO(listaHistorial);
    }


    @Transactional
    public Historial registrarHistorial(RegistrarHistorialDTO dto){
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario()).orElse(null);
        Receta receta = recetaRepository.findById(dto.getIdReceta()).orElse(null);

        if(usuario == null || receta == null){
            throw new IllegalArgumentException("Usuario o Receta no encontrados");
        }

        Historial registroHistorial = new Historial();
        registroHistorial.setIdUsuario(usuario);
        registroHistorial.setIdReceta(receta);

        LocalDateTime fechaRegistro = dto.getFecha_realizacion() != null ? dto.getFecha_realizacion() : LocalDateTime.now();

        registroHistorial.setFecha_realizacion(fechaRegistro);

        registroHistorial.setEstado("EN PROCESO");

        return repository.save(registroHistorial);
    }



}
