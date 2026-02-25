package com.book.store.repository;

import com.book.store.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findByIdAndIsDeletedFalse(int customerId);

    List<Customer> findAllByIsDeletedFalse();

}

