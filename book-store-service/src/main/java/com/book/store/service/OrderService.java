package com.book.store.service;

import com.book.store.dto.OrderRequest;
import com.book.store.dto.OrderResponse;
import com.book.store.dto.UpdateOrderRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);

    OrderResponse getOrderById(int orderId);

    List<OrderResponse> getAllOrder();

    OrderResponse updateOrderById(UpdateOrderRequest updateOrderRequest, int orderId);

    String deleteOrderById(int orderId);

    Page<OrderResponse> findAllOrderPages(int page, int size);

    Page<OrderResponse> sortingAllOrders(int page, int size, String sortBy, String sortDir);
}
