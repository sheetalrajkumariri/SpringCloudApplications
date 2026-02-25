package review.dto;

import lombok.Data;

@Data
public class ReviewRequest {
    private int rating;
    private String comment;
    private int bookId;
}
