package review.service;

import org.springframework.data.domain.Page;
import review.dto.ReviewRequest;
import review.dto.ReviewResponse;

import java.util.List;

public interface ReviewService {
    ReviewResponse createReview(ReviewRequest request);

    ReviewResponse findReviewById(String reviewId);

    List<ReviewResponse> findAllReview();

    String deleteReviewById(String reviewId);


    ReviewResponse updateReviewById(String reviewId, ReviewRequest request);

    Page<ReviewResponse> findAllPages(int page, int size, java.lang.String sortBy, java.lang.String sortDir);

    String softDeleteReviewsByBookId(int bookId);
}
