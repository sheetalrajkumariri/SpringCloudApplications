package com.book.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderRequest {
    public int customerId;
    public List<Integer> bookIds;
}
