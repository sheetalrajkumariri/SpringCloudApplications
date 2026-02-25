package com.book.store.service;

import com.book.store.dto.BookRequest;
import com.book.store.dto.BookResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService  {

    BookResponse createBook(BookRequest request);

    BookResponse findBookById(int bookId);

    List<BookResponse> findAllBooks();

    String deleteBookById(int bookId);

    String deleteAllBook();


    BookResponse updateBookById(int bookId, BookRequest request);

    Page<BookResponse> findAllBookPages(int page, int size);

    Page<BookResponse> sortingAllBooks(int page, int size, String sortBy, String sortDir);
}
