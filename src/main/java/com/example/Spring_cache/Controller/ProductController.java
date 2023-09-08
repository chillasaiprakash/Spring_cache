package com.example.Spring_cache.Controller;


import com.example.Spring_cache.Service.ProductService;
import com.example.Spring_cache.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public Product save(@RequestBody Product product) {

        return productService.saveProduct(product);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findProduct();
    }

    @GetMapping("/{id}")
    @Cacheable(key = "#id",value = "Product")
    public Optional<Product> findProduct(@PathVariable int id) {
        return productService.findProductWithId(id);
    }

    @DeleteMapping("/{id}")
    //@CacheEvict(key = "#id",value = "Product")
    public String remove(@PathVariable int id) {
        return productService.deleteProduct(id);
    }

    //@Scheduled(fixedDelay = 1800) // 3 minutes in milliseconds
    //@Scheduled(cron="0 0 30 * * *")
    @Scheduled(cron = "${cron.expression.value}")
    public void incrementPriceTask() {
        productService.incrementPriceForAllProducts();
        System.out.println("Price increment task executed.");
    }
}
