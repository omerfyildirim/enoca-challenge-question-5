package com.shpping.shopping.app.service;

import com.shpping.shopping.app.dto.CartDTO;
import com.shpping.shopping.app.dto.OrderDTO;
import com.shpping.shopping.app.entity.Customer;
import com.shpping.shopping.app.entity.Order;
import com.shpping.shopping.app.mapper.CartMapper;
import com.shpping.shopping.app.mapper.OrderMapper;
import com.shpping.shopping.app.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class OrderService {
    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final CartMapper cartMapper;

    private final OrderMapper orderMapper;

    public OrderDTO placeOrder(Long customerId, CartDTO cartDTO){
        log.info("initialize order for customer id: {}", customerId);
        Optional<Customer> existCustomer = customerService.isExist(customerId);
        if (!existCustomer.isPresent()){
            log.error("customer not exist");
            return null;
        }else {
            Order order = new Order();
            String orderCode = checkUniqueAndGenerateCode();
            order.setCart(cartMapper.toEntity(cartDTO));
            order.setCustomer(existCustomer.get());
            order.setCode(orderCode);

            return orderMapper.toDto(orderRepository.save(order));

        }

    }

    public OrderDTO getOrderForCode(String code){
        log.info("get order for code: {}", code);
        Optional<Order> existOrder = orderRepository.findByCode(code);
        if (existOrder.isPresent()){
            return orderMapper.toDto(existOrder.get());
        }else {
            log.error("order not exist");
            return null;
        }
    }

    public List<OrderDTO> getAllOrdersForCustomer(Long customerId){
        log.info("get all orders for customer: {}", customerId);
        Optional<Customer> existCustomer = customerService.isExist(customerId);
        if (!existCustomer.isPresent()){
            log.error("customer not exist");
            return null;
        }else {
            List<Order> orderList = orderRepository.findAllByCustomerId(customerId);
            return orderMapper.toDto(orderRepository.findAllByCustomerId(customerId));
        }
    }

    //This code creates a random product serial number for the product
    public static String generateRandomString() {
        String characterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();

        StringBuilder randomStringBuilder = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            int randomIndex = random.nextInt(characterSet.length());
            char randomChar = characterSet.charAt(randomIndex);
            randomStringBuilder.append(randomChar);
        }

        return randomStringBuilder.toString();
    }

    public String checkUniqueAndGenerateCode(){
        String code = generateRandomString();
        Optional<Order> isExistWithCode = isOrderExistWithCode(code);
        if (!isExistWithCode.isPresent()){
            return code;
        }else {
            code = checkUniqueAndGenerateCode();
        }

        return code;
    }

    public Optional<Order> isOrderExistWithCode(String code){
        return orderRepository.findByCode(code);
    }
}
