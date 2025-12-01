package com.huertohogar.backendApi;

import com.huertohogar.backendApi.product.Product;
import com.huertohogar.backendApi.product.ProductRepository;
import com.huertohogar.backendApi.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    
    @Mock
    private ProductRepository productRepository;
    
    @InjectMocks
    private ProductService productService;
    
    private Product product1;
    private Product product2;
    
    @BeforeEach
    public void setUp() {
        product1 = new Product();
        product1.setId(1L);
        product1.setNombre("Tomate");
        product1.setDescripcion("Tomate fresco");
        product1.setPrecio(2.50);
        product1.setCantidadKg(10);
        product1.setCategoria("Verdura");
        
        product2 = new Product();
        product2.setId(2L);
        product2.setNombre("Lechuga");
        product2.setDescripcion("Lechuga fresca");
        product2.setPrecio(1.50);
        product2.setCantidadKg(5);
        product2.setCategoria("Verdura");
    }
    
    @Test
    public void testGetAllProducts() {
        List<Product> products = Arrays.asList(product1, product2);
        when(productRepository.findAll()).thenReturn(products);
        
        List<Product> result = productService.getAllProducts();
        
        assertEquals(2, result.size());
        assertEquals("Tomate", result.get(0).getNombre());
        verify(productRepository, times(1)).findAll();
    }
    
    @Test
    public void testGetProductById() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        
        Optional<Product> result = productService.getProductById(1L);
        
        assertTrue(result.isPresent());
        assertEquals("Tomate", result.get().getNombre());
        verify(productRepository, times(1)).findById(1L);
    }
    
    @Test
    public void testGetProductById_NotFound() {
        when(productRepository.findById(999L)).thenReturn(Optional.empty());
        
        Optional<Product> result = productService.getProductById(999L);
        
        assertFalse(result.isPresent());
        verify(productRepository, times(1)).findById(999L);
    }
    
    @Test
    public void testCreateProduct() {
        when(productRepository.save(product1)).thenReturn(product1);
        
        Product result = productService.createProduct(product1);
        
        assertNotNull(result);
        assertEquals("Tomate", result.getNombre());
        verify(productRepository, times(1)).save(product1);
    }
    
    @Test
    public void testUpdateProduct() {
        Product updatedDetails = new Product();
        updatedDetails.setNombre("Tomate Premium");
        updatedDetails.setDescripcion("Tomate premium");
        updatedDetails.setPrecio(3.50);
        updatedDetails.setCantidadKg(15);
        updatedDetails.setCategoria("Verdura Premium");
        updatedDetails.setImagenUrl("url");
        
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(productRepository.save(any(Product.class))).thenReturn(product1);
        
        Product result = productService.updateProduct(1L, updatedDetails);
        
        assertEquals("Tomate Premium", result.getNombre());
        assertEquals(3.50, result.getPrecio());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(any(Product.class));
    }
    
    @Test
    public void testUpdateProduct_NotFound() {
        when(productRepository.findById(999L)).thenReturn(Optional.empty());
        
        assertThrows(RuntimeException.class, () -> {
            productService.updateProduct(999L, product1);
        });
        
        verify(productRepository, times(1)).findById(999L);
        verify(productRepository, never()).save(any());
    }
    
    @Test
    public void testDeleteProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        
        productService.deleteProduct(1L);
        
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).delete(product1);
    }
    
    @Test
    public void testDeleteProduct_NotFound() {
        when(productRepository.findById(999L)).thenReturn(Optional.empty());
        
        assertThrows(RuntimeException.class, () -> {
            productService.deleteProduct(999L);
        });
        
        verify(productRepository, never()).delete(any());
    }
    
    @Test
    public void testSearchProductsByName() {
        List<Product> products = Arrays.asList(product1);
        when(productRepository.findByNombreContainingIgnoreCase("Tomate"))
            .thenReturn(products);
        
        List<Product> result = productService.searchProductsByName("Tomate");
        
        assertEquals(1, result.size());
        assertEquals("Tomate", result.get(0).getNombre());
        verify(productRepository, times(1)).findByNombreContainingIgnoreCase("Tomate");
    }
    
    @Test
    public void testGetProductsByCategory() {
        List<Product> products = Arrays.asList(product1, product2);
        when(productRepository.findByCategoria("Verdura")).thenReturn(products);
        
        List<Product> result = productService.getProductsByCategory("Verdura");
        
        assertEquals(2, result.size());
        verify(productRepository, times(1)).findByCategoria("Verdura");
    }
    
    @Test
    public void testGetDispProducts() {
        List<Product> products = Arrays.asList(product1, product2);
        when(productRepository.findByCantidadKgGreaterThan(0)).thenReturn(products);
        
        List<Product> result = productService.getDispProducts();
        
        assertEquals(2, result.size());
        verify(productRepository, times(1)).findByCantidadKgGreaterThan(0);
    }
    
    @Test
    public void testUpdateKg() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(productRepository.save(any(Product.class))).thenReturn(product1);
        
        Product result = productService.updateKg(1L, 20);
        
        assertEquals(20, result.getCantidadKg());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(any(Product.class));
    }
}