package com.order_service.order_service.services;

import com.order_service.order_service.dto.OrderItemDto;
import com.order_service.order_service.dto.OrderRequest;
import com.order_service.order_service.models.Order;
import com.order_service.order_service.models.OrderLineItems;
import com.order_service.order_service.repos.OrderRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;
    
    public void placeOrder(OrderRequest orderRequest){

        Order order = mapDtoToOrder(orderRequest);
        orderRepo.save(order);
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
