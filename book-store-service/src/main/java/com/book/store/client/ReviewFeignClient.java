package com.book.store.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "review-service", url = "http://localhost:8082")
public interface ReviewFeignClient {

    @DeleteMapping("/review/delete/book/{bookId}")
    void softDeleteReviewsByBookId(@PathVariable int bookId);

}
