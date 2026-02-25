package com.book.store.controller;

import com.book.store.dto.CustomerRequest;
import com.book.store.dto.CustomerResponse;
import com.book.store.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

 @PostMapping("/create")
    public CustomerResponse createCustomer(@RequestBody CustomerRequest request){
    log.info("Start:: createCustomer()inside the CustomerController with the request, {} ", request);
     return customerService.createCustomer(request);
 }
 @GetMapping("/get/{customerId}")
    public CustomerResponse findCustomerById(@PathVariable int customerId){
     log.info("Start:: findCustomerById()inside the CustomerController with the id, {} ", customerId);
     return customerService.findCustomerById(customerId);
 }
 @GetMapping("/list")
    public List<CustomerResponse> findAllCustomer(){
     log.info("Strat:: findAllCustomer()inside the cCustomerController");
     return customerService.findAllCustomer();
 }
 @DeleteMapping("/delete/{customerId}")
    public String deleteCustomerById(@PathVariable int customerId){
     log.info("Start:: deleteCustomerById()inside the CustomerController with id, {} ",customerId);
     return customerService.deleteCustomerById(customerId);
 }
 @DeleteMapping("/deleteAllCustomer")
    public String deleteAllCustomer(){
     log.info("Start:: deleteAllCustomer()inside the CustomerController");
     return customerService.deleteAllCustomer();
 }
 @PutMapping("/update/{customerId}")
    public CustomerResponse updateCustomerById(@PathVariable int customerId, @RequestBody CustomerRequest request){
     log.info("Start:: updateCustomerById() inside the CustomerController with id, {} ",customerId);
     return customerService.updateCustomerById(customerId,request);
 }

 @GetMapping("/pagination/{page}/{size}")
    public Page<CustomerResponse> findAllCustomerPages(@PathVariable int page, @PathVariable int size){
     return customerService.findAllCustomerPage(page, size);
 }
 @GetMapping("/sorting")
    public Page<CustomerResponse> sortingAllCustomers(@RequestParam int page, @RequestParam int size, @RequestParam String sortBy, @RequestParam String sortDir){
     return customerService.sortingAllCustomers(page, size, sortBy, sortDir);
 }

}
