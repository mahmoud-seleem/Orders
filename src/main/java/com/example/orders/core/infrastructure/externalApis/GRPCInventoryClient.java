package com.example.orders.core.infrastructure.externalApis;


import com.example.inventory.InventoryGrpcServiceGrpc;
import com.example.inventory.ProductExistenceRequest;
import com.example.inventory.ProductExistenceResponse;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class GRPCInventoryClient{
    // Inject the gRPC blocking stub for the InventoryGrpcService
    // The name 'inventory-grpc-service' matches the configuration in application.properties
    @GrpcClient("Inventory")
    private InventoryGrpcServiceGrpc.InventoryGrpcServiceBlockingStub inventoryGrpcServiceBlockingStub;

    public ProductExistenceResponse checkProductExistence(int productId, String productName) {
        // Build the request message using the generated builder
        ProductExistenceRequest request = ProductExistenceRequest.newBuilder()
                .setProductId(productId)
                .setProductName(productName)
                .build();

        // Make the gRPC call using the blocking stub
        // The method name matches the one defined in the .proto file
        return inventoryGrpcServiceBlockingStub.checkProductExistence(request);
    }

}
