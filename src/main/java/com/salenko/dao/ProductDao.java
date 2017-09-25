package com.salenko.dao;

import java.util.List;

import com.salenko.model.Product;

public interface ProductDao {

    void update(Product row);

    Product insert(Product row);

    List<Product> findAll();

    List<Product> sortedFind(int startPosition, int maxResults, String sortFields, String sortDirections);

    void delete(Long id);

    Product findById(Long id);

    Long getCount();

}
