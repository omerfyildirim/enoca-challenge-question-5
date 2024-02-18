package com.shpping.shopping.app.dto;

import com.shpping.shopping.app.entity.Cart;
import lombok.Data;

@Data
public class ProductDTO extends BaseDTO{

    private String description;

    private Double price;

    private String name;

    private Cart cart;
}
