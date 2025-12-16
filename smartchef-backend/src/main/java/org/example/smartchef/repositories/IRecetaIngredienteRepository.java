package org.example.smartchef.repositories;

import org.example.smartchef.models.RecetaIngrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRecetaIngredienteRepository extends JpaRepository<RecetaIngrediente, Integer> {

    // Obtener ingredientes de una receta
    @Query("SELECT ri FROM RecetaIngrediente ri WHERE ri.id_receta.id = :recetaId")
    List<RecetaIngrediente> findByRecetaId(@Param("recetaId") Integer recetaId);

    // Eliminar TODOS los ingredientes asociados a una receta (clave para editar)
    @Modifying
    @Query("DELETE FROM RecetaIngrediente ri WHERE ri.id_receta.id = :recetaId")
    void eliminarIngredientesPorReceta(@Param("recetaId") Integer recetaId);
}
