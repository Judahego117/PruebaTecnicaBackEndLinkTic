package com.inventario.repository;

import com.inventario.entity.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    Optional<Inventario> findById(Long id);

    Optional<Inventario> findByProductoId(Long productoId);

    @Query(value = "SELECT * FROM inventario WHERE producto_id = :productoId AND cantidad = :cantidad ", nativeQuery = true)
    Optional<Inventario> findByProductoAndCantidad(Long productoId, Integer cantidad);
}
