package com.inventory_service;

import com.inventory_service.models.Inventory;
import com.inventory_service.repos.InventoryRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner toDo(InventoryRepo inventoryRepo){

        return args -> {
			Inventory in1 = Inventory.builder()
					.skuCode("iPhone_13")
					.quantity(2)
					.build();
			Inventory in2 = Inventory.builder()
					.skuCode("iPhone_14")
					.quantity(0)
					.build();
			inventoryRepo.save(in1);
			inventoryRepo.save(in2);
		};
    }
}
