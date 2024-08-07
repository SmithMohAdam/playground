package com.order_service.order_service.services;

import com.order_service.order_service.dto.InventoryResponse;
import com.order_service.order_service.dto.OrderItemDto;
import com.order_service.order_service.dto.OrderRequest;
import com.order_service.order_service.exception.NotInInventoryException;
import com.order_service.order_service.models.Order;
import com.order_service.order_service.models.OrderLineItems;
import com.order_service.order_service.repos.OrderRepo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private WebClient webClient;

    @Transactional
    public void placeOrder(OrderRequest orderRequest){

        Order order = mapDtoToOrder(orderRequest);

        List<String> skuCodes = order.getOrderLineItems().stream().map(OrderLineItems::getSkuCode).toList();

        InventoryResponse[] result = webClient.get()
                        .uri("http://localhost:8083/api/inventory",
                                uriBuilder -> uriBuilder.queryParam("skuCodes",skuCodes).build())
                        .retrieve()
                        .bodyToMono(InventoryResponse[].class)
                        .block();
       if(inStock(result)){
           orderRepo.save(order);
       }else{
           throw new NotInInventoryException("Some of your order is not is stock");
       }

    }

    public Boolean inStock(InventoryResponse[] inStock){

        return Arrays.stream(inStock).allMatch(InventoryResponse::getIsInStock);


    }


    private Order mapDtoToOrder(OrderRequest orderRequest){
        List<OrderLineItems> orderLines = orderRequest.getOrderItemDtos()
                .stream().map(this::mapDtoToOrderLineItems).toList();

        return  Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItems(orderLines)
                .build();
    }

    private OrderLineItems mapDtoToOrderLineItems(OrderItemDto orderItemDto) {
        return  OrderLineItems.builder()
                .skuCode(orderItemDto.getSkuCode())
                .price(orderItemDto.getPrice())
                .quantity(orderItemDto.getQuantity())
                .build();
    }
    
    private OrderItemDto mapOrderLineItemsToDto(OrderLineItems orderLineItems){
        return OrderItemDto.builder()
                .id(orderLineItems.getId())
                .skuCode(orderLineItems.getSkuCode())
                .price(orderLineItems.getPrice())
                .quantity(orderLineItems.getQuantity())
                .build();
    }

}
