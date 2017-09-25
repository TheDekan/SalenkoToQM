package com.salenko.service;

import java.util.List;

import com.salenko.model.Product;

public interface ProductService {

    void update(Product row);

    Product insert(Product row);

    List<Product> findAll();

    void delete(Long id);

    Product findById(Long id);

    List<Product> sortedFind(int startPosition, int maxResults, String sortFields, String sortDirections);

    Long getCount();
    
    void setProductRow(Product product, Product row);

}
