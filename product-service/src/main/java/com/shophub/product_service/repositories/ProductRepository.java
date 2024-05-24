package com.shophub.product_service.repositories;

import com.shophub.product_service.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product,String> {
}
