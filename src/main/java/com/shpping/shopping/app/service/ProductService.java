package com.shpping.shopping.app.service;

import com.shpping.shopping.app.dto.ProductDTO;
import com.shpping.shopping.app.entity.Product;
import com.shpping.shopping.app.mapper.ProductMapper;
import com.shpping.shopping.app.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public ProductDTO getProduct(Long id){
        log.info("get product for id: {}", id);
        Optional<Product> productOptional = isExist(id);
        if (productOptional.isPresent()){
            return productMapper.toDto(productOptional.get());
        }else {
            return null;
        }

    }

    public ProductDTO createProduct(ProductDTO productDTO){
        log.info("save product");
        if (productDTO.getId() != null){
            log.error("new product have id");
            return null;
        }

        Product product = productMapper.toEntity(productDTO);
        product = productRepository.save(product);

        return productMapper.toDto(product);
    }

    @Transactional
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO){
        log.info("update product for id: {}", productId);
        Optional<Product> existProduct = isExist(productId);
        if (!existProduct.isPresent()){
            log.error("product not exist");
            return null;
        }

        existProduct.get().setCart(productDTO.getCart());
        existProduct.get().setName(productDTO.getName());
        existProduct.get().setPrice(productDTO.getPrice());
        existProduct.get().setDescription(productDTO.getDescription());

        return productMapper.toDto(productRepository.save(existProduct.get()));
    }

    public void deleteProduct(Long id){
        log.info("delete product for id: {}",id);
        Optional<Product> existProduct = isExist(id);
        if (!existProduct.isPresent()){
            log.error("product not exist");
        }else {
            productRepository.deleteById(id);
        }
    }

    public Optional<Product> isExist(Long id){
        Optional<Product> existProduct = productRepository.findById(id);
        return existProduct;
    }
}
