package com.book.store.service.impl;

import com.book.store.dto.CustomerRequest;
import com.book.store.dto.CustomerResponse;
import com.book.store.entity.Customer;
import com.book.store.exception.BadRequestException;
import com.book.store.exception.NotFoundException;
import com.book.store.repository.CustomerRepository;
import com.book.store.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {
        log.info("Start:: createCustomer()inside the CustomerServiceImpl with the request, {}  ", request);
        Customer customer = modelMapper.map(request, Customer.class);
        customer = customerRepository.save(customer);
        log.info("End:: createCustomer()inside the CustomerServiceImpl with the request, {}  ", request);
        return modelMapper.map(customer, CustomerResponse.class);
    }

    @Override
    public CustomerResponse findCustomerById(int customerId) {
        log.info("Start:: findCustomerById()inside the CustomerServiceImpl with the id, {}  ", customerId);
        Customer customer = customerRepository.findByIdAndIsDeletedFalse(customerId)
                .orElseThrow(() -> new NotFoundException("customer not found for ID: " + customerId));
        log.info("End:: findCustomerById()inside the CustomerServiceImpl with the id, {}  ", customerId);
        return modelMapper.map(customer, CustomerResponse.class);
    }

    @Override
    public List<CustomerResponse> findAllCustomer() {
        log.info("Start:: findAllCustomer()inside the CustomerServiceImpl");
        List<Customer> customerEntities = customerRepository.findAllByIsDeletedFalse();
        log.info("End:: findAllCustomer()inside the CustomerServiceImpl");
        return modelMapper.map(customerEntities, new TypeToken<List<CustomerResponse>>() {
        }.getType());
    }

    @Override
    public String deleteCustomerById(int customerId){
        log.info("Start:: deleteCustomerById()inside the CustomerServiceImpl with the id, {}  ", customerId);
        if (customerId <= 0) {
            throw new BadRequestException("Customer ID must be greater than 0");
        }

        Customer customer = customerRepository.findByIdAndIsDeletedFalse(customerId)
                .orElseThrow(() -> new NotFoundException("customer not found for ID: " + customerId));

        customer.setDeleted(true);
        customerRepository.save(customer);
        log.info("End:: deleteCustomerById()inside the CustomerServiceImpl with the id, {}  ", customerId);
        return "Customer deleted successfully";

    }


    @Override
    public String deleteAllCustomer() {
        log.info("Start:: deleteAll()inside the CustomerServiceImpl");
        customerRepository.deleteAll();
        return "All customer deleted successfully";
    }

    @Override
    public CustomerResponse updateCustomerById(int customerId, CustomerRequest request) {
        log.info("Start:: updateCustomerById()inside the CustomerServiceImpl with the id, {}  ", customerId);
        Customer customer = customerRepository.findByIdAndIsDeletedFalse(customerId)
                .orElseThrow(() -> new NotFoundException("customer not found for ID: " + customerId));
        modelMapper.map(request, customer);
        Customer update = customerRepository.save(customer);
        log.info("End:: updateCustomerById()inside the CustomerServiceImpl with the id, {}  ", customerId);
        return modelMapper.map(update, CustomerResponse.class);
    }

    @Override
    public Page<CustomerResponse> findAllCustomerPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customers = customerRepository.findAll(pageable);
        return customers.map(customer -> modelMapper.map(customer, CustomerResponse.class));
    }

    @Override
    public Page<CustomerResponse> sortingAllCustomers(int page, int size, String sortBy, String sortDir) {
        Sort sorting;
        if (sortDir.equalsIgnoreCase("des")) {
            sorting = Sort.by(sortBy).descending();
        } else {
            sorting = Sort.by(sortBy).ascending();
        }
        Pageable pageable = PageRequest.of(page, size, sorting);
        Page<Customer> customers = customerRepository.findAll(pageable);
        return customers.map(customer -> modelMapper.map(customer, CustomerResponse.class));
    }
}
