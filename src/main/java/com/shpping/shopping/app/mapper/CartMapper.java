package com.shpping.shopping.app.mapper;

import com.shpping.shopping.app.dto.CartDTO;
import com.shpping.shopping.app.dto.OrderDTO;
import com.shpping.shopping.app.entity.Cart;
import com.shpping.shopping.app.entity.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class CartMapper {

    private final CustomerMapper customerMapper;

    public CartDTO toDto(Cart cart){
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setCustomer(customerMapper.toDto(cart.getCustomer()));
        cartDTO.setItems(cart.getItems());
        cartDTO.setTotalAmount(cart.getTotalAmount());
        return cartDTO;
    }

    public Cart toEntity(CartDTO cartDTO){
        Cart cart = new Cart();
        cart.setId(cartDTO.getId());
        cart.setCustomer(customerMapper.toEntity(cartDTO.getCustomer()));
        cart.setItems(cartDTO.getItems());
        cart.setTotalAmount(cartDTO.getTotalAmount());
        return cart;
    }

    public List<CartDTO> toDto(List<Cart> cartList){
        List<CartDTO> cartDTOList = new ArrayList<>();
        for (Cart cart : cartList){
            CartDTO cartDTO = toDto(cart);
            cartDTOList.add(cartDTO);
        }
        return cartDTOList;
    }
}
