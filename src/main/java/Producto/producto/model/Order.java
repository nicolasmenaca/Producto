package Producto.producto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "customer_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long customerId; // ID del cliente que realizó la orden

    @Column(nullable = false)
    private Date orderDate; // Fecha de la orden

    @Column(nullable = false)
    private Double totalAmount; // Monto total de la orden

    @JoinColumn()
    @OneToMany
    private List<Product> product;
    // Otros campos que consideres necesarios, como estado de la orden, dirección de envío, etc.
}



