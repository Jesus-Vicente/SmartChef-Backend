package org.example.smartchef.repositories;

import org.example.smartchef.models.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRecetaRepository extends JpaRepository<Receta, Integer> {

    /*@Query(value = "", nativeQuery = true)*/

}
