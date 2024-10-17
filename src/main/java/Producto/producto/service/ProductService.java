package Producto.producto.service;

import Producto.producto.model.Product;

public interface ProductService {
	
	Product createProduct(Product createProduct);
	Iterable<Product> listAllProducts();

	Product retrieveProductById(Long id);
}
