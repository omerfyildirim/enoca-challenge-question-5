package com.shpping.shopping.app.service;

import com.shpping.shopping.app.dto.CartDTO;
import com.shpping.shopping.app.dto.CustomerDTO;
import com.shpping.shopping.app.dto.ProductDTO;
import com.shpping.shopping.app.entity.Cart;
import com.shpping.shopping.app.entity.Customer;
import com.shpping.shopping.app.entity.Product;
import com.shpping.shopping.app.mapper.CartMapper;
import com.shpping.shopping.app.mapper.CustomerMapper;
import com.shpping.shopping.app.mapper.ProductMapper;
import com.shpping.shopping.app.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CartService {

    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final CustomerMapper customerMapper;

    private final ProductMapper productMapper;

    private final ProductService productService;


    public CartDTO getCart(Long id){
        log.info("get cart for id: {}", id);
        Optional<Cart> existCart = isExist(id);
        if (!existCart.isPresent()){
            log.error("card not exist");
            return null;
        }else {
            return cartMapper.toDto(cartRepository.findById(id).get());
        }
    }

    @Transactional
    public CartDTO updateCart(Long cartId, CartDTO cartDTO){
        log.info("update cart for id: {}", cartId);
        Optional<Cart> existCart = isExist(cartId);
        if (!existCart.isPresent()){
            log.error("cart not exist");
            return null;
        }

        existCart.get().setCustomer(customerMapper.toEntity(cartDTO.getCustomer()));
        existCart.get().setItems(cartDTO.getItems());
        existCart.get().setTotalAmount(cartDTO.getTotalAmount());

        return cartMapper.toDto(cartRepository.save(existCart.get()));
    }

    public Cart createCart(Customer customer){
        log.info("create cart");
        Cart cart = new Cart();
        cart.setCustomer(customer);
        cart = cartRepository.save(cart);
        return cart;
    }

    public void emptyCart(Long id){
        log.info("empty cart for id: {}", id);
        Optional<Cart> existCart = isExist(id);
        if (!existCart.isPresent()) {
            log.error("cart not exist");
        }else {
            existCart.get().setItems(new ArrayList<>());
            existCart.get().setTotalAmount(0.0);
            updateCart(existCart.get().getId(), cartMapper.toDto(existCart.get()));
        }
    }

    public CartDTO addProductToCart(Long cartId, ProductDTO productDTO){
        log.info("add product for cart {}",cartId);
        Optional<Cart> existCart = isExist(cartId);
        if (!existCart.isPresent()) {
            log.error("cart not exist");
            return null;
        }else {
            List<ProductDTO> productDTOList = productMapper.toDto(existCart.get().getItems());
            productDTOList.add(productDTO);
            existCart.get().setItems(productMapper.toEntity(productDTOList));
            existCart.get().setTotalAmount(existCart.get().getTotalAmount() + productDTO.getPrice());
            return updateCart(cartId, cartMapper.toDto(existCart.get()));
        }
    }

    public CartDTO removeProductFromCart(Long cartId, Long productId){
        log.info("remove product: {} from cart: {}", productId, cartId);
        Optional<Cart> existCart = isExist(cartId);
        Optional<Product> existProduct = productService.isExist(productId);
        if (!existCart.isPresent()) {
            log.error("cart not exist");
            return null;
        } else if (!existProduct.isPresent()){
            log.error("product not exist");
            return null;
        }else {
            List<ProductDTO> productDTOList = productMapper.toDto(existCart.get().getItems());
            productDTOList.remove(existProduct.get());
            existCart.get().setItems(productMapper.toEntity(productDTOList));

            return updateCart(cartId, cartMapper.toDto(existCart.get()));
        }

    }

    public Optional<Cart> isExist(Long id){
        return cartRepository.findById(id);
    }
}
