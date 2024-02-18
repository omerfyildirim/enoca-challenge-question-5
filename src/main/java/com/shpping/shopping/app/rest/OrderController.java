package com.shpping.shopping.app.rest;

import com.shpping.shopping.app.dto.CartDTO;
import com.shpping.shopping.app.dto.OrderDTO;
import com.shpping.shopping.app.entity.Customer;
import com.shpping.shopping.app.entity.Order;
import com.shpping.shopping.app.mapper.OrderMapper;
import com.shpping.shopping.app.service.CustomerService;
import com.shpping.shopping.app.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;

    private final OrderMapper orderMapper;

    @PostMapping("/place/{customerId}")
    public ResponseEntity<OrderDTO> placeOrder(@PathVariable Long customerId, @RequestBody CartDTO cartDTO) throws URISyntaxException {
        Optional<Customer> existCustomer = customerService.isExist(customerId);
        if (!existCustomer.isPresent()){
            return ResponseEntity.notFound().build();
        }else {
            OrderDTO orderDTO = orderService.placeOrder(customerId, cartDTO);
            return ResponseEntity.created(new URI("/order/place/" + orderDTO.getId()))
                    .body(orderDTO);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<OrderDTO> getOrderForCode(@RequestParam String code){
        Optional<Order> existOrder = orderService.isOrderExistWithCode(code);
        if (existOrder.isPresent()){
            return ResponseEntity.ok().body(orderMapper.toDto(existOrder.get()));
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<OrderDTO>> getAllOrdersForCustomer(@RequestParam Long customerId){
        Optional<Customer> existCustomer = customerService.isExist(customerId);
        if (!existCustomer.isPresent()){
            return ResponseEntity.notFound().build();
        }else {
            List<OrderDTO> orderDTOList = orderService.getAllOrdersForCustomer(customerId);

            return ResponseEntity.ok().body(orderDTOList);
        }
    }
}
