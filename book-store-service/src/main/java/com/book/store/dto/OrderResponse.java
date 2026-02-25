package com.book.store.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderResponse {
    private int id;
    private LocalDate orderDate;
    private CustomerResponse customer;
    private List<BookResponse> books;
}
