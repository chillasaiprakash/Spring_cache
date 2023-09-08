package com.example.Spring_cache.Service;


import com.example.Spring_cache.entity.Product;
import com.example.Spring_cache.respository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {



   @Autowired
   ProductRepository productRepository;

    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    public List<Product> findProduct(){

        return productRepository.findAll();
    }

    public Optional<Product> findProductWithId(long id){
        System.out.println("called findProductWithId() from DB");
        return productRepository.findById(id);

    }


    public String deleteProduct(int id){
       productRepository.deleteById(id);
        return "product removed !!";
    }


    @Transactional
    public void incrementPriceForAllProducts() {
        List<Product> products = productRepository.findAll();

        for (Product product : products) {
            long newPrice = product.getPrice() + 1;
            product.setPrice(newPrice);
            productRepository.save(product);
        }
    }
}
