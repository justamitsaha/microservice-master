package com.saha.amit.repository;


import com.saha.amit.entity.Product;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {
    public Flux<Product> findByCategoryId(int category);

    public Flux<Product> findByPriceBetween(Double price1, Double price2);

    public Flux<Product> findByUserId(int userId);

    @Modifying
    @Query("delete from Product where id=:id")
    public Mono<Boolean> deleteById(int id);

}
