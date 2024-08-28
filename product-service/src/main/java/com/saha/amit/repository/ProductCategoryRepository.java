package com.saha.amit.repository;

import com.saha.amit.entity.ProductCategory;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductCategoryRepository extends ReactiveCrudRepository<ProductCategory, Integer> {

    @Query("""
            SELECT p.product_id, p.product_name, p.product_description, p.price, p.image_path, c.category_name, p.user_id, p.user_name, p.category_id
            FROM product p
            JOIN category c ON p.category_id = c.id where p.category_id =:category order by p.price DESC""")
    Flux<ProductCategory>  getProductWithCategory(int category);
}
