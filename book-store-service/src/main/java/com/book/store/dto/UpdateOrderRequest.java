package com.book.store.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateOrderRequest {
    private int bookId;
    public List<Integer> bookIds;
}
