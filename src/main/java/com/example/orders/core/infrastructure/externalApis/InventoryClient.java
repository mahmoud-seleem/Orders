package com.example.orders.core.infrastructure.externalApis;


@FeignClient(name = "inventory-service")  // Use the service name registered in Eureka
public interface InventoryClient {

}
