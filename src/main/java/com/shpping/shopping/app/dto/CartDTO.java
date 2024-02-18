package com.shpping.shopping.app.dto;

import com.shpping.shopping.app.entity.Product;
import lombok.Data;

import java.util.List;

@Data
public class CartDTO extends BaseDTO{
    private CustomerDTO customer;
    private List<Product> items;
    private Double totalAmount;
}
