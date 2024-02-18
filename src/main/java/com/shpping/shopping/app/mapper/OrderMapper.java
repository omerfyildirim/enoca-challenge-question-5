package com.shpping.shopping.app.mapper;

import com.shpping.shopping.app.dto.OrderDTO;
import com.shpping.shopping.app.entity.Order;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class OrderMapper {

    public OrderDTO toDto(Order order){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setCart(order.getCart());
        orderDTO.setCode(order.getCode());
        orderDTO.setCustomer(order.getCustomer());

        return orderDTO;
    }

    public Order toEntity(OrderDTO orderDTO){
        Order order = new Order();
        order.setId(orderDTO.getId());
        order.setCart(orderDTO.getCart());
        order.setCode(orderDTO.getCode());
        order.setCustomer(orderDTO.getCustomer());

        return order;
    }

    public List<OrderDTO> toDto(List<Order> orderList){
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (Order order : orderList){
            OrderDTO orderDTO = toDto(order);
            orderDTOList.add(orderDTO);
        }
        return orderDTOList;
    }

    public List<Order> toEntity(List<OrderDTO> orderDTOList){
        List<Order> orderList = new ArrayList<>();
        for (OrderDTO orderDTO : orderDTOList){
            Order order = toEntity(orderDTO);
            orderList.add(order);
        }
        return orderList;
    }
}
