package Producto.producto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Producto.producto.DTO.RequestOrderDTO;
import Producto.producto.DTO.ResponseOrderDTO;
import Producto.producto.model.Order;
import Producto.producto.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "http://localhost:4200")
public class orderController {

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Crear un nuevo pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping("/neworder")
    public ResponseOrderDTO createOrder(@RequestBody RequestOrderDTO orderDto) {
        return orderService.createOrder(orderDto);
    }

    @Operation(summary = "Listar todos los pedidos")
    @GetMapping("/list")
    public ResponseEntity<Iterable<Order>> listOrders() {
        return ResponseEntity.ok(orderService.listAllOrders());
    }

    @Operation(summary = "Obtener un pedido por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderService.findOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
