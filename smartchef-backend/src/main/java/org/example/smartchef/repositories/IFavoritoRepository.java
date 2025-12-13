package org.example.smartchef.repositories;

import org.example.smartchef.models.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IFavoritoRepository extends JpaRepository<Favorito, Integer> {

    Optional<Favorito> findByUsuarioIdAndRecetaId(Integer usuarioId, Integer recetaId);

}
