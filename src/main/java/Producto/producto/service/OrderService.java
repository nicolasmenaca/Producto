package Producto.producto.service;


import java.util.Optional;

import Producto.producto.DTO.RequestOrderDTO;
import Producto.producto.DTO.ResponseOrderDTO;
import Producto.producto.model.Order;

public interface OrderService {
    ResponseOrderDTO createOrder(RequestOrderDTO order);
    Iterable<Order> listAllOrders();
    Optional<Order> findOrderById(Long id);
}
