package com.book.store.dto;

import lombok.Data;

@Data
public class BookResponse {
    private int id;
    private String title;
    private String author;
    private int price;
}
