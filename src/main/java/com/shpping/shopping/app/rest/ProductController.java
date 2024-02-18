package com.shpping.shopping.app.rest;

import com.shpping.shopping.app.dto.ProductDTO;
import com.shpping.shopping.app.entity.Product;
import com.shpping.shopping.app.mapper.ProductMapper;
import com.shpping.shopping.app.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id){
        Optional<Product> existProduct = productService.isExist(id);
        if (existProduct.isPresent()){
            return ResponseEntity.ok().body(productMapper.toDto(existProduct.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) throws URISyntaxException {
        if (productDTO.getId() != null){
            return ResponseEntity.badRequest().build();
        }else {
            ProductDTO createdProduct = productService.createProduct(productDTO);
            return ResponseEntity.created(new URI("/product/create/" + createdProduct.getId()))
                    .body(createdProduct);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO){
        Optional<Product> existProduct = productService.isExist(id);
        if (!existProduct.isPresent()){
            return ResponseEntity.notFound().build();
        } else {
            ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
            return ResponseEntity.ok().body(updatedProduct);
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable Long id){
        Optional<Product> existProduct = productService.isExist(id);
        if (existProduct.isPresent()){
            productService.deleteProduct(id);
        }
    }
}
