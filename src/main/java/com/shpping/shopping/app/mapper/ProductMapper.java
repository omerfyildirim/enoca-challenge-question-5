package com.shpping.shopping.app.mapper;

import com.shpping.shopping.app.dto.ProductDTO;
import com.shpping.shopping.app.entity.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapper {

    public ProductDTO toDto(Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());

        return productDTO;
    }

    public Product toEntity(ProductDTO productDTO){
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());

        return product;
    }

    public List<ProductDTO> toDto(List<Product> productList){
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product product : productList){
            ProductDTO productDTO = toDto(product);
            productDTOList.add(productDTO);
        }

        return productDTOList;
    }

    public List<Product> toEntity(List<ProductDTO> productDTOList){
        List<Product> productList = new ArrayList<>();
        for (ProductDTO productDTO : productDTOList){
            Product product = toEntity(productDTO);
            productList.add(product);
        }

        return productList;
    }
}
