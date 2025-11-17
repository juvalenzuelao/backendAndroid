package com.huertohogar.backendApi.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    // Buscar productos por nombre (contiene)
    List<Product> findByNombreContainingIgnoreCase(String nombre);
    
    // Buscar productos por categoría
    List<Product> findByCategoria(String categoria);
    
    // Buscar productos con stock disponible
    List<Product> findByStockGreaterThan(Integer stock);
    
    // Buscar por categoría y que tenga stock
    List<Product> findByCategoriaAndStockGreaterThan(String categoria, Integer stock);
}
