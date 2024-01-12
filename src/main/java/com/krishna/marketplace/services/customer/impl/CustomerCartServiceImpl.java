package com.krishna.marketplace.services.customer.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.krishna.marketplace.dto.AddToCartDto;
import com.krishna.marketplace.dto.CartItemDto;
import com.krishna.marketplace.dto.OrderDto;
import com.krishna.marketplace.enums.OrderStatus;
import com.krishna.marketplace.model.CartItem;
import com.krishna.marketplace.model.Order;
import com.krishna.marketplace.model.Product;
import com.krishna.marketplace.model.User;
import com.krishna.marketplace.repository.CartItemRepository;
import com.krishna.marketplace.repository.OrderRepository;
import com.krishna.marketplace.repository.ProductRepository;
import com.krishna.marketplace.repository.UserRepository;
import com.krishna.marketplace.services.customer.CustomerCartService;

import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerCartServiceImpl implements CustomerCartService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ResponseEntity<?> addProductToCart(AddToCartDto addToCartDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addToCartDto.getUserId(), OrderStatus.Pending);
        Optional<CartItem> cartItem = cartItemRepository.findByProductIdAndOrderIdAndUserId(addToCartDto.getProductId(),
                activeOrder.getId(), addToCartDto.getUserId());

       System.out.println(activeOrder.getId());
        System.out.println(activeOrder.getOrderStatus());

        if (cartItem.isPresent()) {

            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);

        } else {
            Optional<Product> product = productRepository.findById(addToCartDto.getProductId());
            Optional<User> user = userRepository.findById(addToCartDto.getUserId());

            if (product.isPresent() && user.isPresent()) {
                CartItem newCartItem = new CartItem();
                newCartItem.setProduct(product.get());
                newCartItem.setPrice(product.get().getPrice());
                newCartItem.setQuantity(1L);
                newCartItem.setUser(user.get());
                newCartItem.setOrder(activeOrder);

                CartItem savedCartItem = cartItemRepository.save(newCartItem);
                activeOrder.setTotalAmount(activeOrder.getTotalAmount() + newCartItem.getPrice());
                activeOrder.setAmount(activeOrder.getAmount() + newCartItem.getPrice());
                activeOrder.getCartItems().add(newCartItem);
                orderRepository.save(activeOrder);
                return ResponseEntity.status(HttpStatus.CREATED).body(newCartItem);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product or User not found");
            }

        }

    }

    public OrderDto getCartByUserId(Long userId) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        List<CartItemDto> cartItemDtos = activeOrder.getCartItems().stream().map(CartItem::getCartDto)
                .collect(Collectors.toList());
        OrderDto orderDto = new OrderDto();
        orderDto.setId(activeOrder.getId());
        orderDto.setAmount(activeOrder.getAmount());
        orderDto.setTotalAmount(activeOrder.getTotalAmount());
        orderDto.setDiscount(activeOrder.getDiscount());
        orderDto.setOrderStatus(activeOrder.getOrderStatus());
        orderDto.setCartItemDtos(cartItemDtos);
        return orderDto;

    }

}
