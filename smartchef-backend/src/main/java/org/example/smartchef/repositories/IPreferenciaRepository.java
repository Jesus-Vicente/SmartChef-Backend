package org.example.smartchef.repositories;

import org.example.smartchef.models.Preferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPreferenciaRepository extends JpaRepository<Preferencia, Integer> {

    List<Preferencia> findByNombrePreferenciaIn(List<String> nombresPreferencias);
}
