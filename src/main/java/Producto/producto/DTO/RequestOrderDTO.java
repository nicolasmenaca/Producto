package Producto.producto.DTO;

import lombok.Data;

import java.util.List;

@Data
public class RequestOrderDTO {
    private String orderDate;
    private Long idCustomer;
    private List<ProductOrderDTO> listProducts;
}
