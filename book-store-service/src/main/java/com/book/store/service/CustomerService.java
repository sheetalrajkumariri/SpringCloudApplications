package com.book.store.service;

import com.book.store.dto.CustomerRequest;
import com.book.store.dto.CustomerResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
    CustomerResponse createCustomer(CustomerRequest request);

    CustomerResponse findCustomerById(int customerId);

    List<CustomerResponse> findAllCustomer();

    String deleteCustomerById(int customerId);

    String deleteAllCustomer();

    CustomerResponse updateCustomerById(int customerId, CustomerRequest request);

    Page<CustomerResponse> findAllCustomerPage(int page, int size);

    Page<CustomerResponse> sortingAllCustomers(int page, int size, String sortBy, String sortDir);
}
