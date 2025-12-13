package org.example.smartchef.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.smartchef.converters.FavoritoMapper;
import org.example.smartchef.dto.FavoritoDTO;
import org.example.smartchef.models.Favorito;
import org.example.smartchef.models.Receta;
import org.example.smartchef.models.Usuario;
import org.example.smartchef.repositories.IFavoritoRepository;
import org.example.smartchef.repositories.IRecetaRepository;
import org.example.smartchef.repositories.IUsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class FavoritoService {

    private IFavoritoRepository repository;
    private IUsuarioRepository usuarioRepository;
    private IRecetaRepository recetaRepository;

    private FavoritoMapper favoritoMapper;

    @Transactional
    public FavoritoDTO marcarComoFavorito(Integer id_usuario, Integer id_receta){

        Optional<Favorito> favoritoExistente = repository.findByUsuarioIdAndRecetaId(id_usuario, id_receta);

        if (favoritoExistente.isPresent()) {
            repository.delete(favoritoExistente.get());
            return null;
        } else {

            Usuario usuario = usuarioRepository.findById(id_usuario).orElseThrow();
            Receta receta = recetaRepository.findById(id_receta).orElseThrow();

            Favorito nuevoFavorito = new Favorito();
            nuevoFavorito.setUsuario(usuario);
            nuevoFavorito.setReceta(receta);
            nuevoFavorito.setFecha_guardado(java.time.LocalDate.now());

            Favorito favoritoguardado = repository.save(nuevoFavorito);

            return favoritoMapper.convertirADTO(favoritoguardado);
        }
    }

}
