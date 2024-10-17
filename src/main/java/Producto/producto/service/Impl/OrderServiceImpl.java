package Producto.producto.service.Impl;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Producto.producto.DTO.ProductOrderDTO;
import Producto.producto.DTO.RequestOrderDTO;
import Producto.producto.DTO.ResponseOrderDTO;
import Producto.producto.model.Order;
import Producto.producto.model.Product;
import Producto.producto.repository.OrderRepository;
import Producto.producto.repository.ProductRepository;
import Producto.producto.service.OrderService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;



    @Override
    @Transactional
    public ResponseOrderDTO createOrder(RequestOrderDTO order)  {
        List<Product> products = new ArrayList<>();
        Order oneOrder = new Order();
        Double totalVenta = 0.0;
        for(ProductOrderDTO prod: order.getListProducts()) {
            Product oneProduct = productRepository.findById(prod.getId()).orElse(new Product());
            if(oneProduct != null && oneProduct.getStock() >= prod.getQuantityAmount()){
                oneProduct.setStock(oneProduct.getStock()-prod.getQuantityAmount());
                totalVenta += oneProduct.getCost()*prod.getQuantityAmount();
                products.add(oneProduct);
                productRepository.save(oneProduct);
            } else {
                ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO();
                responseOrderDTO.setMessage("El producto no existe o no tiene esta cantidad en el stock");
                responseOrderDTO.setOrderNumber("0");
                return responseOrderDTO;
            }

        }
        oneOrder.setOrderDate(new Date());
        oneOrder.setTotalAmount(totalVenta);
        oneOrder.setProduct(products);
        oneOrder.setCustomerId(order.getIdCustomer());
        oneOrder = orderRepository.save(oneOrder);
        ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO();
        responseOrderDTO.setMessage("Se genero la orden de venta correctamente");
        responseOrderDTO.setOrderNumber(oneOrder.getId().toString());
        return responseOrderDTO;

    }

    @Override
    public Iterable<Order> listAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> findOrderById(Long id) {
        return orderRepository.findById(id);
    }
}
