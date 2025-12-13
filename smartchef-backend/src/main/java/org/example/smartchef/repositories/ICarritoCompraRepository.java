package org.example.smartchef.repositories;

import org.example.smartchef.models.CarritoCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICarritoCompraRepository extends JpaRepository<CarritoCompra, Integer> {
}
