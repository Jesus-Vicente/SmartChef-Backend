package org.example.smartchef.services;

import org.example.smartchef.converters.HistorialMapper;
import org.example.smartchef.dto.HistorialDTO;
import org.example.smartchef.dto.RegistrarHistorialDTO;
import org.example.smartchef.models.Historial;
import org.example.smartchef.models.Receta;
import org.example.smartchef.models.Usuario;
import org.example.smartchef.repositories.IHistorialRepository;
import org.example.smartchef.repositories.IRecetaRepository;
import org.example.smartchef.repositories.IUsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class HistorialServiceIntegrationTest {

    @InjectMocks
    private HistorialService service;

    @Mock
    private IHistorialRepository historialRepository;

    @Mock
    private IUsuarioRepository usuarioRepository;

    @Mock
    private IRecetaRepository recetaRepository;

    @Mock
    private HistorialMapper mapper;

    @Test
    @DisplayName("Servicio 7 -> Registro lógico de receta cocinada")
    public void registrarHistorialIntegrationTest() {
        // GIVEN
        Integer idUsuario = 1;
        Integer idReceta = 3;

        RegistrarHistorialDTO registrarHistorialDTO = new RegistrarHistorialDTO();
        registrarHistorialDTO.setIdUsuario(idUsuario);
        registrarHistorialDTO.setIdReceta(idReceta);
        registrarHistorialDTO.setFecha_realizacion(LocalDateTime.now());

        Usuario usuario = new Usuario();
        usuario.setId(idUsuario);

        Receta receta = new Receta();
        receta.setId(idReceta);

        Historial historial = new Historial();

        HistorialDTO historialDTO = new HistorialDTO();
        historialDTO.setIdUsuario(idUsuario);
        historialDTO.setIdReceta(idReceta);
        historialDTO.setEstado("EN PROCESO");

        Mockito.when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.of(usuario));
        Mockito.when(recetaRepository.findById(idReceta)).thenReturn(Optional.of(receta));

        Mockito.when(historialRepository.save(ArgumentMatchers.any(Historial.class)))
                .thenReturn(historial);
        Mockito.when(mapper.convertirADTO(ArgumentMatchers.any(Historial.class)))
                .thenReturn(historialDTO);

        // WHEN
        HistorialDTO historialDTODefinitivo = service.registrarHistorial(registrarHistorialDTO);

        // THEN
        assertNotNull(historialDTODefinitivo);
        assertEquals(idUsuario, historialDTODefinitivo.getIdUsuario());
        assertEquals("EN PROCESO", historialDTODefinitivo.getEstado());

        Mockito.verify(usuarioRepository).findById(idUsuario);
        Mockito.verify(recetaRepository).findById(idReceta);
        Mockito.verify(historialRepository).save(ArgumentMatchers.any(Historial.class));
    }


    @Test
    @DisplayName("Servicio 8 -> Consultar historial semanal de comidas (Caso Positivo)")
    public void obtenerHistorialUsuarioIntegrationTest() {
        // GIVEN
        Integer idUsuario = 1;

        Historial h1 = new Historial();
        h1.setId(101);

        Historial h2 = new Historial();
        h2.setId(102);

        List<Historial> listaHistorial = List.of(h1, h2);


        HistorialDTO dto1 = new HistorialDTO();
        dto1.setIdUsuario(idUsuario);
        dto1.setEstado("COMPLETADO");

        HistorialDTO dto2 = new HistorialDTO();
        dto2.setIdUsuario(idUsuario);
        dto2.setEstado("EN PROCESO");

        List<HistorialDTO> listaHistorialDTO = List.of(dto1, dto2);

        Mockito.when(historialRepository.findHistorialSemanal(
                        Mockito.eq(idUsuario),
                        Mockito.any(LocalDateTime.class),
                        Mockito.any(LocalDateTime.class)))
                .thenReturn(listaHistorial);

        Mockito.when(mapper.convertirADTO(listaHistorial))
                .thenReturn(listaHistorialDTO);

        // WHEN
        List<HistorialDTO> historialSemanal = service.obtenerHistorialSemanal(idUsuario);

        // THEN
        assertNotNull(historialSemanal);
        assertFalse(historialSemanal.isEmpty());
        assertEquals(2, historialSemanal.size());
        assertEquals(idUsuario, historialSemanal.get(0).getIdUsuario());

        Mockito.verify(historialRepository).findHistorialSemanal(
                Mockito.eq(idUsuario),
                Mockito.any(LocalDateTime.class),
                Mockito.any(LocalDateTime.class)
        );
    }
}
