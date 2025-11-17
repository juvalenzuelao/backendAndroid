package com.huertohogar.backendApi.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    // Obtener todos los productos
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    // Obtener producto por ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    
    // Crear nuevo producto
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
    
    // Actualizar producto existente
    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
        
        product.setNombre(productDetails.getNombre());
        product.setDescripcion(productDetails.getDescripcion());
        product.setPrecio(productDetails.getPrecio());
        product.setStock(productDetails.getStock());
        product.setCategoria(productDetails.getCategoria());
        product.setImagenUrl(productDetails.getImagenUrl());
        
        return productRepository.save(product);
    }
    
    // Eliminar producto
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
        productRepository.delete(product);
    }
    
    // Buscar productos por nombre
    public List<Product> searchProductsByName(String nombre) {
        return productRepository.findByNombreContainingIgnoreCase(nombre);
    }
    
    // Obtener productos por categoría
    public List<Product> getProductsByCategory(String categoria) {
        return productRepository.findByCategoria(categoria);
    }
    
    // Obtener productos disponibles (con stock > 0)
    public List<Product> getDispProducts() {
        return productRepository.findByStockGreaterThan(0);
    }
    
    // Actualizar solo el stock (útil para cuando se compra)
    public Product updateStock(Long id, Integer newStock) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
        product.setStock(newStock);
        return productRepository.save(product);
    }
}
