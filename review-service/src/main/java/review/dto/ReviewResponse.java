package review.dto;

import lombok.Data;

@Data
public class ReviewResponse {
    private String id;
    private int bookId;
    private int rating;
    private String comment;
}
