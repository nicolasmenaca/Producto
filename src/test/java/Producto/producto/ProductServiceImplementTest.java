package Producto.producto;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import Producto.producto.model.Product;
import Producto.producto.repository.ProductRepository;
import Producto.producto.service.Impl.ProductServiceImplement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;

public class ProductServiceImplementTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImplement productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProduct_Success() {
        Product product = new Product();
        product.setName("Laptop");
        product.setCost(1200.0);
        product.setDetails("Laptop con 16GB RAM y 512GB SSD");
        product.setStock(10);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product createdProduct = productService.createProduct(product);

        assertNotNull(createdProduct);
        assertEquals("Laptop", createdProduct.getName());
        assertEquals(1200.0, createdProduct.getCost());
        assertEquals("Laptop con 16GB RAM y 512GB SSD", createdProduct.getDetails());
        assertEquals(10, createdProduct.getStock());
    }

    @Test
    public void testCreateProduct_Failure() {
        Product product = new Product();
        product.setName("Laptop");

        when(productRepository.save(any(Product.class)))
                .thenThrow(new RuntimeException("Error al guardar el nuevo producto en la base de datos"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.createProduct(product);
        });

        assertEquals("Error al guardar el nuevo producto en la base de datos", exception.getMessage());
    }

    @Test
    public void testRetrieveProductById_Success() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Smartphone");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product foundProduct = productService.retrieveProductById(1L);

        assertNotNull(foundProduct);
        assertEquals("Smartphone", foundProduct.getName());
    }

    @Test
    public void testRetrieveProductById_NotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Product foundProduct = productService.retrieveProductById(1L);

        assertNull(foundProduct);
    }

    @Test
    public void testListAllProducts() {
        Product product1 = new Product();
        product1.setName("Laptop");

        Product product2 = new Product();
        product2.setName("Smartphone");

        when(productRepository.findAll()).thenReturn(List.of(product1, product2));

        Iterable<Product> products = productService.listAllProducts();

        assertNotNull(products);
        List<Product> productList = (List<Product>) products;
        assertEquals(2, productList.size());
        assertEquals("Laptop", productList.get(0).getName());
        assertEquals("Smartphone", productList.get(1).getName());
    }
}