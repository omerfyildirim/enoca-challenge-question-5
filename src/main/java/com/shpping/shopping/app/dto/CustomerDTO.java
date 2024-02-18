package com.shpping.shopping.app.dto;

import com.shpping.shopping.app.entity.BaseEntity;
import lombok.Data;

@Data
public class CustomerDTO extends BaseEntity {

    private String phone;

    private String name;

    private String email;
}
