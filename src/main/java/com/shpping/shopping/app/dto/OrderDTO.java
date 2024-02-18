package com.shpping.shopping.app.dto;

import com.shpping.shopping.app.entity.Cart;
import com.shpping.shopping.app.entity.Customer;
import lombok.Data;

@Data
public class OrderDTO extends BaseDTO{

    private String code;

    private Cart cart;

    private Customer customer;
}
