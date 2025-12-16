package org.example.smartchef.repositories;

import org.example.smartchef.models.Foto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFotoRepository extends JpaRepository<Foto, Integer> {
}
