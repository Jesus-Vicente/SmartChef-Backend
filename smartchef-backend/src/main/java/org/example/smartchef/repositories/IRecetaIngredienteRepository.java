package org.example.smartchef.repositories;

import org.example.smartchef.models.RecetaIngrediente;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRecetaIngredienteRepository extends JpaRepository<RecetaIngrediente, Integer> {

    @Query("SELECT ri FROM RecetaIngrediente ri WHERE ri.id_receta.id = :recetaId")
    List<RecetaIngrediente> findByRecetaId(Integer recetaId);

}
