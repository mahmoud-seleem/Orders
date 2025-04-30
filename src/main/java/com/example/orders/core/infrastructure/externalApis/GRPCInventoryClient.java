package com.example.orders.core.infrastructure.externalApis;

import com.example.inventory.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class GRPCInventoryClient{

    // Inject the gRPC stub for the InventoryService
    // The name 'inventory-service' matches the configuration in application.properties
    @GrpcClient("Inventory")
    private InventoryServiceGrpc.InventoryServiceBlockingStub inventoryServiceBlockingStub;

    public IsProductExistResponse isProductExist(int id,String name) {
        // Build the request message using the generated builder
        ProductRequest request = ProductRequest.newBuilder()
                .setId(id)
                .setName(name)
                .build();

        // Make the gRPC call using the blocking stub
        return inventoryServiceBlockingStub.isProductExist(request);
    }


    public ReserveProductResponse reserveProduct(int id, int quantity) {
        // Build the request message
        ReserveProductRequest request = ReserveProductRequest.newBuilder()
                .setId(id)
                .setQuantity(quantity)
                .build();

        // Make the gRPC call
        return inventoryServiceBlockingStub.reserveProduct(request);
    }
}
