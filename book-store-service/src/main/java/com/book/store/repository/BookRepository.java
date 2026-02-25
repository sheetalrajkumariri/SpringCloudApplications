package com.book.store.repository;

import com.book.store.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllByIdIn(List<Integer> bookIds);


    Optional<Book> findByIdAndIsDeletedFalse(int bookId);

    List<Book> findAllByIsDeletedFalse();


}
