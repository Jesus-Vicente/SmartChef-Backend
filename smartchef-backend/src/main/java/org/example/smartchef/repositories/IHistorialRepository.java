package org.example.smartchef.repositories;

import org.example.smartchef.models.Historial;
import org.hibernate.dialect.function.array.H2ArrayContainsFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IHistorialRepository extends JpaRepository<Historial, Integer> {

    @Query("SELECT h FROM Historial h WHERE h.idUsuario.id = :idUsuario")
    List<Historial> findByUsuarioId(@Param("idUsuario") Integer idUsuario);

    @Query("SELECT h FROM Historial h WHERE h.idUsuario.id = :idUsuario AND h.fecha_realizacion >= :fechaInicio AND h.fecha_realizacion <= :fechaFin ORDER BY h.fecha_realizacion DESC")
    List<Historial> findHistorialSemanal(
            @Param("idUsuario") Integer idUsuario,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin
    );
}
