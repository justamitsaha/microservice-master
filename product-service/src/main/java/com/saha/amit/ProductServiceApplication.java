package com.saha.amit;

import com.github.javafaker.Faker;
import com.saha.amit.dto.product.ProductDto;
import com.saha.amit.entity.Product;
import com.saha.amit.record.AccountsContactInfoDto;
import com.saha.amit.repository.ProductRepository;
import com.saha.amit.service.ProductService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;

@EnableConfigurationProperties(value = {AccountsContactInfoDto.class})
@SpringBootApplication
public class ProductServiceApplication implements CommandLineRunner {

    private final Log log = LogFactory.getLog(ProductServiceApplication.class);

    @Autowired
    ProductService productService;
    @Value("classpath:h2/init.sql")
    private Resource initSql;

    @Autowired
    private R2dbcEntityTemplate entityTemplate;

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        String query = StreamUtils.copyToString(initSql.getInputStream(), StandardCharsets.UTF_8);
        log.info(query);
        this.entityTemplate
                .getDatabaseClient()
                .sql(query)
                .then()
                .subscribe();

        Faker faker = new Faker();
        for (int i = 0; i < 100; i++) {
            String category = faker.commerce().department();
            category = category.replace(" ", "_");
            ProductDto productDto = new ProductDto(
                    faker.commerce().productName(),
                    faker.howIMetYourMother().catchPhrase(),
                    faker.random().nextInt(1, 10000),
                    faker.funnyName().name(),
                    category,
                    1,
                    "Amit Saha");
            productService.save(productDto).subscribe(log::info);
        }
    }
}
