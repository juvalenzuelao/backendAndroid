package com.huertohogar.backendApi.product;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")  // Permitir solicitudes desde la app
public class ProductController {
    @Autowired
    private ProductService productService;
    
    //  Obtener todos los productos
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
    
    // GET /api/products/{id} - Obtener un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // POST /api/products - Crear un nuevo producto
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product newProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }
    
    // PUT /api/products/{id} - Actualizar un producto existente
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id, 
            @RequestBody Product productDetails) {
        try {
            Product updatedProduct = productService.updateProduct(id, productDetails);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // DELETE /api/products/{id} - Eliminar un producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // GET Buscar productos por nombre
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String nombre) {
        List<Product> products = productService.searchProductsByName(nombre);
        return ResponseEntity.ok(products);
    }
    
    // GET Obtener productos por categoría
    @GetMapping("/category/{categoria}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String categoria) {
        List<Product> products = productService.getProductsByCategory(categoria);
        return ResponseEntity.ok(products);
    }
    
    // GET  Obtener solo productos con cantidadKg disponible
    @GetMapping("/disp")
    public ResponseEntity<List<Product>> getDispProducts() {
        List<Product> products = productService.getDispProducts();
        return ResponseEntity.ok(products);
    }
    
    // PATCH Actualizar solo el cantidadKg
    @PatchMapping("/{id}/cantidadKg")
    public ResponseEntity<Product> updatecantidadKg(
            @PathVariable Long id, 
            @RequestParam Integer cantidadKg) {
        try {
            Product updatedProduct = productService.updateKg(id, cantidadKg);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
