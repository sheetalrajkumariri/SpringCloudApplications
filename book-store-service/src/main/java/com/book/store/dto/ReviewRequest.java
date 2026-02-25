package com.book.store.dto;

import lombok.Data;

@Data
public class ReviewRequest {
    private int bookId;
    private int rating;
    private String comment;
}
