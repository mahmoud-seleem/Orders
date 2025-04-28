package com.example.orders.core.domain.servicesImp;

import com.example.orders.core.application.dto.OrderDetailsDto;
import com.example.orders.core.application.dto.ProductDetailsDto;
import com.example.orders.core.domain.entities.Order;
import com.example.orders.core.domain.entities.Product;
import com.example.orders.core.infrastructure.repository.OrderRepo;
import com.example.orders.core.infrastructure.repository.ProductRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class OrdersService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductRepo productRepo;

    @Transactional
    public OrderDetailsDto createNewOrder(OrderDetailsDto orderDetailsDto) throws Exception {
        // validation
        Order order = new Order();
        order.setProducts(new ArrayList<>());
        Double totalPrice = 0.0;
        List<Product> products = order.getProducts();
        for(ProductDetailsDto productDetailsDto : orderDetailsDto.getProductsDetails()){
            Product product = populateProduct(new Product(),productDetailsDto);
            totalPrice += product.getPriceOfUnit() * product.getQuantity();
            products.add(product);
        }
        order.setTotalPrice(totalPrice);
        order.setOrderDate(new Date());
        return populateOrderDto(orderRepo.saveAndFlush(order),orderDetailsDto);
    }

    @Transactional
    public OrderDetailsDto updateOrder(OrderDetailsDto orderDetailsDto) throws Exception {
        // validation
        Order order = orderRepo.findById(orderDetailsDto.getOrderId()).get();
        order.setProducts(new ArrayList<>());
        Double totalPrice = 0.0;
        List<Product> products = order.getProducts();
        for(ProductDetailsDto productDetailsDto : orderDetailsDto.getProductsDetails()){
            Product product = populateProduct(new Product(),productDetailsDto);
            totalPrice += product.getPriceOfUnit() * product.getQuantity();
            products.add(product);
        }
        order.setTotalPrice(totalPrice);
        order.setOrderDate(new Date());
        return populateOrderDto(orderRepo.saveAndFlush(order),orderDetailsDto);
    }

    @Transactional
    public List<OrderDetailsDto> getAllOrders() throws Exception {
        List<OrderDetailsDto> result = new ArrayList<>();
        for(Order order : orderRepo.findAll()){
            result.add(populateOrderDto(order,new OrderDetailsDto()));
        }
        return result;
    }

    @Transactional
    public OrderDetailsDto getOrderById(Long orderId) throws Exception {
        // validation
        Order order = orderRepo.findById(orderId).get();
        return populateOrderDto(order,new OrderDetailsDto());
    }

    @Transactional
    public OrderDetailsDto deleteOrderById(Long orderId) throws Exception {
        // validation
        Order order = orderRepo.findById(orderId).get();
        orderRepo.delete(order);
        return populateOrderDto(order,new OrderDetailsDto());
    }




    private Product populateProduct(Product product,ProductDetailsDto dto) throws Exception {
        Class<?> dtoClass = dto.getClass();
        Class<?> entityClass = product.getClass();

        for (Field dtoField : getAllFields(dtoClass)) {
            dtoField.setAccessible(true);
            Object value = dtoField.get(dto); // Get DTO field value
            if (value != null) { // Only map non-null values
                Field entityField = getField(entityClass, dtoField.getName());
                    entityField.setAccessible(true);
                    entityField.set(product, value); // Set value in entity
                }
            }
        return product;
    }
    private ProductDetailsDto populateProductDto(Product product,ProductDetailsDto dto) throws Exception {
        Class<?> dtoClass = dto.getClass();
        Class<?> entityClass = product.getClass();

        for (Field entityField : getAllFields(entityClass)) {
            entityField.setAccessible(true);
            Object value = entityField.get(product); // Get DTO field value
            if (value != null) { // Only map non-null values
                Field dtoField = getField(dtoClass, entityField.getName());
                dtoField.setAccessible(true);
                dtoField.set(dto, value); // Set value in entity
            }
        }
        return dto;
    }
    private OrderDetailsDto populateOrderDto(Order order,OrderDetailsDto dto) throws Exception {
        Class<?> dtoClass = dto.getClass();
        Class<?> entityClass = order.getClass();

        for (Field entityField : getAllFields(entityClass)) {
            entityField.setAccessible(true);
            Object value = entityField.get(order); // Get DTO field value
            if (value != null) { // Only map non-null values
                Field dtoField = getField(dtoClass, entityField.getName());
                dtoField.setAccessible(true);
                dtoField.set(dto, value); // Set value in entity
            }
        }
        dto.setProductsDetails(new ArrayList<>());
        for(Product product : order.getProducts()){
            dto.getProductsDetails().add(populateProductDto(product,new ProductDetailsDto()));
        }
        return dto;
    }

//    private AdminDto populateAdminDto(Admin admin, AdminDto adminDto) throws IllegalAccessException {
//        System.out.println(admin);
//        for (Field entityField : getAllFields(admin.getClass())) {
//            System.out.println(entityField.getName());
//            entityField.setAccessible(true);
//            Object value = entityField.get(admin);
//            if (value != null) { // Only map non-null values
//                Field dtoField = getField(adminDto.getClass(), entityField.getName());
//                if (dtoField != null) {
//                    dtoField.setAccessible(true);
//                    dtoField.set(adminDto, value);
//                }
//            }
//        }
//        return adminDto;
//    }

    private static Field getField(Class<?> clazz, String fieldName) {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass(); // Search in parent class
            }
        }
        return null; // Field not found
    }

    private static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            fields.addAll(Arrays.stream(clazz.getDeclaredFields()).toList());
            clazz = clazz.getSuperclass();
        }
        return fields;
    }
}


