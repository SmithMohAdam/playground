package com.product_service.repositorys;

import com.product_service.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepo extends MongoRepository<Product , String> {
}
