package com.book.store.service.impl;

import com.book.store.dto.EmailDetails;
import com.book.store.dto.OrderRequest;
import com.book.store.dto.OrderResponse;
import com.book.store.dto.UpdateOrderRequest;
import com.book.store.entity.Book;
import com.book.store.entity.Customer;
import com.book.store.entity.Order;
import com.book.store.exception.BadRequestException;
import com.book.store.exception.NotFoundException;
import com.book.store.producer.Producer;
import com.book.store.repository.BookRepository;
import com.book.store.repository.CustomerRepository;
import com.book.store.repository.OrderRepository;
import com.book.store.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private Producer producer;

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        log.info("Start:: createOrder() inside the OrderController with request, {}  ", orderRequest);
        //1. Fetch customer by customer ID
        Customer customer = customerRepository.findById(orderRequest.getCustomerId())
                .orElseThrow(()->new NotFoundException("customer not found for ID: " +orderRequest));
        List<Book> books = bookRepository.findAllByIdIn(orderRequest.getBookIds());

        //3. Create order
        Order order = new Order();
        order.setCustomer(customer);
        order.setBooks(books);
        order.setOrderDate(LocalDate.now());

        //6. Save order into db
        order = orderRepository.save(order);
        log.info("End:: createOrder() inside the OrderController with request, {}  ", orderRequest);

        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(customer.getEmail());
        emailDetails.setSubject("Order Created With ID : "+order.getId());
        emailDetails.setMsgBody("Congratulations! \n Order created successfully \n you order ID : "+order.getId());

         producer. sendOrderEmail(emailDetails);

        return modelMapper.map(order, OrderResponse.class);
    }



    @Override
    public OrderResponse getOrderById(int orderId) {
        log.info("Start::getOrderById()inside the OrderServiceImpl with the id , {} ",orderId);
        Order order =  orderRepository.findByIdAndIsDeletedFalse(orderId)
                .orElseThrow(()-> new NotFoundException("Order not found for ID: " + orderId));

        log.info("End::getOrderById()inside the OrderServiceImpl with the id , {} ",orderId);
        return modelMapper.map(order, OrderResponse.class);

    }


    @Override
    public List<OrderResponse> getAllOrder(){
        log.info("Start:: getAllOrder()inside the OrderServiceImpl");
        List<Order> orders = orderRepository.findAllByAndIsDeletedFalse();
        log.info("End:: getAllOrder()inside the OrderServiceImpl");
        return modelMapper.map(orders, new TypeToken<List<OrderResponse>>() {}.getType());
    }

    @Override
    public OrderResponse updateOrderById(UpdateOrderRequest updateOrderRequest, int orderId) {
        log.info("Start:: updateOrderById()inside the OrderServiceImpl with id , {} ", orderId);
        Order order = orderRepository.findByIdAndIsDeletedFalse(orderId)
                .orElseThrow(()->new NotFoundException("Order not found for ID: " + orderId));

        List<Book> books = bookRepository.findAllByIdIn(updateOrderRequest.getBookIds());
        order.setBooks(books);
        order = orderRepository.save(order);
        log.info("End:: updateOrderById()inside the OrderServiceImpl with id , {} ", orderId);
        return modelMapper.map(order, OrderResponse.class);
    }

    @Override
    public String deleteOrderById(int orderId) {
        log.info("Start:: deleteOrderById()inside the OrderServiceImpl with the id, {} ",orderId);
        if (orderId<=0){
            throw new BadRequestException("Order ID must be greater than 0");
        }
        Order order =  orderRepository.findByIdAndIsDeletedFalse(orderId)
                .orElseThrow(()-> new NotFoundException("Order not found for ID: " + orderId));

        order.setDeleted(true);
           orderRepository.save(order);
        log.info("End:: deleteOrderById()inside the OrderServiceImpl with the id, {} ",orderId);
        return "Order deleted successfully";
    }

    @Override
    public Page<OrderResponse> findAllOrderPages(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orders = orderRepository.findAll(pageable);
        return orders.map(order -> modelMapper.map(order, OrderResponse.class));
    }

    @Override
    public Page<OrderResponse> sortingAllOrders(int page, int size, String sortBy, String sortDir) {
        Sort sorting ;
        if (sortDir.equalsIgnoreCase("des")){
            sorting = Sort.by(sortBy).descending();
        }else {
            sorting = Sort.by(sortBy).ascending();
        }
        Pageable pageable = PageRequest.of(page, size,sorting);
        Page<Order> orders = orderRepository.findAll(pageable);
        return orders.map(order -> modelMapper.map(order, OrderResponse.class));
    }

}
