package com.book.store.controller;

import com.book.store.dto.OrderRequest;
import com.book.store.dto.OrderResponse;
import com.book.store.dto.UpdateOrderRequest;
import com.book.store.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public OrderResponse createOrder(@RequestBody OrderRequest request) {
        log.info("Start:: createOrder() inside the OrderController with the request, {} ", request);
        return orderService.createOrder(request);
    }

    @GetMapping("/get/{orderId}")
    public OrderResponse getOrderById(@PathVariable int orderId){
        log.info("Start:: geOrderById()inside the OrderController with the id, {} ", orderId);
        return orderService.getOrderById(orderId);
    }

    @GetMapping("/list")
    public List<OrderResponse>getAllOrder(){
        log.info("Start:: getAllOder() inside the OrderController ");
        return orderService.getAllOrder();

    }

    @PutMapping("/update/{orderId}")
    public OrderResponse updateOrderByI(@RequestBody UpdateOrderRequest updateOrderRequest, @PathVariable int orderId){
        log.info("Start:: updateOrderById()inside the OrderController with the id , {} ", orderId);
        return orderService.updateOrderById(updateOrderRequest,orderId);

    }

    @DeleteMapping("/delete/{orderId}")
    public String deleteOrderById(@PathVariable int orderId) {
        log.info("Start:: deleteOrderById()inside the OrderController with id, {} ",orderId);
        return orderService.deleteOrderById(orderId);
    }
    @GetMapping("/pagination/{page}/{size}")
    public Page<OrderResponse> findAllOrderPages(@PathVariable int page, @PathVariable int size){
        return orderService.findAllOrderPages(page, size);
    }

    @GetMapping("/sorting")
    public Page<OrderResponse> sortingAllOrders(@RequestParam int page, @RequestParam int size, @RequestParam String sortBy, @RequestParam String sortDir){
        return orderService.sortingAllOrders(page, size,sortBy,sortDir);
    }


}
