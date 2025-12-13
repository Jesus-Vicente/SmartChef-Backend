package org.example.smartchef.repositories;

import org.example.smartchef.models.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface IIngredientesRepository extends JpaRepository<Ingrediente, Integer> {

    List<Ingrediente> findByNombreIn(Collection<String> nombresIngredientes);

}
