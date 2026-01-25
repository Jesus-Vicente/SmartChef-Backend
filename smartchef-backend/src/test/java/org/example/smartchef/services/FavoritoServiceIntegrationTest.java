package org.example.smartchef.services;

import org.example.smartchef.converters.FavoritoMapper;
import org.example.smartchef.dto.FavoritoDTO;
import org.example.smartchef.models.Favorito;
import org.example.smartchef.models.Receta;
import org.example.smartchef.models.Usuario;
import org.example.smartchef.repositories.IRecetaRepository;
import org.example.smartchef.repositories.IUsuarioRepository;
import org.example.smartchef.repositories.IFavoritoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class FavoritoServiceIntegrationTest {

    @InjectMocks
    private FavoritoService service;

    @Mock
    private IRecetaRepository recetaRepository;

    @Mock
    private IUsuarioRepository usuarioRepository;

    @Mock
    private IFavoritoRepository favoritoRepository;

    // AÑADIR ESTE MOCK para evitar el NullPointerException
    @Mock
    private FavoritoMapper favoritoMapper;

    @Test
    @DisplayName("Servicio 5 -> Marcar receta como favorita (Relacional)")
    public void marcarRecetaComoFavoritaIntegrationTest() {
        // GIVEN
        Integer idUsuario = 1;
        Integer idReceta = 10;

        Usuario usuario = new Usuario();
        usuario.setId(idUsuario);

        Receta receta = new Receta();
        receta.setId(idReceta);

        Favorito favoritoGuardado = new Favorito();
        FavoritoDTO resultadoDTO = new FavoritoDTO();

        Mockito.when(this.usuarioRepository.findById(idUsuario))
                .thenReturn(Optional.of(usuario));

        Mockito.when(this.recetaRepository.findById(idReceta))
                .thenReturn(Optional.of(receta));

        Mockito.when(this.favoritoRepository.save(ArgumentMatchers.any(Favorito.class)))
                .thenReturn(favoritoGuardado);

        Mockito.when(this.favoritoMapper.convertirADTO(ArgumentMatchers.any(Favorito.class)))
                .thenReturn(resultadoDTO);

        // WHEN
        this.service.marcarComoFavorito(idUsuario, idReceta);

        // THEN
        Mockito.verify(this.usuarioRepository).findById(idUsuario);
        Mockito.verify(this.recetaRepository).findById(idReceta);
        Mockito.verify(this.favoritoRepository).save(ArgumentMatchers.any(Favorito.class));

        Mockito.verify(this.favoritoMapper).convertirADTO(ArgumentMatchers.any(Favorito.class));
    }
}