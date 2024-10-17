package Producto.producto.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Producto.producto.model.Product;
import Producto.producto.repository.ProductRepository;
import Producto.producto.service.ProductService;

import java.util.Optional;

@Service
public class ProductServiceImplement implements ProductService {
	
	@Autowired
    private ProductRepository productRepository; // Inyección del servicio

	@Override
    public Iterable<Product> listAllProducts() {
        // Lógica para obtener todos los productos
		Iterable<Product> products = this.productRepository.findAll();
	    System.out.println("Productos recuperados del repositorio: " + products);
        return products;
    }

	@Override
	public Product retrieveProductById(Long id) {
		Optional<Product> product = productRepository.findById(id);
		if (product.isPresent()) {
			return product.get(); // Retorna el producto si está presente
		} else {
			return null; // Retorna null si no se encuentra el producto
		}
	}
	@Override
    public Product createProduct(Product product) {

    	try {
    		return productRepository.save(product);
    	} catch (Exception e) {
    		e.printStackTrace(); // Imprime la traza de la excepción en la consola
            throw new RuntimeException("Error al guardar el nuevo producto en la base de datos");
    	}
    }
}
