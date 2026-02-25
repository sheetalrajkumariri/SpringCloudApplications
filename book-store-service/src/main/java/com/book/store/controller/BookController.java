package com.book.store.controller;

import com.book.store.dto.BookRequest;
import com.book.store.dto.BookResponse;
import com.book.store.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

//    @Autowired
//    private KafkaProducerService kafkaProducer;

@PostMapping("/create")
public BookResponse createBook(@RequestBody BookRequest request){
    log.info("Start:: createBook()inside the BookController with the request, {} ",request);
    return bookService.createBook(request);

}

@GetMapping("/get/{bookId}")
    public BookResponse findBookById(@PathVariable int bookId){
    log.info("Start:: findBookById()inside the BookController with the id , {} ",bookId);
    return bookService.findBookById(bookId);
}

@GetMapping("/list")
    public List<BookResponse> findAllBooks() {
    log.info("Start:: findAllBooks()inside the BookController ");
    return bookService.findAllBooks();

}

@DeleteMapping("/delete/{bookId}")
    public String deleteBookById(@PathVariable int bookId){
    log.info("Start:: deleteBookById()inside the BookController with the id, {} ", bookId);
    return bookService.deleteBookById(bookId);

}

@DeleteMapping("/deleteAll")
    public String deleteAllBooks(){
    log.info("Start:: deleteAllBooks()inside the BookController");
    return bookService.deleteAllBook();
}

@PutMapping("/update/{bookId}")
    public BookResponse updateBookById(@PathVariable int bookId, @RequestBody BookRequest request){
    log.info("Start:: updateBookById()inside the BookController with the id , {} ",bookId);
    return bookService.updateBookById(bookId,request);
}

@GetMapping("/pagination/{page}/{size}")
    public Page<BookResponse> findAllBooksPages(@PathVariable int page, @PathVariable int size){
    return bookService.findAllBookPages(page,size);
}

@GetMapping("/sorting")
    public Page<BookResponse> sortingAllBooks(@RequestParam int page, @RequestParam int size, @RequestParam String sortBy, @RequestParam String sortDir){
    return bookService.sortingAllBooks(page, size, sortBy, sortDir);
}


}
