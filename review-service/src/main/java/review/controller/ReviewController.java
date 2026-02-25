package review.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import review.dto.ReviewRequest;
import review.dto.ReviewResponse;
import review.service.ReviewService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

@PostMapping("/create")
public ReviewResponse createReview(@RequestBody ReviewRequest request){
    log.info("Start:: createReview()inside the ReviewController with the Request, {}  ", request);
    return  reviewService.createReview(request);

}
@GetMapping("/get/{reviewId}")
 public ReviewResponse findReviewById(@PathVariable String reviewId){
    log.info("Start:: findReview() inside the ReViewController with the id, {} ", reviewId);
    return reviewService.findReviewById(reviewId);

}
@GetMapping("/list")
    List<ReviewResponse> findAllReview(){
    log.info("Start:: findAllReview()inside the ReviewController");
    return reviewService.findAllReview();

}
@DeleteMapping("/delete/{reviewId}")
public String deleteReviewById(@PathVariable String reviewId){
    log.info("Start:: deleteReview()inside the ReviewController with the id, {} ", reviewId);
    return reviewService.deleteReviewById(reviewId);
}

@PutMapping("/update/{reviewId}")
public ReviewResponse updateReviewById(@PathVariable String reviewId, @RequestBody ReviewRequest request){
    log.info("Start::updateReviewById()inside the ReviewController with the id, {} ", reviewId);
    return reviewService.updateReviewById(reviewId, request);

}
@GetMapping("/pagination")
public Page<ReviewResponse> findAllPages(@RequestParam int page, @RequestParam int size,
                                         @RequestParam String sortBy,
                                         @RequestParam String sortDir){
    return reviewService.findAllPages(page, size, sortBy, sortDir);

}
@DeleteMapping("delete/book/{bookId}")
public String  softDeleteReviewsByBookId(@PathVariable int bookId){
    return reviewService.softDeleteReviewsByBookId(bookId);
}



}
