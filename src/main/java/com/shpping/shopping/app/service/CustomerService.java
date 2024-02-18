package com.shpping.shopping.app.service;

import com.shpping.shopping.app.dto.CartDTO;
import com.shpping.shopping.app.dto.CustomerDTO;
import com.shpping.shopping.app.entity.Cart;
import com.shpping.shopping.app.entity.Customer;
import com.shpping.shopping.app.mapper.CustomerMapper;
import com.shpping.shopping.app.repository.CartRepository;
import com.shpping.shopping.app.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CustomerService {

    private final Logger log = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;
    private final CartService cartService;
    private final CartRepository cartRepository;
    private final CustomerMapper customerMapper;

    public CustomerDTO addCustomer(CustomerDTO customerDTO){
        if (customerDTO.getId() != null){
            log.error("customer have id");
            return null;

        }else {
            Customer customer = customerMapper.toEntity(customerDTO);
            customer = customerRepository.save(customer);

            if (customer.getCart() == null) {
                Cart cart = new Cart();
                cart.setCustomer(customer);
                cart.setTotalAmount(0.0);
                cartRepository.save(cart);
                customer.setCart(cart);
            }

            return customerMapper.toDto(customerRepository.save(customer));
        }
    }

    public Optional<Customer> isExist(Long id){
        Optional<Customer> existCustomer = customerRepository.findById(id);
        return existCustomer;
    }

}
