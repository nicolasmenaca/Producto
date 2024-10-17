package Producto.producto.DTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

    private String name;
    private Double cost;
    private String details;
    private String imageUrl;
    private Integer stock;
}
