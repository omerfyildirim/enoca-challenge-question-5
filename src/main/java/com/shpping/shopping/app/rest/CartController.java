package com.shpping.shopping.app.rest;

import com.shpping.shopping.app.dto.CartDTO;
import com.shpping.shopping.app.dto.ProductDTO;
import com.shpping.shopping.app.entity.Cart;
import com.shpping.shopping.app.entity.Product;
import com.shpping.shopping.app.service.CartService;
import com.shpping.shopping.app.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    @GetMapping("get/{id}")
    public ResponseEntity<CartDTO> getCart(@PathVariable Long id){
        return ResponseEntity.ok().body(cartService.getCart(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CartDTO> updateCart(@PathVariable Long id, @RequestBody CartDTO cartDTO){
        Optional<Cart> existCart = cartService.isExist(id);
        if (!existCart.isPresent()){
            return ResponseEntity.notFound().build();
        }else {
            CartDTO updateCart = cartService.updateCart(id, cartDTO);
            return ResponseEntity.ok().body(updateCart);
        }
    }

    @PostMapping("/empty-cart/{id}")
    public void emptyCart(@PathVariable Long id){
        cartService.emptyCart(id);
    }

    @PostMapping("/add-to-cart/{id}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long id, @RequestBody ProductDTO productDTO){
        Optional<Cart> existCart = cartService.isExist(id);
        if (!existCart.isPresent()){
            return ResponseEntity.notFound().build();
        }else {
            CartDTO cartDTO = cartService.addProductToCart(id, productDTO);
            return ResponseEntity.ok().body(cartDTO);
        }

    }

    @PostMapping("/remove-from-cart")
    public ResponseEntity<CartDTO> removeProductFromCart(@RequestParam Long cartId, @RequestParam Long productId){
        Optional<Cart> existCart = cartService.isExist(cartId);
        Optional<Product> existProduct = productService.isExist(productId);

        if (!existCart.isPresent()){
            return ResponseEntity.notFound().build();
        } else if (!existProduct.isPresent()) {
            return ResponseEntity.notFound().build();
        }else {
            CartDTO cartDTO = cartService.removeProductFromCart(cartId, productId);
            return ResponseEntity.ok().body(cartDTO);
        }
    }

}
