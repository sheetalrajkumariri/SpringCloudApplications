package com.book.store.dto;

import lombok.Data;

@Data
public class BookRequest {
    private String title;
    private String author;
    private int price;
}
