package Producto.producto;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import Producto.producto.DTO.ProductOrderDTO;
import Producto.producto.DTO.RequestOrderDTO;
import Producto.producto.DTO.ResponseOrderDTO;
import Producto.producto.model.Order;
import Producto.producto.model.Product;
import Producto.producto.repository.OrderRepository;
import Producto.producto.repository.ProductRepository;
import Producto.producto.service.Impl.OrderServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateOrder_Success() {
        ProductOrderDTO productOrderDTO = new ProductOrderDTO();
        productOrderDTO.setId(1L);
        productOrderDTO.setQuantityAmount(2);

        RequestOrderDTO requestOrderDTO = new RequestOrderDTO();
        requestOrderDTO.setIdCustomer(1L);
        requestOrderDTO.setListProducts(List.of(productOrderDTO));

        Product product = new Product();
        product.setId(1L);
        product.setStock(10);
        product.setCost(100.0);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Order savedOrder = new Order();
        savedOrder.setId(1L);
        savedOrder.setOrderDate(new Date());
        savedOrder.setTotalAmount(200.0);

        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        ResponseOrderDTO response = orderService.createOrder(requestOrderDTO);

        assertNotNull(response);
        assertEquals("Se genero la orden de venta correctamente", response.getMessage());
        assertEquals("1", response.getOrderNumber());
    }

    @Test
    public void testCreateOrder_ProductNotInStock() {

        ProductOrderDTO productOrderDTO = new ProductOrderDTO();
        productOrderDTO.setId(1L);
        productOrderDTO.setQuantityAmount(15);
        RequestOrderDTO requestOrderDTO = new RequestOrderDTO();
        requestOrderDTO.setIdCustomer(1L);
        requestOrderDTO.setListProducts(List.of(productOrderDTO));

        Product product = new Product();
        product.setId(1L);
        product.setStock(5);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ResponseOrderDTO response = orderService.createOrder(requestOrderDTO);

        assertNotNull(response);
        assertEquals("El producto no existe o no tiene esta cantidad en el stock", response.getMessage());
        assertEquals("0", response.getOrderNumber());
    }
}