package review.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import review.dto.BookResponse;

@FeignClient(name = "bookstore-service", url = "http://localhost:8080")
public interface BookFeignClient {

    @GetMapping("/book/get/{bookId}")
    BookResponse findBookById(@PathVariable int bookId);

}
