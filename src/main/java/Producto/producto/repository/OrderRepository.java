package Producto.producto.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import Producto.producto.model.Order;

@Repository
@EnableJpaRepositories
public interface OrderRepository extends JpaRepository<Order, Long> {
}
