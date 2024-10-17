package Producto.producto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Producto.producto.DTO.ProductDto;
import Producto.producto.model.Product;
import Producto.producto.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200")
public class productController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Listar todos los productos")
    @GetMapping("/list")
    public ResponseEntity<Iterable<Product>> listAllProducts() {
        return ResponseEntity.ok(productService.listAllProducts());
    }

    @Operation(summary = "Crear un nuevo producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setCost(productDto.getCost());
        product.setDetails(productDto.getDetails());
        product.setImageUrl(productDto.getImageUrl());
        product.setStock(productDto.getStock());

        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.ok(createdProduct);
    }

    @Operation(summary = "Obtener un producto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> retrieveProductById(@PathVariable Long id) {
        Product product = productService.retrieveProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
