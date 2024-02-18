package com.shpping.shopping.app.rest;

import com.shpping.shopping.app.dto.CustomerDTO;
import com.shpping.shopping.app.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@AllArgsConstructor
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/add")
    public ResponseEntity<CustomerDTO> addCustomer(@RequestBody CustomerDTO customerDTO) throws URISyntaxException {
        if (customerDTO.getId() != null){
            return ResponseEntity.badRequest().build();
        }else {
            CustomerDTO createdCustomer = customerService.addCustomer(customerDTO);
            return ResponseEntity.created(new URI("/product/create/" + createdCustomer.getId()))
                    .body(createdCustomer);
        }
    }
}
