package com.book.store.service.impl;

import com.book.store.client.ReviewFeignClient;
import com.book.store.dto.BookRequest;
import com.book.store.dto.BookResponse;
import com.book.store.entity.Book;
import com.book.store.exception.BadRequestException;
import com.book.store.exception.NotFoundException;
import com.book.store.repository.BookRepository;
import com.book.store.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ReviewFeignClient reviewFeignClient;

    @Override
    public BookResponse createBook(BookRequest request) {
        log.info("Start :: createBook()inside the BookServiceImpl with the request, {} ", request);
        Book book = modelMapper.map(request, Book.class);
        book = bookRepository.save(book);
        log.info("End :: createBook()inside the BookServiceImpl with the request, {} ", request);
        return modelMapper.map(book, BookResponse.class);
    }

    @Override
    public BookResponse findBookById(int bookId) {
        log.info("Start:: findBookById()inside the BooKServiceImpl with the id, {} ", bookId);
        Book book = bookRepository.findByIdAndIsDeletedFalse(bookId)
                .orElseThrow(() -> new NotFoundException("Book not found for the given id: " + bookId));
        log.info("End:: findBookById()inside the BooKServiceImpl with the id, {} ", bookId);
        return modelMapper.map(book, BookResponse.class);
    }

    @Override
    public List<BookResponse> findAllBooks() {
        log.info("Start:: findAllBooks()inside the BookServiceController");
        List<Book> bookList = bookRepository.findAllByIsDeletedFalse();
        log.info("End:: findAllBooks()inside the BookServiceController");
        return modelMapper.map(bookList, new TypeToken<List<BookResponse>>() {}.getType());
    }

    @Override
    public String deleteBookById(int bookId) {
        log.info("Start:: deleteBookById()inside the BookServiceController with the id , {} ", bookId);

        Book book = bookRepository.findByIdAndIsDeletedFalse(bookId)
                .orElseThrow(() -> new NotFoundException("Book not found for the given id: " + bookId));
        book.setDeleted(true);
        bookRepository.save(book);

        deleteBookReview(bookId);
        log.info("End:: deleteBookById()inside the BookServiceController with the id , {} ", bookId);
        return "Book deleted successfully";
    }

    private void deleteBookReview(int bookId) {
        try {
            reviewFeignClient.softDeleteReviewsByBookId(bookId);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public String deleteAllBook() {
        log.info("Start:: deleteAllBook()inside the BookServiceController");
        bookRepository.deleteAll();
        log.info("End:: deleteAllBook()inside the BookServiceController");
        return " All book deleted successfully";
    }

    @Override
    public BookResponse updateBookById(int bookId, BookRequest request) {
        log.info("Start:: updateBookById()inside the BookServiceImpl with the id, {} ", bookId);
         Book book = bookRepository.findByIdAndIsDeletedFalse(bookId)
                .orElseThrow(() -> new NotFoundException("Cannot update. Book not found for the given id" + bookId));
            modelMapper.map(request, book);
            Book update = bookRepository.save(book);
            log.info("End:: updateBookById()inside the BookServiceImpl with the id, {} ", bookId);
            return modelMapper.map(update, BookResponse.class);



    }

    @Override
    public Page<BookResponse> findAllBookPages(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> books = bookRepository.findAll(pageable);
        return books.map(book -> modelMapper.map(book, BookResponse.class));
    }

    @Override
    public Page<BookResponse> sortingAllBooks(int page, int size, String sortBy, String sortDir) {
        Sort sorting;
        if (sortDir.equalsIgnoreCase("desc")) {
            sorting = Sort.by(sortBy).descending();
        } else {
            sorting = Sort.by(sortBy).ascending();
        }
        Pageable pageable = PageRequest.of(page, size, sorting);
        Page<Book> books = bookRepository.findAll(pageable);
        return books.map(book -> modelMapper.map(book, BookResponse.class));
    }


}
