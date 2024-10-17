package Producto.producto.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OrderDto {
    private Long customerId;
    private LocalDate orderDate;
    private Double totalAmount;
}
