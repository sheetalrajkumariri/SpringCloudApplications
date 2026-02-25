package review.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import review.client.BookFeignClient;
import review.dto.BookResponse;
import review.dto.ReviewRequest;
import review.dto.ReviewResponse;
import review.entitiy.Review;
import review.exception.BadRequestException;
import review.exception.NotFoundException;
import review.repository.ReviewRepository;
import review.service.ReviewService;

import java.util.List;

@Service
@Slf4j
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BookFeignClient bookFeignClient;

    @Override
    public ReviewResponse createReview(ReviewRequest request) {
        log.info("Start:: createReview()inside the ReviewServiceImpl with the request, {} ",request);

        //TODO Validate bookId
        validateBookByRestTemplate(request.getBookId());
        validateBookByFeignClient(request.getBookId());

        Review review = Review.builder()
                .bookId(request.getBookId())
                .rating(request.getRating())
                .comment(request.getComment())
                .build();
        review = reviewRepository.save(review);
        log.info("End:: createReview()inside the ReviewServiceImpl with the request, {} ",request);
        return modelMapper.map(review, ReviewResponse.class);
    }

    private void validateBookByFeignClient(Integer bookId) {
        try {
            bookFeignClient.findBookById(bookId);
        } catch (Exception e){
            throw new NotFoundException(e.getMessage());
        }
    }

    private void validateBookByRestTemplate(Integer bookId) {
        String url = "http://localhost:8080/book/get/" +bookId;
        try {
            restTemplate.getForObject(url, BookResponse.class);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public ReviewResponse findReviewById(String reviewId) {
        log.info("Strat:: findReviewById()inside the ReviewServiceImp with the id, {} ", reviewId);
        Review review = reviewRepository.findByIdAndIsDeletedFalse(reviewId).orElseThrow(()->new NotFoundException("Review not found for the given id: " + reviewId));
        log.info("End:: findReviewById()inside the ReviewServiceImp with the id, {} ", reviewId);
        return modelMapper.map(review, ReviewResponse.class);
    }

    @Override
    public List<ReviewResponse> findAllReview() {
        log.info("Strat:: findAllReview()inside the ReviewServiceImp");
        List<Review> reviews = reviewRepository.findAllByIsDeletedFalse();
        log.info("End:: findAllReview()inside the ReviewServiceImp");
        return modelMapper.map(reviews,new TypeToken<List<ReviewResponse>>() {}.getType());
    }

    @Override
    public String deleteReviewById(String reviewId) {
        log.info("Start:: deleteReviewById()inside the ReviewServiceImpl with the id, {} ", reviewId);
        Review review = reviewRepository.findByIdAndIsDeletedFalse(reviewId)
                .orElseThrow(() -> new NotFoundException("Review not found for the given id: " + reviewId));
        review.setDeleted(true);
        reviewRepository.save(review);
        log.info("End:: deleteReviewById()inside the ReviewServiceImpl with the id, {} ", reviewId);
        return "Review deleted successfully.";
    }

    @Override
    public ReviewResponse updateReviewById(String reviewId, ReviewRequest request) {
        log.info("Start:: UpdateReviewById()inside theReviewServiceImpl  with the id, {} ", reviewId);
        validateBookByFeignClient(request.getBookId());
       Review review= reviewRepository.findByIdAndIsDeletedFalse(reviewId)
               .orElseThrow(() -> new NotFoundException("Cannot update. Review not found for the given id" + reviewId));

        modelMapper.map(request, review);
        Review update = reviewRepository.save(review);
        log.info("End:: UpdateReviewById()inside the ReviewServiceImpl with the id, {} ", review);
        return modelMapper.map(update, ReviewResponse.class);



    }

    @Override
    public Page<ReviewResponse> findAllPages(int page, int size, String sortBy, String sortDir) {
        Sort sorting;
        if (sortDir.equalsIgnoreCase("desc")) {
            sorting = Sort.by(sortBy).descending();
        } else {
            sorting = Sort.by(sortBy).ascending();
        }
        Pageable pageable = PageRequest.of(page, size, sorting);
        Page<Review> reviews = reviewRepository.findAll(pageable);
        return reviews.map(review -> modelMapper.map(review, ReviewResponse.class));
    }

    @Override
    public String softDeleteReviewsByBookId(int bookId) {
        List<Review> reviews = reviewRepository.findByBookIdAndIsDeletedFalse(bookId);
        if (reviews.isEmpty()){
            return "No reviews found for this book";
        }
        reviews.forEach(review -> review.setDeleted(true));
        reviewRepository.saveAll(reviews);

        return "All reviews for bookId " + bookId + " deleted successfully.";
    }


}
