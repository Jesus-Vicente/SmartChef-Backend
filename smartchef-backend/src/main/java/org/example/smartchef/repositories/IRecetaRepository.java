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


    @Query("select new org.example.smartchef.dto.UsuarioPopularDTO(" +
            "r.usuario_creador_id.id, " +
            "r.usuario_creador_id.nombre, " +
            "r.nombre, " +
            "count(f.id)) " +
            "from Favorito f " +
            "join f.receta r " +
            "group by r.usuario_creador_id.id, r.usuario_creador_id.nombre, r.nombre " +
            "order by count (f.id) desc "
    )
    Optional<UsuarioPopularDTO> findTop1UsuarioPopular();


    @Query(value = "SELECT r.* FROM smartchef.receta r " +
            "LEFT JOIN smartchef.receta_preferencia rp ON r.id = rp.id_receta " +
            "JOIN smartchef.receta_ingrediente ri ON r.id = ri.id_receta " +
            "WHERE " +
            // 1. Preferencias: Filtra si la lista NO está vacía.
            "(:idPreferencias IS NULL OR rp.id_preferencia IN (:idPreferencias)) " +
            "AND " +
            // 2. Ingredientes: Filtro base. Si la lista es NULL/EMPTY, se ignorará por el HAVING.
            "(:idIngredientes IS NULL OR ri.id_ingrediente IN (:idIngredientes)) " +
            "GROUP BY r.id " +
            "HAVING " +
            // 3. HAVING: Asegura que se encontraron TODOS los ingredientes (si la lista no es NULL/EMPTY)
            "(:idIngredientes IS NULL OR array_length(cast(:idIngredientes as int[]), 1) = 0 OR COUNT(DISTINCT ri.id_ingrediente) = array_length(cast(:idIngredientes as int[]), 1))",
            nativeQuery = true)
    List<Receta> buscarRecetasPorPreferenciasEIngredientes(
            @Param("idPreferencias") List<Integer> idPreferencias,
            @Param("idIngredientes") List<Integer> idIngredientes
    );

    @Modifying
    @Query(value = "delete from receta_ingrediente ri where ri.id_receta = :idReceta;" +
                    "delete from receta_preferencia rp where rp.id_receta = :idReceta;" +
                    "delete from favorito f where f.id_receta = :idReceta",
            nativeQuery = true)
    void eliminarRelacionesReceta(@Param("idReceta") Integer idReceta);
}