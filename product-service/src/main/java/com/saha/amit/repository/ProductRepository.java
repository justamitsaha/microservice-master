package com.saha.amit.repository;


import com.saha.amit.entity.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {
    public Flux<Product> findByCategory(String category);
    public Flux<Product> findByPriceBetween(Double price1, Double price2);
}
