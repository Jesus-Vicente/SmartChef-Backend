package org.example.smartchef.repositories;

import org.example.smartchef.dto.IngredienteEstadisticasDTO;
import org.example.smartchef.dto.UsuarioPopularDTO;
import org.example.smartchef.models.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRecetaRepository extends JpaRepository<Receta, Integer> {

    @Query("SELECT r FROM Receta r WHERE (1=1) " +
            "AND (:idPreferencia IS NULL OR EXISTS " +
            "(SELECT rp FROM RecetaPreferencia rp WHERE rp.idReceta = r AND rp.idPreferencia.id = :idPreferencia))" +
            "AND EXISTS " +
            "(SELECT ri FROM RecetaIngrediente ri WHERE ri.id_receta = r AND ri.id_ingrediente.id IN (:ingredientes))"
    )
    List<Receta> buscarConFiltros(
            @Param("idPreferencia") Integer idPreferencia,
            @Param("ingredientes") List<Integer> ingredientes
    );


    @Query("SELECT r FROM Receta r WHERE (1=1) " +
            "AND (:idPreferencia IS NULL OR EXISTS " +
            "(SELECT rp FROM RecetaPreferencia rp WHERE rp.idReceta = r AND rp.idPreferencia.id = :idPreferencia))"
    )
    List<Receta> buscarSinFiltroIngredientes(
            @Param("idPreferencia") Integer idPreferencia
    );


    @Query(value = "SELECT i.nombre AS nombreIngrediente, COUNT(ri.id_receta) AS cantidadRecetas " +
            "FROM public.ingrediente i " +
            "JOIN public.receta_ingrediente ri ON ri.id_ingrediente = i.id " +
            "GROUP BY i.id, i.nombre " +
            "ORDER BY cantidadRecetas DESC " +
            "LIMIT 5",
            nativeQuery = true)
    List<IngredienteEstadisticasDTO> findTop5IngredientesMasUtilizados();


    @Query("SELECT NEW org.example.smartchef.dto.UsuarioPopularDTO(" +
            "r.usuario_creador_id.id, " +
            "r.usuario_creador_id.nombre, " +
            "r.nombre, " +
            "COUNT(f.id)) " +
            "FROM Favorito f " +
            "JOIN f.receta r " +
            "GROUP BY r.usuario_creador_id.id, r.usuario_creador_id.nombre, r.nombre " +
            "ORDER BY COUNT(f.id) DESC"
    )
    Optional<UsuarioPopularDTO> findTop1UsuarioPopular();

    @Modifying
    @Query(value = "delete from smartchef.receta_ingrediente ri where ri.id_receta = :idReceta ", nativeQuery = true)
    void eliminarRelacionesReceta(@Param("idReceta") Integer idReceta);
}