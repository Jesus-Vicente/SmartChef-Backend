package org.example.smartchef.repositories;

import org.example.smartchef.models.CarritoCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ICarritoCompraRepository extends JpaRepository<CarritoCompra, Integer> {

    @Query("SELECT COUNT(c) > 0 FROM CarritoCompra c WHERE c.id_usuario.id = :idUsuario AND c.id_receta.id = :idReceta")
    boolean existeCarritoParaUsuarioYReceta(@Param("idUsuario") Integer idUsuario, @Param("idReceta") Integer idReceta);
}
