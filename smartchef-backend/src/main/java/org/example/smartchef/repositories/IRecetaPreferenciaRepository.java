package org.example.smartchef.repositories;

import org.example.smartchef.models.RecetaPreferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRecetaPreferenciaRepository extends JpaRepository<RecetaPreferencia, Integer> {

}
